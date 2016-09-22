package com.ebusato.survey.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the lead_survey database table.
 * 
 */
@Embeddable
public class LeadSurveyPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_lead", insertable=false, updatable=false, unique=true, nullable=false)
	private Long idLead;

	@Column(name="id_survey", insertable=false, updatable=false, unique=true, nullable=false)
	private Long idSurvey;

	public LeadSurveyPK() {
	}
	public Long getIdLead() {
		return this.idLead;
	}
	public void setIdLead(Long idLead) {
		this.idLead = idLead;
	}
	public Long getIdSurvey() {
		return this.idSurvey;
	}
	public void setIdSurvey(Long idSurvey) {
		this.idSurvey = idSurvey;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LeadSurveyPK)) {
			return false;
		}
		LeadSurveyPK castOther = (LeadSurveyPK)other;
		return 
			this.idLead.equals(castOther.idLead)
			&& this.idSurvey.equals(castOther.idSurvey);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idLead.hashCode();
		hash = hash * prime + this.idSurvey.hashCode();
		
		return hash;
	}
}