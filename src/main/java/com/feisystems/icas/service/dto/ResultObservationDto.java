package com.feisystems.icas.service.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResultObservationDto {
	
	private Long id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
    private Date resultObservationDateTime;
    
	private String resultObservationTypeCode;	
	
	private String resultObservationMeasureCode;

	private String resultObservationInterpretationCode;

	private String resultObservationValue;
	    
    private String resultReferenceRangeMin;
    
    private String resultReferenceRangeMax;
	
    private String resultObservationNote;
	

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

	public String getResultObservationValue() {
		return resultObservationValue;
	}

	public void setResultObservationValue(String resultObservationValue) {
		this.resultObservationValue = resultObservationValue;
	}

	public String getResultObservationInterpretationCode() {
		return resultObservationInterpretationCode;
	}

	public void setResultObservationInterpretationCode(
			String resultObservationInterpretationCode) {
		this.resultObservationInterpretationCode = resultObservationInterpretationCode;
	}

	public String getResultObservationTypeCode() {
		return resultObservationTypeCode;
	}

	public void setResultObservationTypeCode(String resultObservationTypeCode) {
		this.resultObservationTypeCode = resultObservationTypeCode;
	}

	public String getResultObservationMeasureCode() {
		return resultObservationMeasureCode;
	}

	public void setResultObservationMeasureCode(String resultObservationMeasureCode) {
		this.resultObservationMeasureCode = resultObservationMeasureCode;
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
	
	public void setResultObservationNote(String resultObservationNote) {
		this.resultObservationNote = resultObservationNote;
	}
	
	public String getResultObservationNote() {
		return resultObservationNote;
	}
	
}
