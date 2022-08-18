package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_main_visual_file")
public class MainVisualFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mainVisualFileId;

	private String mainVisualFileOriginalName;
	private String mainVisualFileSavedPath;
	private String mainVisualFileSavedName;
}
