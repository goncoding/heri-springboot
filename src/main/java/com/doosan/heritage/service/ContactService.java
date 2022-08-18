package com.doosan.heritage.service;

import com.doosan.heritage.component.MailBaseComponent;
import com.doosan.heritage.component.MessageComponent;
import com.doosan.heritage.component.NasFileComponent;
import com.doosan.heritage.constant.MailTemplatePath;
import com.doosan.heritage.constant.NasSubPath;
import com.doosan.heritage.dto.UploadFileDto;
import com.doosan.heritage.model.Contact;
import com.doosan.heritage.model.ContactFile;
import com.doosan.heritage.model.ContactManager;
import com.doosan.heritage.repository.*;
import com.doosan.heritage.util.DoosanDateUtil;
import com.doosan.heritage.util.DoosanLocaleUtil;
import com.doosan.heritage.util.HtmlStringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactService {

	@Value("${heritage.site.protocol}")
	private String siteProtocol;
	@Value("${heritage.site.domain}")
	private String siteDomain;
	@Value("${heritage.contact.submit.sender-mail:doosanheritage@doosan.com}")
	private String senderMail;
	@Value("${heritage.admin-link.contact-file:https://v3-obc.doosan.com/doosan-heritage/file/contact-file/download/}")
	private String adminContactFileLinkPrefix;

	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private ContactCategoryRepository contactCategoryRepository;
	@Autowired
	private ContactFileRepository contactFileRepository;
	@Autowired
	private ContactManagerRepository contactManagerRepository;
	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CountryService countryService;

	@Autowired
	private MailBaseComponent mailBaseComponent;
	@Autowired
	private MessageComponent messageComponent;
	@Autowired
	private NasFileComponent nasFileComponent;

	@Autowired
	private TemplateEngine templateEngine;

	public Contact saveContact(
			Long contactCategoryId,
			String contactName,
			String contactMail,
			String contactTitle,
			String contactContent,
			String countryId,
			String contactCompany,
			MultipartFile contactMultipartFile
	) {
		Contact contact = new Contact();

		contactCategoryRepository.findById(contactCategoryId).ifPresent(contact::setContactCategory);
		countryRepository.findById(countryId).ifPresent(contact::setCountry);

		contact.setContactTitle(contactTitle);
		contact.setContactContent(contactContent);
		contact.setContactName(contactName);
		contact.setContactMail(contactMail);
		contact.setContactCompany(contactCompany);

		Contact savedContact = contactRepository.save(contact);

		saveContactFile(savedContact, contactMultipartFile);

		return savedContact;
	}

	public void sendContactMail(Contact contact, String languageDirectory) {
		List<ContactManager> contactManagerList = contactManagerRepository.findByContactCategory(contact.getContactCategory());

		contactManagerList.stream()
				.collect(Collectors.groupingBy(ContactManager::getContactManagerTimezoneDiff))
				.forEach((timezoneDiff, contactManagerListByTimezone) -> {
					ZoneOffset zoneOffset;
					if (timezoneDiff != null) {
						int offsetHours = timezoneDiff.intValue() / 60;
						int offsetMinutes = timezoneDiff.intValue() % 60;
						zoneOffset = ZoneOffset.ofHoursMinutes(offsetHours, offsetMinutes);
					} else {
						zoneOffset = ZoneOffset.UTC;
					}

					String mailContent = buildMailContent(contact, languageDirectory, zoneOffset);

					List<String> toList = filterManagerEmailByType(contactManagerListByTimezone, "to");
					List<String> ccList = filterManagerEmailByType(contactManagerListByTimezone, "cc");
					List<String> bccList = filterManagerEmailByType(contactManagerListByTimezone, "bcc");

					String localeLanguage = DoosanLocaleUtil.subDirectoryToLocaleLanguage(languageDirectory);
					String senderName = messageComponent.localize("common.company-name", localeLanguage);
					String mailTitle = messageComponent.localize("contact.submit.mail-title", localeLanguage);

					mailBaseComponent.sendMail(senderName, senderMail, toList, ccList, bccList, mailTitle, mailContent);

					log.debug("Sent mail to: {}, cc: {}, bcc: {}", toList, ccList, bccList);
					log.debug("Mail title: {}", mailTitle);
					log.debug("Sender name: {}", senderName);
					log.debug("Mail Content\n{}", mailContent);
				});
	}

	@SneakyThrows
	private void saveContactFile(Contact contact, MultipartFile contactMultipartFile) {
		UploadFileDto uploadFileDto = nasFileComponent.saveMultipartFile(contactMultipartFile, NasSubPath.CONTACT_UPLOAD_SUB_PATH);

		ContactFile contactFile = new ContactFile();

		contactFile.setContactFileOriginalName(uploadFileDto.getOriginalName());
		contactFile.setContactFileSavedName(uploadFileDto.getSavedName());
		contactFile.setContactFileSavedPath(uploadFileDto.getSavedPath());
		contactFile.setContactId(contact.getContactId());

		ContactFile savedContactFile = contactFileRepository.save(contactFile);
		contact.setContactFile(savedContactFile);
	}

	private String buildMailContent(Contact contact, String languageDirectory, ZoneOffset zoneOffset) {
		String templateLocation =
				MailTemplatePath.MAIL_TEMPLATE_ROOT + "/" + languageDirectory + MailTemplatePath.MAIL_TEMPLATE_SUB_PATH_CONTACT_SUBMIT;
		String localeLanguage = DoosanLocaleUtil.subDirectoryToLocaleLanguage(languageDirectory);

		Context mailContext = new Context();

		applyMailCommonInfo(mailContext, contact);
		applyMailCustomerInfo(mailContext, contact, localeLanguage);
		applyMailInquiryInfo(mailContext, contact, zoneOffset);
		applyMailAttachmentInfo(mailContext, contact);

		return templateEngine.process(templateLocation, mailContext);
	}

	private void applyMailCommonInfo(Context mailContext, Contact contact) {
		mailBaseComponent.addCommonVariablesToContext(mailContext);
		mailContext.setVariable("inquiryId", contact.getContactId());
	}

	private void applyMailCustomerInfo(Context mailContext, Contact contact, String localeLanguage) {
		mailContext.setVariable("customerName", contact.getContactName());
		if (contact.getCountry() != null) {
			countryService.applyLocalizedCountryDisplayName(contact.getCountry(), localeLanguage);
			mailContext.setVariable("customerCountry", contact.getCountry().getCountryDisplayName());
		}
		mailContext.setVariable("customerCompany", contact.getContactCompany());
		mailContext.setVariable("customerMail", contact.getContactMail());
	}

	private void applyMailInquiryInfo(Context mailContext, Contact contact, ZoneOffset zoneOffset) {
		if (contact.getContactCategory() != null) {
			mailContext.setVariable("inquiryType", contact.getContactCategory().getContactCategoryName());
		}
		mailContext.setVariable("inquiryTitle", contact.getContactTitle());
		mailContext.setVariable("inquiryContent", HtmlStringUtil.convertNewLineToBr(contact.getContactContent()));
		if (contact.getContactTime() != null) {
			String inquiryDateTime = DoosanDateUtil.dateToStandardDateTimeString(contact.getContactTime(), zoneOffset);
			if (zoneOffset.getTotalSeconds() != 32400) {
				inquiryDateTime = inquiryDateTime + " (UTC" + zoneOffset + ")";
			}
			mailContext.setVariable("inquiryDateTime", inquiryDateTime);
		}
	}

	private void applyMailAttachmentInfo(Context mailContext, Contact contact) {
		if (contact.getContactFile() != null) {
			mailContext.setVariable("inquiryAttachmentName", contact.getContactFile().getContactFileOriginalName());
			mailContext.setVariable("inquiryAttachmentAddress", adminContactFileLinkPrefix + contact.getContactFile().getContactFileId());
		}
	}

	private List<String> filterManagerEmailByType(List<ContactManager> contactManagerList, String contactType) {
		return contactManagerList.stream().
				filter(contactManager -> Objects.equals(contactManager.getContactManagerMailType(), contactType))
				.map(ContactManager::getContactManagerMailAddress)
				.collect(Collectors.toList());
	}
}
