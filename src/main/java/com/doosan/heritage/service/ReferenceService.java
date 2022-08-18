package com.doosan.heritage.service;

import com.doosan.heritage.constant.YesNoConstant;
import com.doosan.heritage.model.Reference;
import com.doosan.heritage.repository.ReferenceRepository;
import com.doosan.heritage.util.HtmlStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceService {

	@Autowired
	private ReferenceRepository referenceRepository;

	public List<Reference> getReferenceByLanguage(String localeLanguage) {
		List<Reference> referenceList =
				referenceRepository.findByLanguageAndDisplayedOrderByDisplayOrderAsc(localeLanguage, YesNoConstant.YES);

		referenceList.forEach(reference ->
				reference.setReferenceCompanyName(HtmlStringUtil.convertNewLineToBr(reference.getReferenceCompanyName())));

		return referenceList;
	}
}
