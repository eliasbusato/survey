package com.ebusato.geofusion.survey.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the lead_survey database table.
 * 
 */
@Entity
@Table(name="lead_survey")
@NamedQuery(name="LeadSurvey.findAll", query="SELECT l FROM LeadSurvey l")
public class LeadSurvey implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LeadSurveyPK id;

	@Column(nullable=false, length=255)
	private String answer;

	//bi-directional many-to-one association to Lead
	@ManyToOne
	@JoinColumn(name="id_lead", nullable=false, insertable=false, updatable=false)
	private Lead lead;

	//bi-directional many-to-one association to Survey
	@ManyToOne
	@JoinColumn(name="id_survey", nullable=false, insertable=false, updatable=false)
	private Survey survey;

	public LeadSurvey() {
	}

	public LeadSurveyPK getId() {
		return this.id;
	}

	public void setId(LeadSurveyPK id) {
		this.id = id;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Lead getLead() {
		return this.lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public Survey getSurvey() {
		return this.survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

}