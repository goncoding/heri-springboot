package com.doosan.heritage.controller.file;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.ReferenceThumbnail;
import com.doosan.heritage.repository.ReferenceThumbnailRepository;
import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.DoosanDataConvertUtil;
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

@Controller
@RequestMapping("/file/reference-thumbnail")
public class ReferenceThumbnailController {

	@Value("${logistics.file.nas-storage:null}")
	private String nasStorage;

	@Autowired
	private ReferenceThumbnailRepository referenceThumbnailRepository;

	@GetMapping("/view/{referenceThumbnailIdStr}")
	public void viewMainVisual(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable String referenceThumbnailIdStr
	) {
		AccessLogUtil.fileViewAccessLog(request);

		Long referenceThumbnailId = DoosanDataConvertUtil.stringToLong(referenceThumbnailIdStr);
		if (referenceThumbnailId == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong ID format.");
		}

		ReferenceThumbnail referenceThumbnail = referenceThumbnailRepository.findById(referenceThumbnailId).orElse(null);
		if (referenceThumbnail == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry for reference thumbnail ID " + referenceThumbnailId + " does not exists.");
		}

		String savedPath = referenceThumbnail.getReferenceThumbnailSavedPath();
		String savedName = referenceThumbnail.getReferenceThumbnailSavedName();

		File serverFile = new File(nasStorage + savedPath + "/" + savedName);
		NasFileComponent.putFileToResponseStreamAsView(response, serverFile);
	}
}
