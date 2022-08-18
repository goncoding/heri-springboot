package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.EventThumbnail;
import com.doosan.heritage.repository.EventThumbnailRepository;
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
@RequestMapping("/file/event-thumbnail")
public class EventThumbnailController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private EventThumbnailRepository eventThumbnailRepository;

	@GetMapping("/view/{eventThumbnailIdStr}")
	public void viewMainVisual(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String eventThumbnailIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long eventThumbnailId = DoosanDataConvertUtil.stringToLong(eventThumbnailIdStr);
		if (eventThumbnailId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		EventThumbnail eventThumbnail = eventThumbnailRepository.findById(eventThumbnailId).orElse(null);
		if (eventThumbnail == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for event thumbnail ID " + eventThumbnailId + " does not exists.");
		}

		String savedPath = eventThumbnail.getEventThumbnailSavedPath();
		String savedName = eventThumbnail.getEventThumbnailSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsView(response, serverFile);
	}
}
