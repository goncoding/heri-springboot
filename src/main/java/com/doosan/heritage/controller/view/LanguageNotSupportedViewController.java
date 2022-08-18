package com.doosan.heritage.controller.view;

import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.UriStringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LanguageNotSupportedViewController {

    @RequestMapping({"/kr/language-not-supported", "/en/language-not-supported"})
    public String getError(HttpServletRequest request, HttpServletResponse response) {
        AccessLogUtil.pageAccessLog(request);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return UriStringUtil.getDefaultView(request);
    }
}
