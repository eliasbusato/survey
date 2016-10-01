package com.ebusato.survey.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ebusato.survey.persistence.entity.Lead;
import com.ebusato.survey.rest.resource.LeadResource;
import com.ebusato.survey.rest.resource.assembler.LeadResourceAssembler;
import com.ebusato.survey.rest.service.LeadService;

@RestController
@RequestMapping(path="leads")
public class LeadController {
			
	@Autowired
	private LeadService service;
	
	@Autowired
	private LeadResourceAssembler assembler;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Transactional
	public List<LeadResource> get() {
		Iterable<Lead> leads = service.findAll();
		return assembler.toResources(leads);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public LeadResource create(@RequestBody LeadResource resource) {
		Lead entity = resource.toEntity(Lead.class);
		Lead created = service.create(entity);
		return assembler.toResource(created);
	}
}
