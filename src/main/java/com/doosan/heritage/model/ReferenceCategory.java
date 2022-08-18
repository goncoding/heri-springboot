package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_reference_category")
public class ReferenceCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long referenceCategoryId;

	private String referenceCategoryName;
	private String referenceCategoryLanguage;
	private Long referenceCategoryDisplayOrder;
	private String referenceCategoryDisplayed;
}
