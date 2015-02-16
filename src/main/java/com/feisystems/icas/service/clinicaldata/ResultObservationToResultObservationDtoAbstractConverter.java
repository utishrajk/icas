package com.feisystems.icas.service.clinicaldata;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.clinicaldata.ResultObservation;
import com.feisystems.icas.service.dto.ResultObservationDto;

@Component
public class ResultObservationToResultObservationDtoAbstractConverter extends AbstractConverter<Set<ResultObservation>, Set<ResultObservationDto>>{
	
	public ResultObservationToResultObservationDtoAbstractConverter() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected Set<ResultObservationDto> convert(Set<ResultObservation> resultObservationList) {
		
		Set<ResultObservationDto> resultObservationDtoList = new HashSet<ResultObservationDto>();
		  
		  for(ResultObservation resultObservation : resultObservationList){
			  ResultObservationDto resultObservationDto = new ResultObservationDto();
			  resultObservationDto.setId(resultObservation.getId());
			  resultObservationDto.setResultObservationDateTime(resultObservation.getResultObservationDateTime());
			  resultObservationDto.setResultObservationInterpretationCode(resultObservation.getResultObservationInterpretationCode().getCode());
			  resultObservationDto.setResultObservationMeasureCode(resultObservation.getResultObservationMeasureCode().getCode());
			  resultObservationDto.setResultObservationNote(resultObservation.getResultObservationNote());
			  resultObservationDto.setResultObservationTypeCode(resultObservation.getResultObservationTypeCode().getCode());
			  resultObservationDto.setResultObservationValue(resultObservation.getResultObservationValue());
			  resultObservationDto.setResultReferenceRangeMax(resultObservation.getResultReferenceRangeMax());
			  resultObservationDto.setResultReferenceRangeMin(resultObservation.getResultReferenceRangeMin());
			  resultObservationDtoList.add(resultObservationDto);
		  }
		  
		return resultObservationDtoList;
	}

}
