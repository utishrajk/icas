package com.feisystems.icas.service.reference;

import com.feisystems.icas.domain.reference.ProviderTaxonomyCode;
import com.feisystems.icas.domain.reference.ProviderTaxonomyCodeRepository;
import com.feisystems.icas.service.dto.LookupDto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProviderTaxonomyCodeServiceImpl implements ProviderTaxonomyCodeService {

	@Autowired
    ProviderTaxonomyCodeRepository providerTaxonomyCodeRepository;
	
	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;


	public List<LookupDto> findAllProviderTaxonomyCodes() {
		
		List<LookupDto> lookups = new ArrayList<LookupDto>();

		List<ProviderTaxonomyCode> providerTaxonomyCodeList = providerTaxonomyCodeRepository.findAll();

		for (ProviderTaxonomyCode entity : providerTaxonomyCodeList) {
			lookups.add(modelMapper.map(entity, LookupDto.class));
		}
		return lookups;

		
    }

}
