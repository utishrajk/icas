package com.feisystems.icas.service.reference;

import java.util.List;

import com.feisystems.icas.service.dto.LookupDto;

public interface MedicationCodeOutcomesService {
	public abstract List<LookupDto> findAllMedicationCodeOutcomes();
}
