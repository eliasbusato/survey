package com.ebusato.survey.rest.resource.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.ebusato.survey.persistence.entity.Survey;
import com.ebusato.survey.rest.controller.SurveyController;
import com.ebusato.survey.rest.resource.SurveyResource;

@Component
public class SurveyResourceAssembler extends ResourceAssemblerSupport<Survey, SurveyResource> {

	public SurveyResourceAssembler() {
		super(SurveyController.class, SurveyResource.class);
	}

	@Override
	public SurveyResource toResource(Survey entity) {	
		SurveyResource resource = this.createResourceWithId(entity.getIdSurvey(), entity);
		new ModelMapper().map(entity, resource);
		return resource;
	}	
}