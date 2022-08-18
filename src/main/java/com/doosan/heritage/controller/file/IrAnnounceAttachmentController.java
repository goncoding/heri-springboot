package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.IrAnnounceAttachment;
import com.doosan.heritage.repository.IrAnnounceAttachmentRepository;
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
@RequestMapping("/file/ir-announce-attachment")
public class IrAnnounceAttachmentController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private IrAnnounceAttachmentRepository irAnnounceAttachmentRepository;

	@GetMapping("/download/{irAnnounceAttachmentIdStr}")
	public void downloadIrAnnounceAttachment(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String irAnnounceAttachmentIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long irAnnounceAttachmentId = DoosanDataConvertUtil.stringToLong(irAnnounceAttachmentIdStr);
		if (irAnnounceAttachmentId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		IrAnnounceAttachment irAnnounceAttachment = irAnnounceAttachmentRepository.findById(irAnnounceAttachmentId).orElse(null);
		if (irAnnounceAttachment == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for ir announce attachment ID " + irAnnounceAttachmentId + " does not exists.");
		}

		String savedPath = irAnnounceAttachment.getIrAnnounceAttachmentSavedPath();
		String originalName = irAnnounceAttachment.getIrAnnounceAttachmentOriginalName();
		String savedName = irAnnounceAttachment.getIrAnnounceAttachmentSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsDownload(response, serverFile, originalName);
	}
}
