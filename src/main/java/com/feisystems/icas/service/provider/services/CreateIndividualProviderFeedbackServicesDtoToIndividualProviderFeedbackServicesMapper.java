package com.feisystems.icas.service.provider.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.provider.CustomFeedbackRepositoryImpl;
import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.domain.provider.IndividualProviderRepository;
import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServices;
import com.feisystems.icas.domain.reference.FeedbackRatingsCode;
import com.feisystems.icas.domain.reference.FeedbackRatingsCodeRepository;
import com.feisystems.icas.domain.reference.FeedbackServicesCode;
import com.feisystems.icas.domain.reference.FeedbackServicesCodeRepository;
import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;

/**
 * The Class CreateIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper.
 */
@Component
public class CreateIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper {
	
	@Autowired
	CustomFeedbackRepositoryImpl individualProviderFeedbackServices;

	/** The individualProviderRepository repository. */
	private IndividualProviderRepository individualProviderRepository;
	
	private FeedbackServicesCodeRepository servicesRepository;
	
	private FeedbackRatingsCodeRepository ratingsRepository;

	
	/**
	 * Instantiates a new individualProviderFeedbackServices dto to individualProviderFeedbackServices mapper.
	 * @param individualProviderRepository the individualProviderFeedbackServices repository
	 */
	@Autowired
	public CreateIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper(
			IndividualProviderRepository individualProviderRepository,
			FeedbackServicesCodeRepository servicesRepository,
			FeedbackRatingsCodeRepository ratingsRepository) {
		
		super();
		
		this.individualProviderRepository = individualProviderRepository;
		this.servicesRepository = servicesRepository;
		this.ratingsRepository = ratingsRepository;
	}

	public List<IndividualProviderFeedbackServices> createAndAssignServices(IndividualProviderFeedbackServiceDto individualProviderServiceDto) {
		List<Object[]> individualProviderServiceList = individualProviderFeedbackServices.findIps(individualProviderServiceDto.getIndividualprovider_id());
		IndividualProviderFeedbackServices individualProviderService = null;
		List<IndividualProviderFeedbackServices> individualProviderFeedbackServicesList = new ArrayList<IndividualProviderFeedbackServices>();

		// Allow to add services only if there are no services registered 
		if (individualProviderServiceList == null || individualProviderServiceList.isEmpty()) {
			IndividualProvider individualProvider =	individualProviderRepository.findOne(individualProviderServiceDto.getIndividualprovider_id());
			FeedbackRatingsCode rating = ratingsRepository.findOne(6L); // 6 == No Rating
			
			List<FeedbackServicesCode> services  = servicesRepository.findAll();
			for (FeedbackServicesCode service: services) {
				individualProviderService = new IndividualProviderFeedbackServices();
				individualProviderService.setServices(service);
				individualProviderService.setRatings(rating);
				individualProviderService.setIndividualProvider(individualProvider);
				individualProviderFeedbackServicesList.add(individualProviderService);
			}
		}
		return individualProviderFeedbackServicesList;
	}

}
