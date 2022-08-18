package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "heritage_event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;

	@OneToOne
	@JoinColumn(name = "eventThumbnailId")
	private EventThumbnail eventThumbnail;

	private String eventTitle;
	private String eventContent;
	private String eventSchedule;
	private String eventLocation;
	private String eventTopic;
	private String eventHost;

	@Temporal(TemporalType.DATE)
	private Date eventStartDate;

	@Temporal(TemporalType.DATE)
	private Date eventEndDate;

	private String eventLanguage;
	private String eventDisplayed;
	private Long eventDisplayOrder;

	@OneToMany
	@JoinColumn(name = "eventId")
	private List<EventImage> eventImageList = new ArrayList<>();

	@OneToMany
	@JoinColumn(name = "eventId")
	private List<EventAttachment> eventAttachmentList = new ArrayList<>();
}
