package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "heritage_news")
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long newsId;

	@OneToOne
	@JoinColumn(name = "newsThumbnailId")
	private NewsThumbnail newsThumbnail;

	private String newsType;
	private String newsTitle;
	private String newsSummary;
	private String newsContent;
	private String newsCompany;
	private String newsLink;

	@Temporal(TemporalType.DATE)
	private Date newsPressDate;

	private String newsLanguage;
	private String newsDisplayed;
}
