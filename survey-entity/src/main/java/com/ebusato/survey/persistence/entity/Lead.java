package com.ebusato.survey.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the lead database table.
 * 
 */
@Entity
@Table(name="lead")
@NamedQuery(name="Lead.findAll", query="SELECT l FROM Lead l")
public class Lead implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LEAD_IDLEAD_GENERATOR", sequenceName="SEQ_ID_LEAD", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LEAD_IDLEAD_GENERATOR")
	@Column(name="id_lead", unique=true, nullable=false)
	private Long idLead;

	@Column(nullable=false, length=255)
	private String email;

	//bi-directional many-to-one association to LeadSurvey
	@OneToMany(mappedBy="lead")
	private List<LeadSurvey> leadSurveys;
	
	@Column(nullable=false)
	private boolean engaged;

	public Lead() {
	}

	public Long getIdLead() {
		return this.idLead;
	}

	public void setIdLead(Long idLead) {
		this.idLead = idLead;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isEngaged() {
		return this.engaged;
	}
	
	public void setEngaged(boolean engaged) {
		this.engaged = engaged;
	}

	public List<LeadSurvey> getLeadSurveys() {
		return this.leadSurveys;
	}

	public void setLeadSurveys(List<LeadSurvey> leadSurveys) {
		this.leadSurveys = leadSurveys;
	}

	public LeadSurvey addLeadSurvey(LeadSurvey leadSurvey) {
		getLeadSurveys().add(leadSurvey);
		leadSurvey.setLead(this);

		return leadSurvey;
	}

	public LeadSurvey removeLeadSurvey(LeadSurvey leadSurvey) {
		getLeadSurveys().remove(leadSurvey);
		leadSurvey.setLead(null);

		return leadSurvey;
	}

}