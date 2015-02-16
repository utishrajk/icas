package com.feisystems.icas.service.dto;

public class QuantityTypeDto {
	
    private Double measuredValue;

    private String unitOfMeasureCode;

	public Double getMeasuredValue() {
		return measuredValue;
	}

	public void setMeasuredValue(Double measuredValue) {
		this.measuredValue = measuredValue;
	}

	public String getUnitOfMeasureCode() {
		return unitOfMeasureCode;
	}

	public void setUnitOfMeasureCode(String unitOfMeasureCode) {
		this.unitOfMeasureCode = unitOfMeasureCode;
	}
    
    
}
