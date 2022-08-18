package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_event_thumbnail")
public class EventThumbnail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventThumbnailId;

	private String eventThumbnailOriginalName;
	private String eventThumbnailSavedPath;
	private String eventThumbnailSavedName;
}
