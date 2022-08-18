package com.doosan.heritage.service;

import com.doosan.heritage.constant.YesNoConstant;
import com.doosan.heritage.model.Event;
import com.doosan.heritage.model.EventAttachment;
import com.doosan.heritage.model.EventImage;
import com.doosan.heritage.model.EventThumbnail;
import com.doosan.heritage.repository.EventRepository;
import com.doosan.heritage.repository.EventThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private EventThumbnailRepository eventThumbnailRepository;

	private static final Sort eventDefaultSort = Sort.by("eventDisplayOrder").descending();

	public Page<Event> getEventPageEventListByLanguageAndPageAndQuery(String localeLanguage, String query, int page) {
		int pageSize = 9;
		Pageable pageable = PageRequest.of(page, pageSize, eventDefaultSort);

		Page<Event> eventPage =
				eventRepository.findByEventLanguageAndEventDisplayedAndQuery(localeLanguage, YesNoConstant.YES, query, pageable);

		removePathAndFileNameInfo(eventPage);

		return eventPage;
	}

	public Event getEventPageEventByLanguageAndEventId(String localeLanguage, Long eventId) {
		Event event = eventRepository.findById(eventId).orElse(new Event());

		removePathAndFileNameInfo(event);

		if (Objects.equals(event.getEventLanguage(), localeLanguage)) {
			return event;
		} else {
			return null;
		}
	}

	public Event getPreviousEvent(Event event) {
		if (event == null) {
			return null;
		}

		String language = event.getEventLanguage();
		Long eventDisplayOrder = event.getEventDisplayOrder();

		return eventRepository.findFirstByEventLanguageAndEventDisplayedAndEventDisplayOrderGreaterThanOrderByEventDisplayOrderAsc(
				language, YesNoConstant.YES, eventDisplayOrder);
	}

	public Event getNextEvent(Event event) {
		if (event == null) {
			return null;
		}

		String language = event.getEventLanguage();
		Long eventDisplayOrder = event.getEventDisplayOrder();

		return eventRepository.findFirstByEventLanguageAndEventDisplayedAndEventDisplayOrderLessThanOrderByEventDisplayOrderDesc(
				language, YesNoConstant.YES, eventDisplayOrder);
	}

	private void removePathAndFileNameInfo(Event event) {
		if (event.getEventThumbnail() == null) {
			return;
		}

		removeEventThumbnailPathAndFileNameInfo(event);
		removeEventImagePathAndFileNameInfo(event);
		removeEventAttachmentPathAndFileNameInfo(event);
	}

	private void removePathAndFileNameInfo(Page<Event> eventPage) {
		eventPage.getContent().forEach(this::removePathAndFileNameInfo);
	}

	private void removeEventThumbnailPathAndFileNameInfo(Event event) {
		EventThumbnail eventThumbnail = event.getEventThumbnail();
		eventThumbnail.setEventThumbnailOriginalName("");
		eventThumbnail.setEventThumbnailSavedPath("");
		eventThumbnail.setEventThumbnailSavedName("");
	}

	private void removeEventImagePathAndFileNameInfo(Event event) {
		List<EventImage> eventImageList = event.getEventImageList();
		eventImageList.forEach(eventImage -> {
			eventImage.setEventImageOriginalName("");
			eventImage.setEventImageSavedPath("");
			eventImage.setEventImageSavedName("");
		});
	}

	private void removeEventAttachmentPathAndFileNameInfo(Event event) {
		List<EventAttachment> eventAttachmentList = event.getEventAttachmentList();
		eventAttachmentList.forEach(eventAttachment -> {
			eventAttachment.setEventAttachmentOriginalName("");
			eventAttachment.setEventAttachmentSavedPath("");
			eventAttachment.setEventAttachmentSavedName("");
		});
	}
}
