package com.feisystems.icas.domain.clinicaldata;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class TreatmentPlan implements Serializable {
	
	private static final long serialVersionUID = -2192272303034543019L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date planCreateTime;

    @Column(name = "primaryDiagnosis")
	private String primaryDiagnosis;
	
    @Column(name = "shortTermGoal")
	private String shortTermGoal;
	
	@Column(name = "longTermGoal")
    private String longTermGoal;
	
	@ElementCollection
    @CollectionTable(name="Objectives", joinColumns=@JoinColumn(name="treatment_objectives"))
    @Column(name="objectives")
	private List<String> objectives;
	
    @Column(name = "objectivesNote")
    private String objectivesNote;
    
    @Column(name = "goalsNote")
    private String goalsNote;
    
	@Column(name = "evidence")
    private String evidence;
	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "treatmentplan")
    private Set<Intervention> interventions = new HashSet<Intervention>();
    
    public Date getPlanCreateTime() {
		return planCreateTime;
	}

	public void setPlanCreateTime(Date planCreateTime) {
		this.planCreateTime = planCreateTime;
	}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Intervention> getInterventions() {
    	return interventions;
    }
    
    public void setInterventions(Set<Intervention> interventions) {
    	this.interventions = interventions;
    }
    	
	public String getPrimaryDiagnosis() {
		return primaryDiagnosis;
	}

	public void setPrimaryDiagnosis(String primaryDiagnosis) {
		this.primaryDiagnosis = primaryDiagnosis;
	}

	public String getShortTermGoal() {
		return shortTermGoal;
	}

	public void setShortTermGoal(String shortTermGoal) {
		this.shortTermGoal = shortTermGoal;
	}

	public String getLongTermGoal() {
		return longTermGoal;
	}

	public void setLongTermGoal(String longTermGoal) {
		this.longTermGoal = longTermGoal;
	}

	public List<String> getObjectives() {
		return objectives;
	}

	public void setObjectives(List<String> objectives) {
		this.objectives = objectives;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public String getObjectivesNote() {
		return objectivesNote;
	}

	public void setObjectivesNote(String objectivesNote) {
		this.objectivesNote = objectivesNote;
	}

	public String getGoalsNote() {
		return goalsNote;
	}

	public void setGoalsNote(String goalsNote) {
		this.goalsNote = goalsNote;
	}
	
	

}
