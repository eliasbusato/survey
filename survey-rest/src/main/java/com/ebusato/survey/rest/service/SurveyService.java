package com.ebusato.survey.rest.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebusato.survey.persistence.entity.Survey;
import com.ebusato.survey.persistence.repository.SurveyRepository;

@Service
public class SurveyService {

	@Autowired
	private SurveyRepository repository;
	
	public Iterable<Survey> findAll() {
		return this.repository.findAll();
	}	
}
