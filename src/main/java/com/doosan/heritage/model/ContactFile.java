package com.doosan.heritage.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "heritage_contact_file")
public class ContactFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactFileId;

	private Long contactId;

	private String contactFileOriginalName;
	private String contactFileSavedPath;
	private String contactFileSavedName;
}
