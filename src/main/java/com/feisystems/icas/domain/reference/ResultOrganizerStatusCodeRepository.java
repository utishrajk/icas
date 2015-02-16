package com.feisystems.icas.domain.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultOrganizerStatusCodeRepository extends JpaSpecificationExecutor<ResultOrganizerStatusCode>, JpaRepository<ResultOrganizerStatusCode, Long> {

	public abstract ResultOrganizerStatusCode findByCode(String code);	
	
}

