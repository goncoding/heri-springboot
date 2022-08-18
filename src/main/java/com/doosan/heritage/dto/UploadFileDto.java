package com.doosan.heritage.dto;

import lombok.Data;

@Data
public class UploadFileDto {

	private String originalName;
	private String savedName;
	private String savedPath;
}
