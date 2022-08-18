package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_news_thumbnail")
public class NewsThumbnail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long newsThumbnailId;

	private String newsThumbnailOriginalName;
	private String newsThumbnailSavedPath;
	private String newsThumbnailSavedName;
}
