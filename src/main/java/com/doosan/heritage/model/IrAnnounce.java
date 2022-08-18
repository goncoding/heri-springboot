package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "heritage_ir_announce")
public class IrAnnounce {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long irAnnounceId;

	private String irAnnounceTitle;
	private String irAnnounceContent;

	@Temporal(TemporalType.DATE)
	private Date irAnnounceDate;

	private Date irAnnounceReleaseStartTime;
	private String irAnnounceLanguage;
	private String irAnnounceDisplayed;

	@OneToMany
	@JoinColumn(name = "irAnnounceId")
	@OrderBy("irAnnounceAttachmentDisplayOrder")
	private List<IrAnnounceAttachment> irAnnounceAttachmentList = new ArrayList<>();
}
