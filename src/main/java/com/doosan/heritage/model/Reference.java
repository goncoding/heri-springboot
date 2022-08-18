package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_reference")
public class Reference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long referenceId;

	@ManyToOne
	@JoinColumn(name = "referenceCategoryId")
	private ReferenceCategory referenceCategory;

	@OneToOne
	@JoinColumn(name = "referenceThumbnailId")
	private ReferenceThumbnail referenceThumbnail;

	private String referenceCompanyName;
	private String referenceEmbeddedVideoTitle;
	private String referenceEmbeddedVideoUrl;
	private String referenceDisplayed;
	private Long referenceDisplayOrder;
}
