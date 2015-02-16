package com.feisystems.icas.service.provider.services;

import java.util.List;

import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServices;
import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;

public interface IndividualProviderFeedbackServicesService {

	public abstract void saveIndividualProviderFeedbackServices(IndividualProviderFeedbackServiceDto IndividualProviderServiceDto);

	public abstract IndividualProviderFeedbackServices updateIndividualProviderService(IndividualProviderFeedbackServiceDto individualProviderServiceDto);
	
	public List<IndividualProviderFeedbackServiceDto> findServicesByIndividualProviderId(Long id);
	
}
