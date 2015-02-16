package com.feisystems.icas.service.clinicaldata;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.clinicaldata.TreatmentPlan;
import com.feisystems.icas.domain.clinicaldata.TreatmentPlanRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.TreatmentPlanDto;

@Service
@Transactional(rollbackFor = { ClinicalDataNotFoundException.class })
public class TreatmentPlanServiceImpl implements TreatmentPlanService {

	/** The treatmentPlan repository. */
	@Autowired
	TreatmentPlanRepository treatmentPlanRepository;

	/** The model mapper. */
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	InterventionToInterventionDtoAbstractabstractConverter interventionToInterventionDtoAbstractabstractConverter;

	/** The treatmentPlan profile dto to treatmentPlan mapper. */
	@Autowired
	private DtoToDomainEntityMapper<TreatmentPlanDto, TreatmentPlan> treatmentPlanDtoToTreatmentPlanMapper;

	/** {@inheritDoc} */
	public List<TreatmentPlanDto> findAllTreatmentPlans() {

		modelMapper.addConverter(interventionToInterventionDtoAbstractabstractConverter);

		List<TreatmentPlanDto> treatmentPlanDtoList = new ArrayList<>();
		List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();

		if (treatmentPlanList != null && treatmentPlanList.size() > 0) {
			for (TreatmentPlan treatmentPlan : treatmentPlanList) {
				TreatmentPlanDto treatmentPlanDto = modelMapper.map(treatmentPlan, TreatmentPlanDto.class);
				treatmentPlanDtoList.add(treatmentPlanDto);
			}
		}
		return treatmentPlanDtoList;
	}

	/** {@inheritDoc} */
	public void saveTreatmentPlan(TreatmentPlanDto treatmentPlanDto) {
		TreatmentPlan treatmentPlan = treatmentPlanDtoToTreatmentPlanMapper.map(treatmentPlanDto);
		treatmentPlanRepository.save(treatmentPlan);
	}

	/** {@inheritDoc} */
	@Override
	public TreatmentPlanDto findTreatmentPlan(Long id) {
		TreatmentPlan treatmentPlan = treatmentPlanRepository.findOne(id);
		if (treatmentPlan != null) {
			return modelMapper.map(treatmentPlan, TreatmentPlanDto.class);
		}
		throw new ClinicalDataNotFoundException(id);
	}

	@Override
	public void deleteTreatmentPlan(TreatmentPlanDto treatmentPlanDto) {
		treatmentPlanRepository.delete(treatmentPlanDto.getId());		
	}

}
