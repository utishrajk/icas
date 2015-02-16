package com.feisystems.icas.service.clinicaldata;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.clinicaldata.Intervention;
import com.feisystems.icas.service.dto.InterventionDto;

@Component
public class InterventionToInterventionDtoAbstractabstractConverter extends AbstractConverter<Set<Intervention>, Set<InterventionDto>>{

	public InterventionToInterventionDtoAbstractabstractConverter() {
	}
	
	@Override
	protected Set<InterventionDto> convert(Set<Intervention> InterventionList) {
		
		Set<InterventionDto> interventionDtoList = new HashSet<InterventionDto>();
		  
		  for(Intervention intervention : InterventionList){
			  InterventionDto interventionDto = new InterventionDto();
			  interventionDto.setCptCode(intervention.getCptCode());
			  interventionDto.setDescription(intervention.getDescription());
			  interventionDto.setNotes(intervention.getNotes());
			  interventionDto.setResolutionDate(intervention.getResolutionDate());
			  interventionDto.setTargetDate(intervention.getTargetDate());
			  interventionDtoList.add(interventionDto);
		  }
		  
		return interventionDtoList;
	}
}
