package com.feisystems.icas.service.clinicaldata;
import com.feisystems.icas.domain.clinicaldata.Medication;
import com.feisystems.icas.service.dto.MedicationDto;

import java.util.List;

public interface MedicationService {

	public abstract long countAllMedications();


	public abstract void deleteMedication(MedicationDto medicationDto);


	public abstract MedicationDto findMedication(Long id);


	public abstract List<MedicationDto> findAllMedications();


	public abstract void saveMedication(MedicationDto medicationDto);


	public abstract Medication updateMedication(MedicationDto medicationDto);
	
	public abstract  List<MedicationDto> findAllMedicationByPatientId(Long patientId);

}
