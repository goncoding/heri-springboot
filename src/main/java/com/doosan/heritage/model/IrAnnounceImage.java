package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_ir_announce_image")
public class IrAnnounceImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long irAnnounceImageId;

	private String irAnnounceImageOriginalName;
	private String irAnnounceImageSavedPath;
	private String irAnnounceImageSavedName;
}
