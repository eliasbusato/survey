package com.ebusato.geofusion.survey.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the survey database table.
 * 
 */
@Entity
@Table(name="survey")
@NamedQuery(name="Survey.findAll", query="SELECT s FROM Survey s")
public class Survey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SURVEY_IDSURVEY_GENERATOR", sequenceName="SEQ_ID_SURVEY", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SURVEY_IDSURVEY_GENERATOR")
	@Column(name="id_survey", unique=true, nullable=false)
	private Long idSurvey;

	@Column(name="question_description", nullable=false, length=255)
	private String questionDescription;

	@Column(name="question_index", nullable=false)
	private Integer questionIndex;

	//bi-directional many-to-one association to LeadSurvey
	@OneToMany(mappedBy="survey")
	private List<LeadSurvey> leadSurveys;

	public Survey() {
	}

	public Long getIdSurvey() {
		return this.idSurvey;
	}

	public void setIdSurvey(Long idSurvey) {
		this.idSurvey = idSurvey;
	}

	public String getQuestionDescription() {
		return this.questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public Integer getQuestionIndex() {
		return this.questionIndex;
	}

	public void setQuestionIndex(Integer questionIndex) {
		this.questionIndex = questionIndex;
	}

	public List<LeadSurvey> getLeadSurveys() {
		return this.leadSurveys;
	}

	public void setLeadSurveys(List<LeadSurvey> leadSurveys) {
		this.leadSurveys = leadSurveys;
	}

	public LeadSurvey addLeadSurvey(LeadSurvey leadSurvey) {
		getLeadSurveys().add(leadSurvey);
		leadSurvey.setSurvey(this);

		return leadSurvey;
	}

	public LeadSurvey removeLeadSurvey(LeadSurvey leadSurvey) {
		getLeadSurveys().remove(leadSurvey);
		leadSurvey.setSurvey(null);

		return leadSurvey;
	}

}