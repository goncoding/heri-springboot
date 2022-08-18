package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.NewsThumbnail;
import com.doosan.heritage.repository.NewsThumbnailRepository;
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
@RequestMapping("/file/news-thumbnail")
public class NewsThumbnailController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private NewsThumbnailRepository newsThumbnailRepository;

	@GetMapping("/view/{newsThumbnailIdStr}")
	public void viewMainVisual(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String newsThumbnailIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long newsThumbnailId = DoosanDataConvertUtil.stringToLong(newsThumbnailIdStr);
		if (newsThumbnailId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		NewsThumbnail newsThumbnail = newsThumbnailRepository.findById(newsThumbnailId).orElse(null);
		if (newsThumbnail == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for news thumbnail ID " + newsThumbnailId + " does not exists.");
		}

		String savedPath = newsThumbnail.getNewsThumbnailSavedPath();
		String savedName = newsThumbnail.getNewsThumbnailSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsView(response, serverFile);
	}
}
