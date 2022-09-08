package com.doosan.heritage.controller.view;

import com.doosan.heritage.controller.constant.NasSubPath;
import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.DoosanLocaleUtil;
import com.doosan.heritage.util.UriStringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/kr")
public class HtmlViewController {
	@GetMapping({"/en/**", "/kr/**"})
	public String getHtmlPage(HttpServletRequest request) {



		AccessLogUtil.pageAccessLog(request);
		return UriStringUtil.getDefaultView(request);
	}

//	@GetMapping("/pr_center")
//	public String pr_center(HttpServletRequest request){
//		return "/page/kr/pr_center";
//	}

}
