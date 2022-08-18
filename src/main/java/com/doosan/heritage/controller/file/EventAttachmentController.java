package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.EventAttachment;
import com.doosan.heritage.repository.EventAttachmentRepository;
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
@RequestMapping("/file/event-attachment")
public class EventAttachmentController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private EventAttachmentRepository eventAttachmentRepository;

	@GetMapping("/download/{eventAttachmentIdStr}")
	public void viewMainVisual(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String eventAttachmentIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long eventAttachmentId = DoosanDataConvertUtil.stringToLong(eventAttachmentIdStr);
		if (eventAttachmentId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		EventAttachment eventAttachment = eventAttachmentRepository.findById(eventAttachmentId).orElse(null);
		if (eventAttachment == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for event attachment ID " + eventAttachmentId + " does not exists.");
		}

		String savedPath = eventAttachment.getEventAttachmentSavedPath();
		String originalName = eventAttachment.getEventAttachmentOriginalName();
		String savedName = eventAttachment.getEventAttachmentSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsDownload(response, serverFile, originalName);
	}
}
