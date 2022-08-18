package com.doosan.heritage.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class DoosanStringUtil {

	public static String encodeURI(String string) {
		String encodedString = "";
		try {
			encodedString = URLEncoder.encode(string, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			log.error("Unsupported Encoding UTF-8. ", e);
		}
		return encodedString;
	}

	public static String decodeURI(String string) {
		String decodedString = "";
		try {
			decodedString = URLDecoder.decode(string, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			log.error("Unsupported Encoding UTF-8. ", e);
		}
		return decodedString;
	}

	public static List<String> splitString(String string, String delimiter) {
		List<String> resultList = new ArrayList<>();
		if (string == null) {
			return resultList;
		} else if (delimiter == null) {
			resultList.add(string);
			return resultList;
		}

		resultList = Arrays.asList(string.split(delimiter));
		return resultList.stream()
				.filter(Objects::nonNull)
				.map(String::trim)
				.collect(Collectors.toList());
	}

	private DoosanStringUtil() {
	}
}
