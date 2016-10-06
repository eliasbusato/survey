package com.ebusato.survey.rest.resource;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.ebusato.survey.persistence.entity.Lead;
import com.ebusato.survey.rest.resource.parser.ResourceParser;

public class LeadResource extends ResourceSupport implements ResourceParser<Lead> {
	
	private Long idLead;
	private String email;
	
	private List<LeadSurveyResource> leadSurveys;
		
	public Long getIdLead() {
		return idLead;
	}
	public void setIdLead(Long idLead) {
		this.idLead = idLead;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<LeadSurveyResource> getLeadSurveys() {
		return leadSurveys;
	}	
	public void setLeadSurveys(List<LeadSurveyResource> leadSurveys) {
		this.leadSurveys = leadSurveys;
	}
}