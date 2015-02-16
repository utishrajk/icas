package com.feisystems.icas.domain.clinicaldata;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.reference.ActStatusCode;
import com.feisystems.icas.domain.reference.AdverseEventTypeCode;
import com.feisystems.icas.domain.reference.AllergenCode;
import com.feisystems.icas.domain.reference.AllergyReactionCode;
import com.feisystems.icas.domain.reference.AllergySeverityCode;

@Entity
public class Allergy implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	@ManyToOne
	private AdverseEventTypeCode adverseEventTypeCode;

	@ManyToOne
	private AllergenCode allergenCode;

	@ManyToOne
	private AllergyReactionCode allergyReactionCode;

	@ManyToOne
	private ActStatusCode allergyStatusCode;

	@ManyToOne
	private AllergySeverityCode allergySeverityCode;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date allergyStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date allergyEndDate;

	private String note;

	@ManyToOne
	private Patient patient;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public AdverseEventTypeCode getAdverseEventTypeCode() {
		return adverseEventTypeCode;
	}

	public void setAdverseEventTypeCode(AdverseEventTypeCode adverseEventTypeCode) {
		this.adverseEventTypeCode = adverseEventTypeCode;
	}

	public AllergenCode getAllergenCode() {
		return allergenCode;
	}

	public void setAllergenCode(AllergenCode allergenCode) {
		this.allergenCode = allergenCode;
	}

	public AllergyReactionCode getAllergyReactionCode() {
		return allergyReactionCode;
	}

	public void setAllergyReactionCode(AllergyReactionCode allergyReactionCode) {
		this.allergyReactionCode = allergyReactionCode;
	}

	public ActStatusCode getAllergyStatusCode() {
		return allergyStatusCode;
	}

	public void setAllergyStatusCode(ActStatusCode allergyStatusCode) {
		this.allergyStatusCode = allergyStatusCode;
	}

	public AllergySeverityCode getAllergySeverityCode() {
		return allergySeverityCode;
	}

	public void setAllergySeverityCode(AllergySeverityCode allergySeverityCode) {
		this.allergySeverityCode = allergySeverityCode;
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

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
