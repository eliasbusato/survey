package com.ebusato.survey.rest.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ebusato.survey.persistence.entity.Lead;
import com.ebusato.survey.persistence.entity.QLead;
import com.ebusato.survey.persistence.repository.LeadRepository;
import com.ebusato.survey.rest.exception.AlreadyExistsException;
import com.ebusato.survey.rest.exception.BadRequestException;

@Service
public class LeadService {

	@Autowired
	private LeadRepository repository;
	
	private static QLead l = QLead.lead;
	
	public Lead create(Lead entity) {		
		if (StringUtils.isEmpty(entity.getEmail())) {
			throw new BadRequestException("invalid email");
		}		
		Lead found = repository.findOne(l.email.eq(entity.getEmail()));
		if (found != null) {
			throw new AlreadyExistsException("email already exists");
		}
		return repository.save(entity);		
	}

	public Lead update(Lead entity) {		
		Lead lead = repository.findOne(l.email.eq(entity.getEmail()));
		if (lead == null) {
			throw new BadRequestException("email does not exists");
		}
		if (!CollectionUtils.isEmpty(lead.getLeadSurveys())) {
			throw new BadRequestException("email already answered survey");
		}
		
		entity.getLeadSurveys().stream().forEach(leadSurvey -> {	
			leadSurvey.getId().setIdSurvey(leadSurvey.getSurvey().getIdSurvey());
			leadSurvey.getId().setIdLead(lead.getIdLead());
			lead.addLeadSurvey(leadSurvey);
		});
		return repository.save(lead);
	}
}
