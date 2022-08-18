package com.doosan.heritage.service;

import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.model.NewsImage;
import com.doosan.heritage.repository.NewsImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticsNewsImageService {

	@Autowired
	private NewsImageRepository newsImageRepository;

	@Autowired
	private NasFileComponent nasFileComponent;

	public NewsImage getNewsImage(Long newsImageId) {
		if (newsImageId == null) {
			return new NewsImage();
		}
		return newsImageRepository.findById(newsImageId).orElse(new NewsImage());
	}
}
