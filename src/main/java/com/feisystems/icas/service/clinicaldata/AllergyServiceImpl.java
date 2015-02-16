package com.feisystems.icas.service.clinicaldata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.clinicaldata.Allergy;
import com.feisystems.icas.domain.clinicaldata.AllergyRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.AllergyDto;

@Service
@Transactional
public class AllergyServiceImpl implements AllergyService {

	@Autowired
	AllergyRepository allergyRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DtoToDomainEntityMapper<AllergyDto, Allergy> allergyDtoToAllergyMapper;

	@Override
	public void deleteAllergy(AllergyDto dto) {
		allergyRepository.delete(dto.getId());
	}

	@Override
	public AllergyDto findAllergy(Long id) {
		Allergy allergy = allergyRepository.findOne(id);
		if (allergy != null) {
			return modelMapper.map(allergy, AllergyDto.class);
		}
		throw new ClinicalDataNotFoundException(id);
	}

	@Override
	public void saveAllergy(AllergyDto dto) {
		Allergy allergy = allergyDtoToAllergyMapper.map(dto);
		try {
			allergyRepository.save(allergy);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Allergy updateAllergy(AllergyDto dto) {
		Allergy allergy = allergyDtoToAllergyMapper.map(dto);
		if (dto != null) {
			return allergyRepository.save(allergy);
		}
		throw new ClinicalDataNotFoundException(dto.getId());
	}

	@Override
	public List<AllergyDto> findAllAllergysByPatientId(Long patientId) {
		List<AllergyDto> allergyDtoList = new ArrayList<>();
		Patient patient = patientRepository.findOne(patientId);

		Set<Allergy> allergyList = patient.getAllergies();

		for (Allergy allergy : allergyList) {
			AllergyDto dto = modelMapper.map(allergy, AllergyDto.class);
			allergyDtoList.add(dto);
		}

		return allergyDtoList;
	}
}
