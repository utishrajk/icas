package com.feisystems.icas.service.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MedicationDto {

	private Long id;

	private String medicationCode;
	
	private String medicationCodeDisplayName;

	private QuantityTypeDto doseQuantity;

	private String productFormCode;
	
	private String productFormCodeDisplayName;

	private String routeCode;
	
	private String routeCodeDisplayName;

	private String freeTextSig;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date medicationStartDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date medicationEndDate;

	private Long patientId;

	private String medicationNote;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMedicationCode() {
		return medicationCode;
	}

	public void setMedicationCode(String medicationCode) {
		this.medicationCode = medicationCode;
	}

	public QuantityTypeDto getDoseQuantity() {
		return doseQuantity;
	}

	public void setDoseQuantity(QuantityTypeDto doseQuantity) {
		this.doseQuantity = doseQuantity;
	}

	public String getProductFormCode() {
		return productFormCode;
	}

	public void setProductFormCode(String productFormCode) {
		this.productFormCode = productFormCode;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getFreeTextSig() {
		return freeTextSig;
	}

	public void setFreeTextSig(String freeTextSig) {
		this.freeTextSig = freeTextSig;
	}

	public Date getMedicationStartDate() {
		return medicationStartDate;
	}

	public void setMedicationStartDate(Date medicationStartDate) {
		this.medicationStartDate = medicationStartDate;
	}

	public Date getMedicationEndDate() {
		return medicationEndDate;
	}

	public void setMedicationEndDate(Date medicationEndDate) {
		this.medicationEndDate = medicationEndDate;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public String getMedicationNote() {
		return medicationNote;
	}

	public void setMedicationNote(String medicationNote) {
		this.medicationNote = medicationNote;
	}

	public String getMedicationCodeDisplayName() {
		return medicationCodeDisplayName;
	}

	public void setMedicationCodeDisplayName(String medicationCodeDisplayName) {
		this.medicationCodeDisplayName = medicationCodeDisplayName;
	}

	public String getProductFormCodeDisplayName() {
		return productFormCodeDisplayName;
	}

	public void setProductFormCodeDisplayName(String productFormCodeDisplayName) {
		this.productFormCodeDisplayName = productFormCodeDisplayName;
	}

	public String getRouteCodeDisplayName() {
		return routeCodeDisplayName;
	}

	public void setRouteCodeDisplayName(String routeCodeDisplayName) {
		this.routeCodeDisplayName = routeCodeDisplayName;
	}
	
	
}
