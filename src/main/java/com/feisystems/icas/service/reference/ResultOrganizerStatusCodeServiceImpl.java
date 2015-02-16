package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.ResultOrganizerCode;
import com.feisystems.icas.domain.reference.ResultOrganizerCodeRepository;
import com.feisystems.icas.domain.reference.ResultOrganizerStatusCode;
import com.feisystems.icas.domain.reference.ResultOrganizerStatusCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class ResultOrganizerStatusCodeServiceImpl implements
		ResultOrganizerStatusCodeService {
	
	@Autowired
	ResultOrganizerStatusCodeRepository resultOrganizerStatusCodeRepository;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper; 
	
	
	@Override
	public List<LookupDto> findAllResultOrganizerStatusCodes() {
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<ResultOrganizerStatusCode> resultOrganizerStatusCodeList = resultOrganizerStatusCodeRepository.findAll();

		for (ResultOrganizerStatusCode entity : resultOrganizerStatusCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}

}
