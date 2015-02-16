package com.feisystems.icas.service.dto;

import java.util.Date;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feisystems.icas.domain.clinicaldata.ResultObservation;

public class ResultOrganizerDto {
	
	private Long id;

	private Long patientId;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
    private Date resultOrganizerDateTime;
	
	private String resultOrganizerCode;
	
	private String resultOrganizerCodeDisplayName;
	
	private String resultOrganizerStatusCode;
	
	private Set<ResultObservationDto> resultObservations = new HashSet<ResultObservationDto>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Date getResultOrganizerDateTime() {
		return resultOrganizerDateTime;
	}

	public void setResultOrganizerDateTime(Date resultOrganizerDateTime) {
		this.resultOrganizerDateTime = resultOrganizerDateTime;
	}

	public String getResultOrganizerCode() {
		return resultOrganizerCode;
	}

	public void setResultOrganizerCode(String resultOrganizerCode) {
		this.resultOrganizerCode = resultOrganizerCode;
	}

	public String getResultOrganizerStatusCode() {
		return resultOrganizerStatusCode;
	}

	public void setResultOrganizerStatusCode(String resultOrganizerStatusCode) {
		this.resultOrganizerStatusCode = resultOrganizerStatusCode;
	}

	public Set<ResultObservationDto> getResultObservations() {
		return resultObservations;
	}

	public void setResultObservations(Set<ResultObservationDto> resultObservations) {
		this.resultObservations = resultObservations;
	}

	public void setResultOrganizerCodeDisplayName(
			String resultOrganizerCodeDisplayName) {
		this.resultOrganizerCodeDisplayName = resultOrganizerCodeDisplayName;
	}
	
	public String getResultOrganizerCodeDisplayName() {
		return resultOrganizerCodeDisplayName;
	}

}
