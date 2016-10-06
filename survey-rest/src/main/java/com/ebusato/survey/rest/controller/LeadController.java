package com.ebusato.survey.rest.controller;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ebusato.survey.persistence.entity.Lead;
import com.ebusato.survey.rest.exception.BadRequestException;
import com.ebusato.survey.rest.resource.LeadResource;
import com.ebusato.survey.rest.resource.assembler.LeadResourceAssembler;
import com.ebusato.survey.rest.service.LeadService;
import com.google.common.io.BaseEncoding;

@RestController
@RequestMapping(path = "leads")
public class LeadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LeadController.class);

	@Autowired
	private LeadService service;

	@Autowired
	private LeadResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public LeadResource create(@RequestBody LeadResource resource) {

		if(!isValidEmailAddress(resource.getEmail())) {
			throw new BadRequestException("invalid email address.");
		}
		
		LOGGER.info("created called for lead with email[{}]", resource.getEmail());
		Lead entity = resource.toEntity(Lead.class);
		Lead created = service.create(entity);
		LOGGER.info("lead with email[{}] succesfully created!");
		return assembler.toResource(created);
	}

	@RequestMapping(path = "/{idLead}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Transactional
	public LeadResource update(@PathVariable(value = "idLead") String idLead, @RequestBody LeadResource resource) {

		if (StringUtils.isEmpty(idLead)) {
			throw new BadRequestException("lead id is required!");
		}

		String decodedEmail = new String(BaseEncoding.base16().lowerCase().decode(idLead), StandardCharsets.UTF_8);
		LOGGER.info("update called for lead with email[{}]", decodedEmail);
		resource.setEmail(decodedEmail);

		Lead entity = resource.toEntity(Lead.class);
		Lead created = service.update(entity);
		LOGGER.info("lead with email[{}] updated!", decodedEmail);
		return assembler.toResource(created);
	}

	public static boolean isValidEmailAddress(String email) {		
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
