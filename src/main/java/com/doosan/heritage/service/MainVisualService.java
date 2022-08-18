package com.doosan.heritage.service;

import com.doosan.heritage.constant.YesNoConstant;
import com.doosan.heritage.model.MainVisual;
import com.doosan.heritage.repository.MainVisualFileRepository;
import com.doosan.heritage.repository.MainVisualRepository;
import com.doosan.heritage.util.HtmlStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainVisualService {

	@Autowired
	private MainVisualRepository mainVisualRepository;
	@Autowired
	private MainVisualFileRepository mainVisualFileRepository;

	public List<MainVisual> getMainVisualByLanguage(String localeLanguage) {
		List<MainVisual> mainVisualList =
				mainVisualRepository.findByMainVisualLanguageAndMainVisualDisplayedOrderByMainVisualDisplayOrderAsc(localeLanguage, YesNoConstant.YES);

		convertMainVisualTitleToHtml(mainVisualList);

		return mainVisualList;
	}

	private void convertMainVisualTitleToHtml(List<MainVisual> mainVisualList) {
		mainVisualList.forEach(mainVisual ->
				mainVisual.setMainVisualTitle(HtmlStringUtil.convertNewLineToBr(mainVisual.getMainVisualTitle())));
	}
}
