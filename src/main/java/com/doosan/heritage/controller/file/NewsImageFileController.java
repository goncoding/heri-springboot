package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.NewsImage;
import com.doosan.heritage.repository.NewsImageRepository;
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
@RequestMapping("/file/news-image")
public class NewsImageFileController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private NewsImageRepository newsImageRepository;

	@GetMapping("/view/{newsImageIdStr}")
	public void viewMainVisual(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String newsImageIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long newsImageId = DoosanDataConvertUtil.stringToLong(newsImageIdStr);
		if (newsImageId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		NewsImage newsImage = newsImageRepository.findById(newsImageId).orElse(null);
		if (newsImage == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for news image ID " + newsImageId + " does not exists.");
		}

		String savedPath = newsImage.getNewsImageSavedPath();
		String savedName = newsImage.getNewsImageSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsView(response, serverFile);
	}
}
