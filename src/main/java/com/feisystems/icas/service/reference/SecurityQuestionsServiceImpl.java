package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.SecurityQuestionsCode;
import com.feisystems.icas.domain.reference.SecurityQuestionsRepository;
import com.feisystems.icas.exceptions.DataNotFoundException;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class SecurityQuestionsServiceImpl implements SecurityQuestionsService {
	
	@Autowired
	SecurityQuestionsRepository repository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllSecurityQuestions() {
		
		List<LookupDto> lookups = new ArrayList<>();
		List<SecurityQuestionsCode> securityQuestionsCodeList = repository.findAll(); 
		
		for(SecurityQuestionsCode e : securityQuestionsCodeList) {
			lookups.add(modelMapper.map(e, LookupDto.class));
		}
		
		if(lookups == null || lookups.size() == 0) {
			throw new DataNotFoundException("Data not found");
		}
		
		return lookups;
	}

}
