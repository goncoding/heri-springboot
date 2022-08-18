package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_main_visual")
public class MainVisual {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mainVisualId;

	private String mainVisualTitle;
	private String mainVisualType;
	private String mainVisualLanguage;
	private String mainVisualDisplayed;
	private Long mainVisualDisplayOrder;

	@OneToOne
	@JoinColumn(name = "mainVisualFileId")
	private MainVisualFile mainVisualFile;
}
