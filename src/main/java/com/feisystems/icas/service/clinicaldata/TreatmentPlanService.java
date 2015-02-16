package com.feisystems.icas.service.clinicaldata;

import java.util.List;

import com.feisystems.icas.service.dto.PatientProfileDto;
import com.feisystems.icas.service.dto.TreatmentPlanDto;

public interface TreatmentPlanService {

	/**
	 * Find all patients
	 * 
	 * @return list of all patient profile dto
	 */
	public List<TreatmentPlanDto> findAllTreatmentPlans();

	/**
	 * Save Patient
	 * 
	 * @param patientProfileDto
	 *            the patient profile dto
	 */
	public void saveTreatmentPlan(TreatmentPlanDto treatmentPlanDto);

	/**
	 * Find TreatmentPlan for an id
	 * 
	 * @param id
	 */
	public TreatmentPlanDto findTreatmentPlan(Long id);
	
	
	public void deleteTreatmentPlan(TreatmentPlanDto treatmentPlanDto);


}
