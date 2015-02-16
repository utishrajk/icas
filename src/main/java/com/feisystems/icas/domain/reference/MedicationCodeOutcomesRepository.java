package com.feisystems.icas.domain.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MedicationCodeOutcomesRepository extends JpaSpecificationExecutor<MedicationCodeOutcomes>, JpaRepository<MedicationCodeOutcomes, Long> {

	public abstract MedicationCodeOutcomes findByCode(String code);
}
