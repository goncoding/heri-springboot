package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_news_attachment")
public class NewsAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long newsAttachmentId;

	private Long newsId;

	private String newsAttachmentOriginalName;
	private String newsAttachmentSavedPath;
	private String newsAttachmentSavedName;
	private Long newsAttachmentDisplayOrder;
}
