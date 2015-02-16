package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.ResultObservationStatusCode;
import com.feisystems.icas.domain.reference.ResultObservationStatusCodeRepository;
import com.feisystems.icas.domain.reference.ResultObservationType;
import com.feisystems.icas.domain.reference.ResultObservationTypeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class ResultObservationTypeServiceImpl implements ResultObservationTypeService {
	@Autowired
	ResultObservationTypeRepository resultObservationTypeRepository;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper; 
	
	@Override
	public List<LookupDto> findAllResultObservationTypes() {
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<ResultObservationType> ResultObservationTypeList = resultObservationTypeRepository.findAll();

		for (ResultObservationType entity : ResultObservationTypeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}
	
	


}
