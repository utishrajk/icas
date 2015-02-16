package com.feisystems.icas.domain.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergySeverityCodeRepository extends JpaSpecificationExecutor<AllergySeverityCode>, JpaRepository<AllergySeverityCode, Long> {

	public abstract AllergySeverityCode findByCode(String code);

}
