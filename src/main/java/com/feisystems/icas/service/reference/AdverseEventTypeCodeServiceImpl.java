package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.AdverseEventTypeCode;
import com.feisystems.icas.domain.reference.AdverseEventTypeCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class AdverseEventTypeCodeServiceImpl implements AdverseEventTypeCodeService {

	@Autowired
	AdverseEventTypeCodeRepository adverseEventTypeCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllAdverseEventTypeCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<AdverseEventTypeCode> adverseEventTypeCodeList = adverseEventTypeCodeRepository.findAll();

		for (AdverseEventTypeCode entity : adverseEventTypeCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}

}
