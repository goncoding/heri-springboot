package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.EventImage;
import com.doosan.heritage.repository.EventImageRepository;
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
@RequestMapping("/file/event-image")
public class EventImageController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private EventImageRepository eventImageRepository;

	@GetMapping("/view/{eventImageIdStr}")
	public void viewMainVisual(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String eventImageIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long eventImageId = DoosanDataConvertUtil.stringToLong(eventImageIdStr);
		if (eventImageId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		EventImage eventImage = eventImageRepository.findById(eventImageId).orElse(null);
		if (eventImage == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for event image ID " + eventImageId + " does not exists.");
		}

		String savedPath = eventImage.getEventImageSavedPath();
		String savedName = eventImage.getEventImageSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsView(response, serverFile);
	}
}
