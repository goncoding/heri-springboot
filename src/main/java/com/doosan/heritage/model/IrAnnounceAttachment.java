package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_ir_announce_attachment")
public class IrAnnounceAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long irAnnounceAttachmentId;

	private Long irAnnounceId;

	private String irAnnounceAttachmentOriginalName;
	private String irAnnounceAttachmentSavedPath;
	private String irAnnounceAttachmentSavedName;
	private Long irAnnounceAttachmentDisplayOrder;
}
