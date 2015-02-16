package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.AllergySeverityCode;
import com.feisystems.icas.domain.reference.AllergySeverityCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class AllergySeverityCodeServiceImpl implements AllergySeverityCodeService {

	@Autowired
	AllergySeverityCodeRepository allergySeverityCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllAllergySeverityCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<AllergySeverityCode> allergySeverityCodeList = allergySeverityCodeRepository.findAll();

		for (AllergySeverityCode entity : allergySeverityCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}

}
