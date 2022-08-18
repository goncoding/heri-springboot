package com.doosan.heritage.controller.view;

import com.doosan.heritage.service.MainVisualService;
import com.doosan.heritage.service.NewsService;
import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.DoosanLocaleUtil;
import com.doosan.heritage.util.UriStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainViewController {

	@Autowired
	private MainVisualService mainVisualService;
	@Autowired
	private NewsService newsService;

	@GetMapping({"/en", "/kr"})
	public String getMain(HttpServletRequest request, Model model) {
		AccessLogUtil.pageAccessLog(request);
		String localeLanguage = DoosanLocaleUtil.getLocaleLanguageStringFromRequestURI(request.getRequestURI());
		addCommonAttributesToModel(localeLanguage, model);
		return UriStringUtil.DEFAULT_PAGE_LOCATION + DoosanLocaleUtil.getDirectoryLanguageFromRequest(request) + "/index";
	}

	private void addCommonAttributesToModel(String localeLanguage, Model model) {
		model.addAttribute("mainVisualList", mainVisualService.getMainVisualByLanguage(localeLanguage));
		model.addAttribute("newsList", newsService.getMainNewsByLanguage(localeLanguage));
	}
}
