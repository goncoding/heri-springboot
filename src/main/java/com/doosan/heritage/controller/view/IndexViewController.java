package com.doosan.heritage.controller.view;

import com.doosan.heritage.util.DoosanLocaleUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexViewController {

	@GetMapping
	public String getIndex(HttpServletRequest request) {
		String subDirectory = DoosanLocaleUtil.localeToSubDirectory(request.getLocale());
		return "redirect:/" + subDirectory;
	}
}
