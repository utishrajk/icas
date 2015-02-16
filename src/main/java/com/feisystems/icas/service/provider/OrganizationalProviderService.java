package com.feisystems.icas.service.provider;
import com.feisystems.icas.domain.provider.OrganizationalProvider;
import com.feisystems.icas.service.dto.OrganizationalProviderDto;

import java.util.List;

public interface OrganizationalProviderService {

	public abstract long countAllOrganizationalProviders();


	public abstract void deleteOrganizationalProvider(OrganizationalProvider organizationalProvider);


	public abstract OrganizationalProviderDto findOrganizationalProvider(Long id);


	public abstract List<OrganizationalProviderDto> findAllOrganizationalProviders();


	public abstract List<OrganizationalProvider> findOrganizationalProviderEntries(int firstResult, int maxResults);


	public abstract void saveOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto);


	public abstract OrganizationalProvider updateOrganizationalProvider(OrganizationalProviderDto organizationalProviderDto);
	
}
