package com.feisystems.icas.service.patient;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.patient.Patient;
import com.feisystems.icas.domain.patient.PatientRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.PatientProfileDto;

@Service
@Transactional(rollbackFor = { ClinicalDataNotFoundException.class })
public class PatientServiceImpl implements PatientService {

	/** The patient repository. */
	@Autowired
    PatientRepository patientRepository;
	
	/** The model mapper. */
	@Autowired
	private ModelMapper modelMapper;

	/** The patient profile dto to patient mapper. */
	@Autowired
	private DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper;
	
	/** {@inheritDoc} */
	public PatientProfileDto findPatient(Long id) {
		Patient patient = patientRepository.findOne(id);
		if(patient != null) {
			return modelMapper.map(patient, PatientProfileDto.class);
		}
		throw new ClinicalDataNotFoundException(id);
    }
	
	/** {@inheritDoc} */
	public List<PatientProfileDto> findAllPatients() {
		List<PatientProfileDto> patientProfileDtoList = new ArrayList<>();
        List<Patient> patientList =  patientRepository.findAll();
        
        if (patientList != null &&  patientList.size() > 0) {
            for(Patient patient: patientList) {
            	PatientProfileDto patientDto = modelMapper.map(patient, PatientProfileDto.class);
            	patientProfileDtoList.add(patientDto);
            }
            return patientProfileDtoList;
        }
		throw new IllegalArgumentException("No Patients Found");
	}

	/** {@inheritDoc} */
	public void savePatient(PatientProfileDto patientDto) {
		Patient patient = patientProfileDtoToPatientMapper.map(patientDto);
		patientRepository.save(patient);
	}

	/** {@inheritDoc} */
	public Patient updatePatient(PatientProfileDto patientDto) {
		Patient patient = patientProfileDtoToPatientMapper.map(patientDto);
		if (patient != null) {
			return patientRepository.save(patient);	
		} 
		throw new ClinicalDataNotFoundException(patientDto.getId());
    }

	/** {@inheritDoc} */
	public void deletePatient(PatientProfileDto patientProfileDto) {
        patientRepository.delete(patientProfileDto.getId());
    }
	
}
