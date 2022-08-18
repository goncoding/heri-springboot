package com.doosan.heritage.controller.view;

import com.doosan.heritage.constant.YesNoConstant;
import com.doosan.heritage.model.Contact;
import com.doosan.heritage.model.ContactCategory;
import com.doosan.heritage.model.Country;
import com.doosan.heritage.repository.ContactCategoryRepository;
import com.doosan.heritage.service.ContactService;
import com.doosan.heritage.service.CountryService;
import com.doosan.heritage.util.AccessLogUtil;
import com.doosan.heritage.util.DoosanLocaleUtil;
import com.doosan.heritage.util.UriStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping({"/en/contact-us", "/kr/contact-us"})
public class ContactUsViewController {

	@Autowired
	private ContactCategoryRepository contactCategoryRepository;

	@Autowired
	private CountryService countryService;
	@Autowired
	private ContactService contactService;

	@GetMapping
	public String getContactUs(HttpServletRequest request, Model model) {
		AccessLogUtil.pageAccessLog(request);

		String localeLanguage = DoosanLocaleUtil.getLocaleLanguageStringFromRequestURI(request.getRequestURI());
		List<ContactCategory> contactCategoryList =
				contactCategoryRepository.findByContactCategoryLanguageAndContactCategoryDisplayed(localeLanguage, YesNoConstant.YES);
		List<Country> countryList = countryService.getLocalizedCountryList(localeLanguage);

		model.addAttribute("contactCategoryList", contactCategoryList);
		model.addAttribute("countryList", countryList);

		return UriStringUtil.getDefaultView(request);
	}

	@PostMapping
	public String postContactUs(
			HttpServletRequest request,
			Model model,
			@RequestParam(name = "questionType") Long contactCategoryId,
			@RequestParam(name = "userName") String contactName,
			@RequestParam(name = "userEmail") String contactMail,
			@RequestParam(name = "title") String contactTitle,
			@RequestParam(name = "content") String contactContent,
			@RequestParam(name = "userCountryValue", required = false) String countryId,
			@RequestParam(name = "userCompany", required = false) String contactCompany,
			@RequestParam(name = "contactUsFile", required = false) MultipartFile contactMultipartFile
	) {
		String languageDirectory = DoosanLocaleUtil.getDirectoryLanguageFromRequest(request);

		Contact savedContact = contactService.saveContact(contactCategoryId, contactName, contactMail, contactTitle,
				contactContent, countryId, contactCompany, contactMultipartFile);

		contactService.sendContactMail(savedContact, languageDirectory);

		model.addAttribute("contactMail", savedContact.getContactMail());

		return UriStringUtil.DEFAULT_PAGE_LOCATION + languageDirectory + "/contact-us-submitted";
	}
}
