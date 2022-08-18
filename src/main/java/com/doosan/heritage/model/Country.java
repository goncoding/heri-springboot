package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Data
@Table(name = "heritage_country")
public class Country {

	@Id
	private String countryId;

	private String countryDefaultName;
	private String countryDisplayed;

	@Transient
	private String countryDisplayName;
}
