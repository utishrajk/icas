package com.feisystems.icas.service.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AllergyDto {

	private Long id;

	private String adverseEventTypeCode;

	private String adverseEventTypeName;

	private String allergenCode;

	private String allergenName;

	private String allergyReactionCode;

	private String allergyReactionName;

	private String allergySeverityCode;

	private String allergySeverityName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date allergyStartDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date allergyEndDate;

	private String note;

	private Long patientId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdverseEventTypeCode() {
		return adverseEventTypeCode;
	}

	public void setAdverseEventTypeCode(String adverseEventTypeCode) {
		this.adverseEventTypeCode = adverseEventTypeCode;
	}

	public String getAdverseEventTypeName() {
		return adverseEventTypeName;
	}

	public void setAdverseEventTypeName(String adverseEventTypeName) {
		this.adverseEventTypeName = adverseEventTypeName;
	}

	public String getAllergenCode() {
		return allergenCode;
	}

	public void setAllergenCode(String allergenCode) {
		this.allergenCode = allergenCode;
	}

	public String getAllergenName() {
		return allergenName;
	}

	public void setAllergenName(String allergenName) {
		this.allergenName = allergenName;
	}

	public String getAllergyReactionCode() {
		return allergyReactionCode;
	}

	public void setAllergyReactionCode(String allergyReactionCode) {
		this.allergyReactionCode = allergyReactionCode;
	}

	public String getAllergyReactionName() {
		return allergyReactionName;
	}

	public void setAllergyReactionName(String allergyReactionName) {
		this.allergyReactionName = allergyReactionName;
	}

	public String getAllergySeverityCode() {
		return allergySeverityCode;
	}

	public void setAllergySeverityCode(String allergySeverityCode) {
		this.allergySeverityCode = allergySeverityCode;
	}

	public String getAllergySeverityName() {
		return allergySeverityName;
	}

	public void setAllergySeverityName(String allergySeverityName) {
		this.allergySeverityName = allergySeverityName;
	}

	public Date getAllergyStartDate() {
		return allergyStartDate;
	}

	public void setAllergyStartDate(Date allergyStartDate) {
		this.allergyStartDate = allergyStartDate;
	}

	public Date getAllergyEndDate() {
		return allergyEndDate;
	}

	public void setAllergyEndDate(Date allergyEndDate) {
		this.allergyEndDate = allergyEndDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

}
