package com.doosan.heritage.controller.view;

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

	@GetMapping({"/en", "/kr"})
	public String getMain(HttpServletRequest request, Model model) {
		AccessLogUtil.pageAccessLog(request);
		String localeLanguage = DoosanLocaleUtil.getLocaleLanguageStringFromRequestURI(request.getRequestURI());
		return UriStringUtil.DEFAULT_LAYOUT_LOCATION + DoosanLocaleUtil.getDirectoryLanguageFromRequest(request) + "/index";
	}

}
