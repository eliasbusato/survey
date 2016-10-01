package com.ebusato.survey.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.ebusato.survey.persistence.entity.Lead;
import com.ebusato.survey.rest.resource.parser.ResourceParser;

public class LeadResource extends ResourceSupport implements ResourceParser<Lead> {
	
	private Long idLead;
	private String email;
	
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
}