package com.doosan.heritage.component;

import com.doosan.heritage.util.DoosanLocaleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class MessageComponent {

	@Autowired
	private MessageSource messageSource;

	public String localizeWithArgArray(String messageId, String[] args, Locale locale) {
		return messageSource.getMessage(messageId, args, locale);
	}

	public String localizeWithArgArray(String messageId, String[] args, String localeLanguage) {
		Locale locale = DoosanLocaleUtil.localeLanguageToLocale(localeLanguage);
		return messageSource.getMessage(messageId, args, locale);
	}

	public String localizeWithArgList(String messageId, List<String> argList, Locale locale) {
		if (argList == null) {
			argList = new ArrayList<>();
		}
		return localizeWithArgArray(messageId, argList.toArray(new String[0]), locale);
	}

	public String localizeWithArgList(String messageId, List<String> argList, String localeLanguage) {
		if (argList == null) {
			argList = new ArrayList<>();
		}
		return localizeWithArgArray(messageId, argList.toArray(new String[0]), localeLanguage);
	}

	public String localize(String messageId, Locale locale) {
		return localizeWithArgArray(messageId, null, locale);
	}

	public String localize(String messageId, String localeLanguage) {
		return localizeWithArgArray(messageId, null, localeLanguage);
	}
}
