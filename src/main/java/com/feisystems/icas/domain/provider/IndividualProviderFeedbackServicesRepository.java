package com.feisystems.icas.domain.provider;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualProviderFeedbackServicesRepository extends JpaRepository<IndividualProviderFeedbackServices, Long>, JpaSpecificationExecutor<IndividualProviderFeedbackServices> {
	
}

