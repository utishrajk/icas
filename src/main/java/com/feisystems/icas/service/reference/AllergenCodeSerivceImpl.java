package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.AllergenCode;
import com.feisystems.icas.domain.reference.AllergenCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class AllergenCodeSerivceImpl implements AllergenCodeService {

	@Autowired
	AllergenCodeRepository allergenCodeRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<LookupDto> findAllAllergenCodes() {

		List<LookupDto> lookups = new ArrayList<>();

		List<AllergenCode> allergenCodeList = allergenCodeRepository.findAll();

		for (AllergenCode entity : allergenCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}

		return lookups;
	}
}
