package com.doosan.heritage.controller.view;

import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.UriStringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HtmlViewController {

	@GetMapping({"/en/**", "/kr/**"})
	public String getHtmlPage(HttpServletRequest request) {
		AccessLogUtil.pageAccessLog(request);
		return UriStringUtil.getDefaultView(request);
	}
}
