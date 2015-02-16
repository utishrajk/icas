package com.feisystems.icas.domain.clinicaldata;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Intervention implements Serializable {

	private static final long serialVersionUID = -7324827311626617328L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    private TreatmentPlan treatmentplan;
    
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date targetDate;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date resolutionDate;

	private String cptCode;
    
    private String description;
    
    private String notes;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TreatmentPlan getTreatmentplan() {
		return treatmentplan;
	}

	public void setTreatmentplan(TreatmentPlan treatmentplan) {
		this.treatmentplan = treatmentplan;
	}

	public String getCptCode() {
		return cptCode;
	}

	public void setCptCode(String cptCode) {
		this.cptCode = cptCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public Date getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

}
