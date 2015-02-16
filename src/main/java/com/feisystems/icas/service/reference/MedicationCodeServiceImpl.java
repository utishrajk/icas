package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.MedicationCode;
import com.feisystems.icas.domain.reference.MedicationCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;
@Service
@Transactional
public class MedicationCodeServiceImpl implements MedicationCodeService {
	
	@Autowired
    MedicationCodeRepository medicationCodeRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<LookupDto> findAllMedicationCodes() {
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<MedicationCode> medicationCodeList = medicationCodeRepository.findAll();

		for (MedicationCode entity : medicationCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}
}
