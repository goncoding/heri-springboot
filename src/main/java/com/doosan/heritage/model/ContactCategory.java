package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_contact_category")
public class ContactCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactCategoryId;

	private String contactCategoryName;
	private String contactCategoryLanguage;
	private String contactCategoryDisplayed;
}
