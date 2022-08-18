package com.doosan.heritage.service;

import com.doosan.heritage.constant.YesNoConstant;
import com.doosan.heritage.model.IrAnnounce;
import com.doosan.heritage.repository.IrAnnounceAttachmentRepository;
import com.doosan.heritage.repository.IrAnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IrAnnounceService {

	private static final Sort irAnnounceDefaultSort = Sort.by("irAnnounceDate").descending();

	@Autowired
	private IrAnnounceRepository irAnnounceRepository;
	@Autowired
	private IrAnnounceAttachmentRepository irAnnounceAttachmentRepository;

	public IrAnnounce getIrAnnounceByLanguageAndIrAnnounceId(String localeLanguage, Long irAnnounceId) {
		IrAnnounce irAnnounce = irAnnounceRepository.findById(irAnnounceId).orElse(new IrAnnounce());

		if (Objects.equals(irAnnounce.getIrAnnounceLanguage(), localeLanguage)) {
			return irAnnounce;
		} else {
			return null;
		}
	}

	public Page<IrAnnounce> getIrAnnouncePageByLanguageAndPageAndIrAnnounceYearAndQuery(
			String localeLanguage, Integer irAnnounceYear, int pageNumber, String query) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(pageNumber, pageSize, irAnnounceDefaultSort);
		Date currentDate = new Date();

		return irAnnounceRepository.findByLanguageAndDisplayedAndReleaseStartTimeAfterAndYearAndQuery(
				localeLanguage, YesNoConstant.YES, irAnnounceYear, currentDate, query, pageable);
	}

	public Integer getIrAnnounceMinYear(String localeLanguage) {
		IrAnnounce firstIrAnnounce =
				irAnnounceRepository.findFirstByIrAnnounceLanguageAndIrAnnounceDisplayedOrderByIrAnnounceDateAsc(
						localeLanguage, YesNoConstant.YES);

		Calendar baseDate = new GregorianCalendar();
		if (firstIrAnnounce != null && firstIrAnnounce.getIrAnnounceDate() != null) {
			baseDate.setTime(firstIrAnnounce.getIrAnnounceDate());
		} else {
			baseDate.setTime(new Date());
		}

		return baseDate.get(Calendar.YEAR);
	}

	public Integer getIrAnnounceMaxYear(String localeLanguage) {
		IrAnnounce firstIrAnnounce =
				irAnnounceRepository.findFirstByIrAnnounceLanguageAndIrAnnounceDisplayedOrderByIrAnnounceDateDesc(
						localeLanguage, YesNoConstant.YES);

		Calendar baseDate = new GregorianCalendar();
		if (firstIrAnnounce != null && firstIrAnnounce.getIrAnnounceDate() != null) {
			baseDate.setTime(firstIrAnnounce.getIrAnnounceDate());
		} else {
			baseDate.setTime(new Date());
		}

		return baseDate.get(Calendar.YEAR);
	}

	public IrAnnounce getPrevIrAnnounce(IrAnnounce irAnnounce) {
		if (irAnnounce == null) {
			return null;
		}

		String language = irAnnounce.getIrAnnounceLanguage();
		Date currentDate = new Date();
		Date irAnnounceDate = irAnnounce.getIrAnnounceDate();

		Pageable pageable = PageRequest.of(0, 1, Sort.Direction.ASC, "irAnnounceDate", "irAnnounceId");
		List<IrAnnounce> irAnnounceList =
				irAnnounceRepository.findDisplayablePreviousNewsByLanguage(language, YesNoConstant.YES, currentDate, irAnnounceDate, pageable);

		if (irAnnounceList.isEmpty()) {
			return null;
		} else {
			return irAnnounceList.get(0);
		}
	}

	public IrAnnounce getNextIrAnnounce(IrAnnounce irAnnounce) {
		if (irAnnounce == null) {
			return null;
		}

		String language = irAnnounce.getIrAnnounceLanguage();
		Date currentDate = new Date();
		Date irAnnounceDate = irAnnounce.getIrAnnounceDate();

		Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "irAnnounceDate", "irAnnounceId");

		List<IrAnnounce> irAnnounceList =
				irAnnounceRepository.findDisplayableNextNewsByLanguage(language, YesNoConstant.YES, currentDate, irAnnounceDate, pageable);
		if (irAnnounceList.isEmpty()) {
			return null;
		} else {
			return irAnnounceList.get(0);
		}
	}
}
