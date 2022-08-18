package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.MainVisualFile;
import com.doosan.heritage.repository.MainVisualFileRepository;
import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.DoosanDataConvertUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Log4j2
@Controller
@RequestMapping("/file/main-visual-file")
public class MainVisualFileController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private MainVisualFileRepository mainVisualFileRepository;

	@GetMapping("/view/{mainVisualFileIdStr}")
	public void viewMainVisual(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String mainVisualFileIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long mainVisualFileId = DoosanDataConvertUtil.stringToLong(mainVisualFileIdStr);
		if (mainVisualFileId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		MainVisualFile mainVisualFile = mainVisualFileRepository.findById(mainVisualFileId).orElse(null);
		if (mainVisualFile == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for main visual ID " + mainVisualFileId + " does not exists.");
		}

		String savedPath = mainVisualFile.getMainVisualFileSavedPath();
		String savedName = mainVisualFile.getMainVisualFileSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsView(response, serverFile);
	}
}
