package com.doosan.heritage.controller.rest;

import com.doosan.heritage.model.Event;
import com.doosan.heritage.service.EventService;
import com.doosan.heritage.util.DoosanDataConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/event")
public class EventRestController {

	@Autowired
	private EventService eventService;

	@GetMapping
	public Page<Event> eventList(
			@RequestParam(required = false, defaultValue = "") String language,
			@RequestParam(required = false, defaultValue = "") String query,
			@RequestParam(required = false) String page
	) {
		int pageInt = DoosanDataConvertUtil.stringToLongWithDefault(page, 0L).intValue();
		return eventService.getEventPageEventListByLanguageAndPageAndQuery(language, query, pageInt);
	}
}
