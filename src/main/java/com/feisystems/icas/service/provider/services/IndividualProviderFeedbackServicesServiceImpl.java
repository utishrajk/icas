package com.feisystems.icas.service.provider.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.provider.CustomFeedbackRepositoryImpl;
import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServices;
import com.feisystems.icas.domain.provider.IndividualProviderFeedbackServicesRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;

@Service
@Transactional
public class IndividualProviderFeedbackServicesServiceImpl implements IndividualProviderFeedbackServicesService {
	
	@Autowired
    IndividualProviderFeedbackServicesRepository individualProviderFeedbackServicesRepository;
	
	@Autowired
	CustomFeedbackRepositoryImpl individualProviderFeedbackServices;
	
	/** The individualProvider profile dto to individualProvider mapper. */
	@Autowired
	private DtoToDomainEntityMapper<IndividualProviderFeedbackServiceDto, IndividualProviderFeedbackServices> individualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper;

	@Autowired
	private CreateIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper createIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper;
	
	/** {@inheritDoc} */
	public List<IndividualProviderFeedbackServiceDto> findServicesByIndividualProviderId(Long id) {
		List<IndividualProviderFeedbackServiceDto> individualProviderProfileDtoList = new ArrayList<>();
		List<Object[]> individualProviderList =  individualProviderFeedbackServices.findIps(id);
		
        if (individualProviderList != null &&  individualProviderList.size() > 0) {
            for(Object[] result: individualProviderList) {
            	IndividualProviderFeedbackServiceDto individualProviderServiceDto = new IndividualProviderFeedbackServiceDto();
            	individualProviderServiceDto.setService_id(((BigInteger)result[0]).longValue());
            	individualProviderServiceDto.setName(result[1].toString());
            	individualProviderServiceDto.setRating_id(((BigInteger)result[2]).longValue());
            	individualProviderServiceDto.setId(((BigInteger)result[3]).longValue());
            	individualProviderServiceDto.setIndividualprovider_id(((BigInteger)result[4]).longValue());
            	individualProviderProfileDtoList.add(individualProviderServiceDto);
            }
            return individualProviderProfileDtoList;
        }
		throw new IllegalArgumentException("No Services Found");
	}
	
	@Override
	public void saveIndividualProviderFeedbackServices(IndividualProviderFeedbackServiceDto individualProviderServiceDto) {
		List<IndividualProviderFeedbackServices> individualProviderFeedbackServices = createIndividualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper.createAndAssignServices(individualProviderServiceDto);
		individualProviderFeedbackServicesRepository.save(individualProviderFeedbackServices);
	}

	@Override
	public IndividualProviderFeedbackServices updateIndividualProviderService(IndividualProviderFeedbackServiceDto individualProviderServiceDto) {
		IndividualProviderFeedbackServices individualProviderFeedbackServices = individualProviderFeedbackServicesDtoToIndividualProviderFeedbackServicesMapper.map(individualProviderServiceDto);
		if (individualProviderFeedbackServices != null) {
			return individualProviderFeedbackServicesRepository.save(individualProviderFeedbackServices);
		}
		throw new ClinicalDataNotFoundException(individualProviderServiceDto.getId());
	}
	
}
