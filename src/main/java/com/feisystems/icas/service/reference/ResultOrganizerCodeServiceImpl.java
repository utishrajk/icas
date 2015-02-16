package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.ResultObservationType;
import com.feisystems.icas.domain.reference.ResultObservationTypeRepository;
import com.feisystems.icas.domain.reference.ResultOrganizerCode;
import com.feisystems.icas.domain.reference.ResultOrganizerCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class ResultOrganizerCodeServiceImpl implements
		ResultOrganizerCodeService {
	
	@Autowired
	ResultOrganizerCodeRepository resultOrganizerCodeRepository;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper; 
	
	@Override
	public List<LookupDto> findAllResultOrganizerCodes() {
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<ResultOrganizerCode> resultOrganizerCodeList = resultOrganizerCodeRepository.findAll();

		for (ResultOrganizerCode entity : resultOrganizerCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}

}
