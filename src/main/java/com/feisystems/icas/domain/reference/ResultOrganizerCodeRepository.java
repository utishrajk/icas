package com.feisystems.icas.domain.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface ResultOrganizerCodeRepository extends JpaSpecificationExecutor<ResultOrganizerCode>, JpaRepository<ResultOrganizerCode, Long> {
	public abstract ResultOrganizerCode findByCode(String code);	
}
