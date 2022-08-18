package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.ContactFile;
import com.doosan.heritage.repository.ContactFileRepository;
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
@RequestMapping("/file/contact")
public class ContactFileController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private ContactFileRepository contactFileRepository;

	@GetMapping("/download/{contactFileIdStr}")
	public void viewMainVisual(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String contactFileIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long contactFileId = DoosanDataConvertUtil.stringToLong(contactFileIdStr);
		if (contactFileId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		ContactFile contactFile = contactFileRepository.findById(contactFileId).orElse(null);
		if (contactFile == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for event attachment ID " + contactFileId + " does not exists.");
		}

		String savedPath = contactFile.getContactFileSavedPath();
		String originalName = contactFile.getContactFileOriginalName();
		String savedName = contactFile.getContactFileSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsDownload(response, serverFile, originalName);
	}
}
