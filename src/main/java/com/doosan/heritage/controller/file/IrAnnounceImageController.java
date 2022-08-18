package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.IrAnnounceImage;
import com.doosan.heritage.repository.IrAnnounceImageRepository;
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
@RequestMapping("/file/ir-announce-image")
public class IrAnnounceImageController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private IrAnnounceImageRepository irAnnounceImageRepository;

	@GetMapping("/view/{irAnnounceImageIdStr}")
	public void viewIrAnnounceImage(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String irAnnounceImageIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long irAnnounceImageId = DoosanDataConvertUtil.stringToLong(irAnnounceImageIdStr);
		if (irAnnounceImageId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		IrAnnounceImage irAnnounceImage = irAnnounceImageRepository.findById(irAnnounceImageId).orElse(null);
		if (irAnnounceImage == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for ir announce image ID " + irAnnounceImageId + " does not exists.");
		}

		String savedPath = irAnnounceImage.getIrAnnounceImageSavedPath();
		String savedName = irAnnounceImage.getIrAnnounceImageSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsView(response, serverFile);
	}
}
