package com.feisystems.icas.service.clinicaldata;

import java.util.List;

import com.feisystems.icas.domain.clinicaldata.Allergy;
import com.feisystems.icas.service.dto.AllergyDto;

public interface AllergyService {

	public abstract void deleteAllergy(AllergyDto allergy);

	public abstract AllergyDto findAllergy(Long id);

	public abstract void saveAllergy(AllergyDto allergy);

	public abstract Allergy updateAllergy(AllergyDto allergy);

	public abstract List<AllergyDto> findAllAllergysByPatientId(Long patientId);

}
