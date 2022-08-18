package com.doosan.heritage.util;

public class DoosanDataConvertUtil {

	public static Long stringToLongWithDefault(String string, Long defaultValue) {
		if (string == null) {
			return defaultValue;
		}

		try {
			return Long.parseLong(string);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Long stringToLong(String string) {
		return stringToLongWithDefault(string, null);
	}

	public static Integer stringToIntegerWithDefault(String string, Integer defaultValue) {
		if (string == null) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Integer stringToInteger(String string) {
		return stringToIntegerWithDefault(string, null);
	}

	private DoosanDataConvertUtil() {
	}
}
