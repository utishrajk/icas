package com.feisystems.icas.service.provider.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServices;
import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServicesRepository;
import com.feisystems.icas.domain.reference.FeedbackRatingsCode;
import com.feisystems.icas.domain.reference.FeedbackRatingsCodeRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;

/**
 * The Class IndividualProviderDtoToIndividualProviderMapper.
 */
@Component
public class IndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper implements
		DtoToDomainEntityMapper<IndividualProviderFeedbackServiceDto, IndividualProviderFeedbackServices> {

	private IndividualProviderFeedbackServicesRepository  individualProviderFeedbackServicesRepository;

	private FeedbackRatingsCodeRepository ratingsRepository;
	/**
	 * Instantiates a new individualProviderFeedbackServices dto to individualProviderFeedbackServices mapper.
	 * @param individualProviderRepository the individualProviderFeedbackServices repository
	 */
	@Autowired
	public IndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper(
			IndividualProviderFeedbackServicesRepository individualProviderFeedbackServicesRepository,
			FeedbackRatingsCodeRepository ratingsRepository) {
		
		super();
		
		this.individualProviderFeedbackServicesRepository = individualProviderFeedbackServicesRepository;
		this.ratingsRepository = ratingsRepository;
	}

	/* (non-Javadoc)
	 * @see com.feisystems.icas.infrastructure.DtoToDomainEntityMapper#map(java.lang.Object)
	 */
	@Override
	public IndividualProviderFeedbackServices map(IndividualProviderFeedbackServiceDto individualProviderServiceDto) {
		IndividualProviderFeedbackServices individualProviderFeedbackServices = null;

		if (individualProviderServiceDto.getId() != null) {
			individualProviderFeedbackServices = individualProviderFeedbackServicesRepository.findOne(individualProviderServiceDto.getId());
		} else {
			individualProviderFeedbackServices = new IndividualProviderFeedbackServices();
		}

		// Return null if no IndividualProviderFeedbackServices found
		if (individualProviderFeedbackServices == null) {
			return null;
		}
		
		FeedbackRatingsCode rating = ratingsRepository.findOne(individualProviderServiceDto.getRating_id());
		individualProviderFeedbackServices.setRatings(rating);
		
		return individualProviderFeedbackServices;
	}
}
