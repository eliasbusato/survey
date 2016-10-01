package com.ebusato.survey.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebusato.survey.persistence.entity.Lead;
import com.ebusato.survey.persistence.entity.QLead;
import com.ebusato.survey.persistence.repository.LeadRepository;
import com.ebusato.survey.rest.exception.AlreadyExistsException;

@Service
public class LeadService {

	@Autowired
	private LeadRepository repository;
	
	private static QLead l = QLead.lead;
	
	public Lead create(Lead entity) {
		
		Lead found = repository.findOne(l.email.eq(entity.getEmail()));
		if (found != null) {
			throw new AlreadyExistsException("email already exists");
		}
		return repository.save(entity);
	}

	public Iterable<Lead> findAll() {
		return repository.findAll();
	}
}
