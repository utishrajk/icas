package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.AllergyReactionCode;
import com.feisystems.icas.domain.reference.AllergyReactionCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class AllergyReactionCodeServiceImpl implements AllergyReactionCodeService {

	@Autowired
	AllergyReactionCodeRepository allergyReactionCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllAllergyReactionCodes() {
		List<LookupDto> lookups = new ArrayList<>();

		List<AllergyReactionCode> allergyReactionCodeList = allergyReactionCodeRepository.findAll();

		for (AllergyReactionCode entity : allergyReactionCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}

}
