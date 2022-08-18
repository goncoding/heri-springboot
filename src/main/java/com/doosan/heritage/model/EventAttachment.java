package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_event_attachment")
public class EventAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventAttachmentId;

	private Long eventId;

	private String eventAttachmentOriginalName;
	private String eventAttachmentSavedPath;
	private String eventAttachmentSavedName;
	private Long eventAttachmentDisplayOrder;
}
