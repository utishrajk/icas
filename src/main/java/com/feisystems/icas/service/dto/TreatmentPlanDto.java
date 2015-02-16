package com.feisystems.icas.service.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TreatmentPlanDto {

	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date planCreateTime;

	private Set<InterventionDto> interventions = new HashSet<InterventionDto>();

	private String primaryDiagnosis;

	private String shortTermGoal;

	private String longTermGoal;

	private List<String> objectives;
	
	private String objectivesNote;
	
    private String goalsNote;
    
    private String evidence;

    public Set<InterventionDto> getInterventions() {
		return interventions;
	}

	public void setInterventions(Set<InterventionDto> interventions) {
		this.interventions = interventions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPlanCreateTime() {
		return planCreateTime;
	}

	public void setPlanCreateTime(Date planCreateTime) {
		this.planCreateTime = planCreateTime;
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
