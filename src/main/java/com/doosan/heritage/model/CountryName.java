package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_country_name")
public class CountryName {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long countryNameId;

	private String countryId;

	private String countryNameLanguage;
	private String countryNameTranslated;
}
