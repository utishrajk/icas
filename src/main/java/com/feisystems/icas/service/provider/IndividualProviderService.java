package com.feisystems.icas.service.provider;

import java.util.Date;
import java.util.List;

import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.service.dto.IndividualProviderDto;

public interface IndividualProviderService {

	public abstract void deleteIndividualProvider(IndividualProvider individualProvider);

	public abstract IndividualProviderDto findIndividualProvider(Long id);

	public abstract void saveIndividualProvider(IndividualProviderDto individualProviderDto);

	public abstract IndividualProvider updateIndividualProvider(IndividualProviderDto individualProviderDto);

	public List<IndividualProviderDto> findAllIndividualProviders();

	public IndividualProvider findByUsername(String username);

	public IndividualProvider findByUsernameAndDateOfBirth(String username, Date dateOfBirth);

	public IndividualProvider getUserWithAuthorities();

	public void changePassword(String password);

	public void changePasswordForUser(String password, String username);

	public void updateUserInformation(String firstName, String lastName, String email);

	public void changeSecurityQuestions(IndividualProviderDto dto);

}
