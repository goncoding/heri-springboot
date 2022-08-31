package com.doosan.heritage.util;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class DoosanLocaleUtil {

	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	private static final String DEFAULT_SUB_DIRECTORY = "en";
	private static final Map<Locale, String> LOCALE_SUB_DIRECTORY_MAP = new HashMap<>();

	static {
		LOCALE_SUB_DIRECTORY_MAP.put(Locale.KOREAN, "kr");
	}

	public static String localeToSubDirectory(Locale locale) {
		String subDirectory = LOCALE_SUB_DIRECTORY_MAP.get(locale);
		if (subDirectory == null) {
			return DEFAULT_SUB_DIRECTORY;
		}
		return subDirectory;
	}

	public static Locale localeLanguageToLocale(String localeLanguage) {
		Locale locale = DEFAULT_LOCALE;
		for (Map.Entry<Locale, String> localeSubdirectoryEntry : LOCALE_SUB_DIRECTORY_MAP.entrySet()) {
			if (Objects.equals(localeSubdirectoryEntry.getKey().getLanguage(), localeLanguage)) {
				locale = localeSubdirectoryEntry.getKey();
			}
		}
		return locale;
	}

	public static Locale subDirectoryToLocale(String subDirectory) {
		Locale locale = DEFAULT_LOCALE;
		for (Map.Entry<Locale, String> localeSubdirectoryEntry : LOCALE_SUB_DIRECTORY_MAP.entrySet()) {
			if (Objects.equals(localeSubdirectoryEntry.getValue(), subDirectory)) {
				locale = localeSubdirectoryEntry.getKey();
				break;
			}
		}
		return locale;
	}

	public static String getDirectoryLanguageFromURI(String requestURI) {
		List<String> uriPath = UriStringUtil.getUriPath(requestURI);
		if (!uriPath.isEmpty()) {
			String topSubPath = uriPath.get(0);
			for (Map.Entry<Locale, String> localeSubdirectoryEntry : LOCALE_SUB_DIRECTORY_MAP.entrySet()) {
				if (Objects.equals(localeSubdirectoryEntry.getValue(), topSubPath)) {
					return topSubPath;
				}
			}
		}
		return DEFAULT_SUB_DIRECTORY;
	}

	public static String getDirectoryLanguageFromRequest(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return getDirectoryLanguageFromURI(requestURI);
	}

	public static String subDirectoryToLocaleLanguage(String subDirectory) {
		Locale locale = subDirectoryToLocale(subDirectory);
		return locale.getLanguage();
	}

	public static String getLocaleLanguageStringFromRequestURI(String requestURI) {
		return getLocaleFromRequestURI(requestURI).getLanguage();
	}

	public static Locale getLocaleFromRequestURI(String requestURI) {
		Locale locale = DEFAULT_LOCALE;
		if (requestURI != null) {
			List<String> uriPath = Arrays.stream(requestURI.split("/"))
					.filter(str -> !str.isEmpty())
					.collect(Collectors.toList());
			if (!uriPath.isEmpty()) {
				locale = subDirectoryToLocale(uriPath.get(0));
			}
		}
		return locale;
	}

	public static String getLanguageNotSupported(HttpServletRequest request) {
		return UriStringUtil.DEFAULT_PAGE_LOCATION + getDirectoryLanguageFromRequest(request) + "/language-not-supported";
	}

	private DoosanLocaleUtil() {
	}
}
