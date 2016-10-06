package com.ebusato.survey.rest.resource;

import org.springframework.hateoas.ResourceSupport;

public class SurveyResource extends ResourceSupport {
	
	private Long idSurvey;
	private Integer questionIndex;
	private String questionDescription;
	
	
	public Long getIdSurvey() {
		return idSurvey;
	}
	public void setIdSurvey(Long idSurvey) {
		this.idSurvey = idSurvey;
	}
	public Integer getQuestionIndex() {
		return questionIndex;
	}
	public void setQuestionIndex(Integer questionIndex) {
		this.questionIndex = questionIndex;
	}
	public String getQuestionDescription() {
		return questionDescription;
	}
	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}	
}