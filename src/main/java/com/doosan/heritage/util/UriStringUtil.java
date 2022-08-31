package com.doosan.heritage.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UriStringUtil {

	public static final String DEFAULT_LAYOUT_LOCATION = "layout/";
	public static final String DEFAULT_PAGE_LOCATION = "page/";

	public static final String DEFAULT_SUB_LOCATION = "sub/";

	public static List<String> getUriPath(String requestUri) {
		if (requestUri == null) {
			return Collections.emptyList();
		}

		return Arrays.stream(requestUri.split("/"))
				.filter(string -> !string.isEmpty())
				.collect(Collectors.toList());
	}

	public static String getDefaultView(HttpServletRequest request) {
		return DEFAULT_PAGE_LOCATION + UriStringUtil.getUriPathWithNoTrailingSlash(request.getRequestURI());
	}

	public static String getViewPageDefaultView(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		List<String> uriPath = getUriPath(requestUri);
		if (!uriPath.isEmpty()) {
			uriPath.remove(uriPath.get(uriPath.size() - 1));
		}
		return DEFAULT_PAGE_LOCATION + String.join("/", uriPath);
	}

	public static String getUriPathWithNoTrailingSlash(String requestUri) {
		return String.join("/", getUriPath(requestUri));
	}

	private UriStringUtil() {
	}
}
