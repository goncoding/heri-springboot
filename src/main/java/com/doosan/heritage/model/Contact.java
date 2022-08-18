package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "heritage_contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactId;

	@OneToOne
	@JoinColumn(name = "contactCategoryId")
	private ContactCategory contactCategory;

	private String contactTitle;
	private String contactContent;
	private String contactName;
	private String contactMail;

	@OneToOne
	@JoinColumn(name = "countryId")
	private Country country;

	private String contactCompany;

	@Temporal(TemporalType.TIMESTAMP)
	private Date contactTime = new Date();

	private String contactMarkedAsJunk = "n";

	@Transient
	private ContactFile contactFile;
}
