package com.ebusato.survey.batch.listener;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ebusato.survey.persistence.entity.Lead;
import com.google.common.io.BaseEncoding;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class EmailNotification implements ItemProcessListener<Lead, Lead> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotification.class);

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Autowired
	private ResourceLoader resourceLoader;
		
	@Value("${mail.from}")
	private String mailFrom;
	
	@Value("${mail.subject}")
	private String mailSubject;
	
	@Value("${survey.url}")
	private String surveyURL;
	
	@Override
	public void beforeProcess(Lead item) { }

	@Override
	public void afterProcess(Lead item, Lead result) {
		
		LOGGER.info("sending welcome email to [{}]", item.getEmail());
		
		StringWriter stringWriter = new StringWriter();
		
		try {
			MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setFrom(this.mailFrom);
			helper.setSubject(this.mailSubject);			
			helper.setTo(item.getEmail());
			
			String encodedEmail = BaseEncoding.base16().lowerCase().encode(item.getEmail().getBytes(StandardCharsets.UTF_8));			
			String surveyLink = String.format(surveyURL, encodedEmail);
			
			Template emailTemplate = this.freemarkerConfig.getTemplate("emailtemplate.html");
			Map<String, Object> model = new HashMap<>();
			model.put("surveyLink", surveyLink);
			
			emailTemplate.process(model, stringWriter);	
						
			Resource resource = resourceLoader.getResource("classpath:logo-email.jpg");
									
			helper.setText(stringWriter.toString(), true);
			helper.addInline("logo-email.jpg", resource);
			mailSender.send(mimeMessage);
			LOGGER.info("welcome email succefully sent to [{}]", item.getEmail());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				stringWriter.close();
			} catch (IOException e) { 
				LOGGER.warn("could not close writer. memory leak alert!");
			}
		}
	}

	@Override
	public void onProcessError(Lead item, Exception e) {
		LOGGER.error("error sending mail to [{}] skipping item...", item.getEmail(), e);			
	}
}