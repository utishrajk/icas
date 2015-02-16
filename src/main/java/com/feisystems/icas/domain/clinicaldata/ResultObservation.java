package com.feisystems.icas.domain.clinicaldata;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.feisystems.icas.domain.reference.ResultInterpretationCode;
import com.feisystems.icas.domain.reference.ResultObservationType;
import com.feisystems.icas.domain.reference.UnitOfMeasureCode;

@Entity
public class ResultObservation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date resultObservationDateTime;

	@ManyToOne
	private ResultObservationType resultObservationTypeCode;

	@ManyToOne
	private UnitOfMeasureCode resultObservationMeasureCode;

	@ManyToOne
	private ResultInterpretationCode resultObservationInterpretationCode;

	private String resultObservationValue;

	private String resultReferenceRangeMin;

	private String resultReferenceRangeMax;

	private String resultObservationNote;
	
	@ManyToOne
	@JoinColumn(name = "resultOrganizerId")
	private ResultOrganizer resultOrganizer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getResultObservationDateTime() {
		return resultObservationDateTime;
	}

	public void setResultObservationDateTime(Date resultObservationDateTime) {
		this.resultObservationDateTime = resultObservationDateTime;
	}

	public ResultObservationType getResultObservationTypeCode() {
		return resultObservationTypeCode;
	}

	public void setResultObservationTypeCode(
			ResultObservationType resultObservationTypeCode) {
		this.resultObservationTypeCode = resultObservationTypeCode;
	}

	public String getResultObservationValue() {
		return resultObservationValue;
	}

	public void setResultObservationValue(String resultObservationValue) {
		this.resultObservationValue = resultObservationValue;
	}

	public String getResultReferenceRangeMin() {
		return resultReferenceRangeMin;
	}

	public void setResultReferenceRangeMin(String resultReferenceRangeMin) {
		this.resultReferenceRangeMin = resultReferenceRangeMin;
	}

	public String getResultReferenceRangeMax() {
		return resultReferenceRangeMax;
	}

	public void setResultReferenceRangeMax(String resultReferenceRangeMax) {
		this.resultReferenceRangeMax = resultReferenceRangeMax;
	}

	public String getResultObservationNote() {
		return resultObservationNote;
	}

	public void setResultObservationNote(String resultObservationNote) {
		this.resultObservationNote = resultObservationNote;
	}

	public UnitOfMeasureCode getResultObservationMeasureCode() {
		return resultObservationMeasureCode;
	}

	public void setResultObservationMeasureCode(
			UnitOfMeasureCode resultObservationMeasureCode) {
		this.resultObservationMeasureCode = resultObservationMeasureCode;
	}

	public ResultInterpretationCode getResultObservationInterpretationCode() {
		return resultObservationInterpretationCode;
	}

	public void setResultObservationInterpretationCode(
			ResultInterpretationCode resultObservationInterpretationCode) {
		this.resultObservationInterpretationCode = resultObservationInterpretationCode;
	}
	
	
	public void setResultOrganizer(ResultOrganizer resultOrganizer) {
		this.resultOrganizer = resultOrganizer;
	}
	
	public ResultOrganizer getResultOrganizer() {
		return resultOrganizer;
	}

}
