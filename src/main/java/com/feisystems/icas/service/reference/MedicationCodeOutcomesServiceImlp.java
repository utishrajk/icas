package com.feisystems.icas.service.reference;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.reference.MedicationCodeOutcomes;
import com.feisystems.icas.domain.reference.MedicationCodeOutcomesRepository;
import com.feisystems.icas.service.dto.LookupDto;

@Service
@Transactional
public class MedicationCodeOutcomesServiceImlp implements MedicationCodeOutcomesService {
	
	@Autowired
    MedicationCodeOutcomesRepository medicationCodeOutcomesRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<LookupDto> findAllMedicationCodeOutcomes() {
		
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<MedicationCodeOutcomes> medicationCodeOutcomesList = medicationCodeOutcomesRepository.findAll();

		for (MedicationCodeOutcomes entity : medicationCodeOutcomesList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;
	}	
}
