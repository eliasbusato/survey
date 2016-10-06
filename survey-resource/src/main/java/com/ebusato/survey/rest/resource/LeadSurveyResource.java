package com.ebusato.survey.rest.resource;

import org.springframework.hateoas.ResourceSupport;

public class LeadSurveyResource extends ResourceSupport {

	private String answer;
	private SurveyResource survey;
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public SurveyResource getSurvey() {
		return survey;
	}
	public void setSurvey(SurveyResource survey) {
		this.survey = survey;
	}	
}