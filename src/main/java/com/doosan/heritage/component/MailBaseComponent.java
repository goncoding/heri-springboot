package com.doosan.heritage.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Component
public class MailBaseComponent {

	@Value("${heritage.site.protocol}")
	private String siteProtocol;
	@Value("${heritage.site.domain}")
	private String siteDomain;

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private TemplateEngine templateEngine;

	public void sendMail(
			String senderName,
			String senderMail,
			List<String> mailToList,
			List<String> mailCcList,
			List<String> mailBccList,
			String mailTitle,
			String mailContents
	) {
		MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

			mimeMessageHelper.setFrom(senderMail, senderName);

			mimeMessageHelper.setTo(mailToList.toArray(new String[0]));
			if (mailCcList != null) {
				mimeMessageHelper.setCc(mailCcList.toArray(new String[0]));
			}
			if (mailBccList != null) {
				mimeMessageHelper.setBcc(mailBccList.toArray(new String[0]));
			}

			mimeMessageHelper.setSubject(mailTitle);
			mimeMessageHelper.setText(mailContents, true);
		};

		javaMailSender.send(mimeMessagePreparator);
	}

	public void addCommonVariablesToContext(Context mailContext) {
		mailContext.setVariable("siteProtocol", siteProtocol);
		mailContext.setVariable("siteDomain", siteDomain);
	}
}
