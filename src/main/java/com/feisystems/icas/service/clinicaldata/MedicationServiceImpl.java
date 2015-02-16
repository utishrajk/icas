package com.feisystems.icas.service.clinicaldata;

import com.feisystems.icas.domain.clinicaldata.Medication;
import com.feisystems.icas.domain.clinicaldata.MedicationRepository;
import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.MedicationDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

	@Autowired
    MedicationRepository medicationRepository;
	
	@Autowired
    PatientRepository patientRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private DtoToDomainEntityMapper<MedicationDto, Medication> medicationDtoToMedicationMapper;
	

	public long countAllMedications() {
        return medicationRepository.count();
    }

	public void deleteMedication(MedicationDto medicationDto) {
        medicationRepository.delete(medicationDto.getId());
    }

	public MedicationDto findMedication(Long id) {
        
        Medication medication = medicationRepository.findOne(id);

		if (medication != null) {
			return modelMapper.map(medication, MedicationDto.class);
		}
		throw new ClinicalDataNotFoundException(id);
		
    }

	public List<MedicationDto> findAllMedications() {
        
        List<MedicationDto> medicationDtoList = new ArrayList<MedicationDto>();
		List<Medication> medicationList = medicationRepository.findAll();

		if (medicationList != null) {
			for (Medication medication : medicationList) {
				MedicationDto medicationDto = modelMapper.map(medication, MedicationDto.class);
				medicationDtoList.add(medicationDto);
			}
			return medicationDtoList;
		}

		throw new IllegalArgumentException("No Problems Found");
		
    }


	public void saveMedication(MedicationDto medicationDto) {
		Medication medication = medicationDtoToMedicationMapper.map(medicationDto);
        medicationRepository.save(medication);
    }

	public Medication updateMedication(MedicationDto medicationDto) {
        
        Medication medication = medicationDtoToMedicationMapper.map(medicationDto);

		if (medication == null) {
			throw new ClinicalDataNotFoundException(1L);
		}

		return  medicationRepository.save(medication);
    }

	@Override
	public List<MedicationDto> findAllMedicationByPatientId(Long patientId) {
		
		List<MedicationDto> medicationDtoList = new ArrayList<>();
		Patient patient = patientRepository.findOne(patientId);

		Set<Medication> medicationList = patient.getMedications();

		for (Medication medication : medicationList) {
			MedicationDto dto = modelMapper.map(medication, MedicationDto.class);
			medicationDtoList.add(dto);
		}

		return medicationDtoList;
		
	}
}
