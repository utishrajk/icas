package com.feisystems.icas.service.provider;

import com.feisystems.icas.domain.provider.OrganizationalProvider;
import com.feisystems.icas.domain.provider.OrganizationalProviderRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.OrganizationalProviderDto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganizationalProviderServiceImpl implements OrganizationalProviderService {
	
	@Autowired
    OrganizationalProviderRepository organizationalProviderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	SetOfObjectToSetOfStringContverter setOfObjectToSetOfStringContverter;
	/** The individualProvider profile dto to individualProvider mapper. */
	@Autowired
	private DtoToDomainEntityMapper<OrganizationalProviderDto, OrganizationalProvider> organizationalProviderDtoToOrganizationalProviderMapper;

	
	public long countAllOrganizationalProviders() {
        return organizationalProviderRepository.count();
    }

	public void deleteOrganizationalProvider(OrganizationalProvider organizationalProvider) {
        organizationalProviderRepository.delete(organizationalProvider);
    }

	public List<OrganizationalProviderDto> findAllOrganizationalProviders() {
		List<OrganizationalProviderDto> OrganizationalProviderProviderProfileDtoList = new ArrayList<>();
        List<OrganizationalProvider> OrganizationalProviderProviderList =  organizationalProviderRepository.findAll();
        
        if (OrganizationalProviderProviderList != null &&  OrganizationalProviderProviderList.size() > 0) {
            for(OrganizationalProvider organizationalProvider: OrganizationalProviderProviderList) {
            	OrganizationalProviderDto organizationalProviderDto = modelMapper.map(organizationalProvider, OrganizationalProviderDto.class);
            	OrganizationalProviderProviderProfileDtoList.add(organizationalProviderDto);
            }
            return OrganizationalProviderProviderProfileDtoList;
        }
		throw new IllegalArgumentException("No Care Manager Found");
    }

	public List<OrganizationalProvider> findOrganizationalProviderEntries(int firstResult, int maxResults) {
        return organizationalProviderRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	@Override
	public void saveOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto) {
		OrganizationalProvider organizationalProvider = organizationalProviderDtoToOrganizationalProviderMapper.map(organizationalProviderDto);
		organizationalProviderRepository.save(organizationalProvider);
	}

	@Override
	public OrganizationalProvider updateOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto) {
		OrganizationalProvider organizationalProvider = organizationalProviderDtoToOrganizationalProviderMapper.map(organizationalProviderDto);
		
		if (organizationalProvider != null) {
			return organizationalProviderRepository.save(organizationalProvider);	
		} 
		throw new ClinicalDataNotFoundException(organizationalProviderDto.getId());
	}

	@Override
	public OrganizationalProviderDto findOrganizationalProvider(Long id) {
		
		OrganizationalProvider organizationalProvider = organizationalProviderRepository.findOne(id);
		
		if(organizationalProvider != null) {			
			modelMapper.addConverter(setOfObjectToSetOfStringContverter);
			
			return modelMapper.map(organizationalProvider, OrganizationalProviderDto.class);
		}
		throw new ClinicalDataNotFoundException(1L);
	}
}
