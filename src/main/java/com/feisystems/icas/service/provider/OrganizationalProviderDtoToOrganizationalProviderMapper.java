package com.feisystems.icas.service.provider;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.icas.domain.provider.OrganizationalProviderRepository;
import com.feisystems.icas.domain.provider.OrganizationalProvider;
import com.feisystems.icas.domain.reference.ServiceCode;
import com.feisystems.icas.domain.reference.ServiceCodeRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.service.dto.OrganizationalProviderDto;

@Component
public class OrganizationalProviderDtoToOrganizationalProviderMapper implements
		DtoToDomainEntityMapper<OrganizationalProviderDto, OrganizationalProvider> {

	/** The OrganizationalProvider repository. */
//	private OrganizationalProviderRepository organizationalProviderRepository;
	
	@Autowired
    OrganizationalProviderRepository organizationalProviderRepository;
	
	@Autowired
    ServiceCodeRepository serviceCodeRepository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.feisystems.icas.infrastructure.DtoToDomainEntityMapper#map(java.lang
	 * .Object)
	 */
	@Override
	public OrganizationalProvider map(OrganizationalProviderDto organizationalProviderDto) {
		OrganizationalProvider organizationalProvider = null;

		if(organizationalProviderDto.getEmail() != null) {			
		    Long id = organizationalProviderDto.getId();
			organizationalProvider = organizationalProviderRepository.findOne(id);
		}
		else {
			organizationalProvider = new OrganizationalProvider();
		}

		// Return null if no IndividualProvider found
		if (organizationalProvider == null) {
			return null;
		}
		
		if(organizationalProviderDto != null){
			//General
			organizationalProvider.setOrgName(organizationalProviderDto.getOrgName());
			organizationalProvider.setNpi(organizationalProviderDto.getNpi());
			organizationalProvider.setAuthorizedOfficialNamePrefix(organizationalProviderDto.getAuthorizedOfficialNamePrefix());
			organizationalProvider.setAuthorizedOfficialFirstName(organizationalProviderDto.getAuthorizedOfficialFirstName());
			organizationalProvider.setAuthorizedOfficialLastName(organizationalProviderDto.getAuthorizedOfficialLastName());
			organizationalProvider.setAuthorizedOfficialTelephoneNumber(organizationalProviderDto.getAuthorizedOfficialTelephoneNumber());
			
			//Contact
			organizationalProvider.setEmail(organizationalProviderDto.getEmail());
			organizationalProvider.setWebsite(organizationalProviderDto.getWebsite());
			organizationalProvider.setMailingAddressTelephoneNumber(organizationalProviderDto.getMailingAddressTelephoneNumber());
			organizationalProvider.setMailingAddressFaxNumber(organizationalProviderDto.getMailingAddressFaxNumber());
			
			//Mailing Address
			organizationalProvider.setFirstLineMailingAddress(organizationalProviderDto.getFirstLineMailingAddress());
			organizationalProvider.setSecondLineMailingAddress(organizationalProviderDto.getSecondLineMailingAddress());
			organizationalProvider.setMailingAddressCityName(organizationalProviderDto.getMailingAddressCityName());
			organizationalProvider.setMailingAddressStateName(organizationalProviderDto.getMailingAddressStateName());
			organizationalProvider.setNonUSState(organizationalProviderDto.getNonUSState());
			organizationalProvider.setMailingAddressCountryCode(organizationalProviderDto.getMailingAddressCountryCode());
			organizationalProvider.setMailingAddressPostalCode(organizationalProviderDto.getMailingAddressPostalCode());
			
			Set<ServiceCode> newServices = new HashSet<ServiceCode>();
			
			for(String code: organizationalProviderDto.getServices()){
				ServiceCode s = serviceCodeRepository.findByCode(code);
				newServices.add(s);
			}
			
			organizationalProvider.setServices(newServices);
		}

		return organizationalProvider;
	}

}
