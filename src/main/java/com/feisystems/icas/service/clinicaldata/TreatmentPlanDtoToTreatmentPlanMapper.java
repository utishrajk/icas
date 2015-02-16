package com.feisystems.icas.service.clinicaldata;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.clinicaldata.Intervention;
import com.feisystems.icas.domain.clinicaldata.TreatmentPlan;
import com.feisystems.icas.domain.clinicaldata.TreatmentPlanRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.InterventionDto;
import com.feisystems.icas.service.dto.TreatmentPlanDto;

/**
 * The Class TreatmentPlanDtoToTreatmentPlanMapper.
 */
@Component
public class TreatmentPlanDtoToTreatmentPlanMapper implements
		DtoToDomainEntityMapper<TreatmentPlanDto, TreatmentPlan> {

	/** The treatmentPlan repository. */
	private TreatmentPlanRepository treatmentPlanRepository;

	@Autowired
	public TreatmentPlanDtoToTreatmentPlanMapper(
			TreatmentPlanRepository treatmentPlanRepository) {

		super();

		this.treatmentPlanRepository = treatmentPlanRepository;
	}

	/* (non-Javadoc)
	 * @see com.feisystems.icas.infrastructure.DtoToDomainEntityMapper#map(java.lang.Object)
	 */
	@Override
	public TreatmentPlan map(TreatmentPlanDto treatmentPlanDto) {
		TreatmentPlan treatmentPlan = null;

		if (treatmentPlanDto.getId() != null) {
			treatmentPlan = treatmentPlanRepository.findOne(treatmentPlanDto.getId());
		}
		else {
			treatmentPlan = new TreatmentPlan();
		}

		// Return null if no TreatmentPlan found
		if (treatmentPlan == null) {
			return null;
		}

		Set<InterventionDto> interventionDtos = treatmentPlanDto.getInterventions();
		for (InterventionDto interventionDto : interventionDtos) {
			Intervention intervention = new Intervention();
			intervention.setCptCode(interventionDto.getCptCode());
			intervention.setDescription(interventionDto.getDescription());
			intervention.setNotes(interventionDto.getNotes());
			intervention.setResolutionDate(interventionDto.getResolutionDate());
			intervention.setTargetDate(interventionDto.getTargetDate());
			intervention.setTreatmentplan(treatmentPlan);
			treatmentPlan.getInterventions().add(intervention);
		}

		treatmentPlan.setPlanCreateTime(new Date());
		treatmentPlan.setPrimaryDiagnosis(treatmentPlanDto.getPrimaryDiagnosis());
		treatmentPlan.setEvidence(treatmentPlanDto.getEvidence());
		treatmentPlan.setLongTermGoal(treatmentPlanDto.getLongTermGoal());
		treatmentPlan.setShortTermGoal(treatmentPlanDto.getShortTermGoal());
		treatmentPlan.setGoalsNote(treatmentPlanDto.getGoalsNote());
		treatmentPlan.setObjectivesNote(treatmentPlanDto.getObjectivesNote());
		treatmentPlan.setObjectives(treatmentPlanDto.getObjectives());

		return treatmentPlan;
	}

}
