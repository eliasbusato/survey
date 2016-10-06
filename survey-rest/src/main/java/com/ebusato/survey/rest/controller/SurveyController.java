package com.ebusato.survey.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ebusato.survey.persistence.entity.Survey;
import com.ebusato.survey.rest.resource.SurveyResource;
import com.ebusato.survey.rest.resource.assembler.SurveyResourceAssembler;
import com.ebusato.survey.rest.service.SurveyService;

@RestController
@RequestMapping(path = "surveys")
public class SurveyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);

	@Autowired
	private SurveyService service;

	@Autowired
	private SurveyResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SurveyResource> get() {
		LOGGER.info("survey list called");
		Iterable<Survey> entities = service.findAll();
		return assembler.toResources(entities);
	}
}
