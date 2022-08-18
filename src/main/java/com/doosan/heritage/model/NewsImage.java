package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_news_image")
public class NewsImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long newsImageId;

	private String newsImageOriginalName;
	private String newsImageSavedPath;
	private String newsImageSavedName;
}
