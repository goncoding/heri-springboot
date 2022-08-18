package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_event_image")
public class EventImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventImageId;

	private Long eventId;

	private String eventImageOriginalName;
	private String eventImageSavedPath;
	private String eventImageSavedName;
	private Long eventImageDisplayOrder;
}
