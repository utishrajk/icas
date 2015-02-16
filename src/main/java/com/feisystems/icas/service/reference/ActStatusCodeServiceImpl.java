package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.ActStatusCode;
import com.feisystems.icas.domain.reference.ActStatusCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class ActStatusCodeServiceImpl implements ActStatusCodeService {

	@Autowired
	ActStatusCodeRepository actStatusCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllActStatusCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<ActStatusCode> actStatusCodeList = actStatusCodeRepository.findAll();

		for (ActStatusCode entity : actStatusCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}

}
