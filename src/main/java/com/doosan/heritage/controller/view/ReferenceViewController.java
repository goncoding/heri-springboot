package com.doosan.heritage.controller.view;

import com.doosan.heritage.model.Reference;
import com.doosan.heritage.model.ReferenceCategory;
import com.doosan.heritage.service.ReferenceService;
import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.DoosanLocaleUtil;
import com.doosan.heritage.util.UriStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/kr/reference", "/en/reference"})
public class ReferenceViewController {

	@Autowired
	private ReferenceService referenceService;

	@GetMapping("/reference")
	public String getReference(HttpServletRequest request, Model model) {
		AccessLogUtil.pageAccessLog(request);
		String localeLanguage = DoosanLocaleUtil.getLocaleLanguageStringFromRequestURI(request.getRequestURI());

		List<Reference> referenceList = referenceService.getReferenceByLanguage(localeLanguage);
		List<ReferenceCategory> referenceCategoryList = referenceList.stream()
				.map(Reference::getReferenceCategory)
				.distinct()
				.sorted(Comparator.comparing(ReferenceCategory::getReferenceCategoryDisplayOrder))
				.collect(Collectors.toList());

		model.addAttribute("referenceList", referenceList);
		model.addAttribute("referenceCategoryList", referenceCategoryList);

		return UriStringUtil.getDefaultView(request);
	}
}
