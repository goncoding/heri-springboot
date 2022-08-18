package com.doosan.heritage.controller.view;

import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.DoosanLocaleUtil;
import com.doosan.heritage.util.UriStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class ErrorViewController implements ErrorController {

	@RequestMapping("/error")
	public String getError(HttpServletRequest request) {
		String errorURI = getErrorURI(request);
		String errorQueryString = getErrorQueryString(request);
		int statusCode = getStatusCode(request);

		AccessLogUtil.pageErrorLog(statusCode, errorURI, errorQueryString);
		return UriStringUtil.DEFAULT_PAGE_LOCATION + DoosanLocaleUtil.getDirectoryLanguageFromURI(errorURI) + "/error";
	}

	private String getErrorURI(HttpServletRequest request) {
		Object errorURIObject = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

		if (errorURIObject != null) {
			return errorURIObject.toString();
		} else {
			return "";
		}
	}

	private String getErrorQueryString(HttpServletRequest request) {
		return (String) request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);
	}

	private int getStatusCode(HttpServletRequest request) {
		Object statusCodeObject = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (statusCodeObject instanceof Integer) {
			return (Integer) statusCodeObject;
		} else {
			return HttpStatus.NOT_FOUND.value();
		}
	}
}
