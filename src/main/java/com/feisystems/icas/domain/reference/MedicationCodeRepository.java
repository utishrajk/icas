package com.feisystems.icas.domain.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MedicationCodeRepository extends JpaSpecificationExecutor<MedicationCode>, JpaRepository<MedicationCode, Long> {

	public abstract MedicationCode findByCode(String code);
}

