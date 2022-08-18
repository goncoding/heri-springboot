package com.doosan.heritage.config;

import com.doosan.heritage.util.DoosanLocaleUtil;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class LogisticsLocaleResolver implements LocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		return DoosanLocaleUtil.getLocaleFromRequestURI(request.getRequestURI());
	}

	@Override
	public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
		// There is no need to control locale via httpServletRequest/Response, nothing to do here.
	}
}
