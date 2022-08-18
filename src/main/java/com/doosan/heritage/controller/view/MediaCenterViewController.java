package com.doosan.heritage.controller.view;

import com.doosan.heritage.model.Event;
import com.doosan.heritage.model.IrAnnounce;
import com.doosan.heritage.model.News;
import com.doosan.heritage.service.EventService;
import com.doosan.heritage.service.IrAnnounceService;
import com.doosan.heritage.service.NewsService;
import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.DoosanDataConvertUtil;
import com.doosan.heritage.util.DoosanLocaleUtil;
import com.doosan.heritage.util.UriStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
@RequestMapping({"/en/media-center", "/kr/media-center"})
public class MediaCenterViewController {

	@Autowired
	private NewsService newsService;
	@Autowired
	private EventService eventService;
	@Autowired
	private IrAnnounceService irAnnounceService;

	@GetMapping("/news/{newsIdStr}")
	public String getNewsDetail(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@PathVariable String newsIdStr
	) {
		AccessLogUtil.pageAccessLog(request);
		String localeLanguage = DoosanLocaleUtil.getLocaleLanguageStringFromRequestURI(request.getRequestURI());

		Long newsId = DoosanDataConvertUtil.stringToLong(newsIdStr);
		News news = newsService.getNewsPageNewsByLanguageAndNewsId(localeLanguage, newsId);
		News previousNews = newsService.getPreviousNews(news);
		News nextNews = newsService.getNextNews(news);

		model.addAttribute("news", news);
		model.addAttribute("previousNews", previousNews);
		model.addAttribute("nextNews", nextNews);

		return UriStringUtil.DEFAULT_PAGE_LOCATION + DoosanLocaleUtil.getDirectoryLanguageFromRequest(request) + "/media-center/news-detail";
	}

	@GetMapping("/event/{eventIdStr}")
	public String getEventDetail(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@PathVariable String eventIdStr
	) {
		AccessLogUtil.pageAccessLog(request);
		String localeLanguage = DoosanLocaleUtil.getLocaleLanguageStringFromRequestURI(request.getRequestURI());

		Long eventId = DoosanDataConvertUtil.stringToLong(eventIdStr);
		Event event = eventService.getEventPageEventByLanguageAndEventId(localeLanguage, eventId);
		Event previousEvent = eventService.getPreviousEvent(event);
		Event nextEvent = eventService.getNextEvent(event);

		model.addAttribute("event", event);
		model.addAttribute("previousEvent", previousEvent);
		model.addAttribute("nextEvent", nextEvent);

		return UriStringUtil.DEFAULT_PAGE_LOCATION + DoosanLocaleUtil.getDirectoryLanguageFromRequest(request) + "/media-center/event-detail";
	}

	@GetMapping("/ir-announce")
	public String getIrAnnounce(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(defaultValue = "") String year,
			@RequestParam(defaultValue = "") String query,
			@RequestParam(required = false) String page
	) {
		AccessLogUtil.pageAccessLog(request);
		String localeLanguage = DoosanLocaleUtil.getLocaleLanguageStringFromRequestURI(request.getRequestURI());

		if (!Objects.equals(localeLanguage, "ko")) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return DoosanLocaleUtil.getLanguageNotSupported(request);
		}

		Integer irAnnounceYear = DoosanDataConvertUtil.stringToInteger(year);
		int pageNumberInt = DoosanDataConvertUtil.stringToLongWithDefault(page, 0L).intValue();

		Page<IrAnnounce> irAnnouncePage =
				irAnnounceService.getIrAnnouncePageByLanguageAndPageAndIrAnnounceYearAndQuery(localeLanguage, irAnnounceYear, pageNumberInt, query);

		Integer irAnnounceStartYear = irAnnounceService.getIrAnnounceMinYear(localeLanguage);
		Integer irAnnounceEndYear = irAnnounceService.getIrAnnounceMaxYear(localeLanguage);

		model.addAttribute("irAnnouncePage", irAnnouncePage);
		model.addAttribute("irAnnounceStartYear", irAnnounceStartYear);
		model.addAttribute("irAnnounceEndYear", irAnnounceEndYear);

		model.addAttribute("year", year);
		model.addAttribute("query", query);

		return UriStringUtil.getDefaultView(request);
	}

	@GetMapping("/ir-announce/{irAnnounceIdStr}")
	public String getIrAnnounceDetail(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@PathVariable String irAnnounceIdStr
	) {
		AccessLogUtil.pageAccessLog(request);
		String localeLanguage = DoosanLocaleUtil.getLocaleLanguageStringFromRequestURI(request.getRequestURI());

		if (!Objects.equals(localeLanguage, "ko")) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return DoosanLocaleUtil.getLanguageNotSupported(request);
		}

		Long irAnnounceId = DoosanDataConvertUtil.stringToLong(irAnnounceIdStr);
		IrAnnounce irAnnounce = irAnnounceService.getIrAnnounceByLanguageAndIrAnnounceId(localeLanguage, irAnnounceId);
		IrAnnounce previousIrAnnounce = irAnnounceService.getPrevIrAnnounce(irAnnounce);
		IrAnnounce nextIrAnnounce = irAnnounceService.getNextIrAnnounce(irAnnounce);

		model.addAttribute("irAnnounce", irAnnounce);
		model.addAttribute("previousIrAnnounce", previousIrAnnounce);
		model.addAttribute("nextIrAnnounce", nextIrAnnounce);

		return UriStringUtil.DEFAULT_PAGE_LOCATION + DoosanLocaleUtil.getDirectoryLanguageFromRequest(request) + "/media-center/ir-announce-detail";
	}
}
