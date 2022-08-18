package com.doosan.heritage.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class LogisticsWebConfig {

	@Bean
	public LocaleResolver localeResolver() {
		return new LogisticsLocaleResolver();
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
