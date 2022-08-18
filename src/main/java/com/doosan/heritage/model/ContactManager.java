package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_contact_manager")
public class ContactManager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactManagerId;

	@OneToOne
	@JoinColumn(name = "contactCategoryId")
	private ContactCategory contactCategory;

	private String contactManagerName;
	private String contactManagerMailAddress;
	private String contactManagerMailType;
	private String contactManagerLanguage;
	private Long contactManagerTimezoneDiff;
}
