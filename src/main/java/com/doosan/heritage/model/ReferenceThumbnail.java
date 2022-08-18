package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_reference_thumbnail")
public class ReferenceThumbnail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long referenceThumbnailId;

	private Long referenceId;

	private String referenceThumbnailOriginalName;
	private String referenceThumbnailSavedPath;
	private String referenceThumbnailSavedName;
}
