package com.ebusato.survey.rest.resource.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.ebusato.survey.persistence.entity.Lead;
import com.ebusato.survey.rest.controller.LeadController;
import com.ebusato.survey.rest.resource.LeadResource;

@Component
public class LeadResourceAssembler extends ResourceAssemblerSupport<Lead, LeadResource> {

	public LeadResourceAssembler() {
		super(LeadController.class, LeadResource.class);
	}

	@Override
	public LeadResource toResource(Lead entity) {	
		LeadResource resource = this.createResourceWithId(entity.getIdLead(), entity);
		new ModelMapper().map(entity, resource);
		return resource;
	}	
}