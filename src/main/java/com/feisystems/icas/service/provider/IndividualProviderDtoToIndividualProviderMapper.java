package com.feisystems.icas.service.provider;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feisystems.icas.domain.provider.Authority;
import com.feisystems.icas.domain.provider.Credential;
import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.domain.provider.IndividualProviderRepository;
import com.feisystems.icas.domain.reference.AdministrativeGenderCode;
import com.feisystems.icas.domain.reference.AdministrativeGenderCodeRepository;
import com.feisystems.icas.domain.reference.ProviderTaxonomyCode;
import com.feisystems.icas.domain.reference.ProviderTaxonomyCodeRepository;
import com.feisystems.icas.domain.reference.SecurityQuestionsCode;
import com.feisystems.icas.domain.reference.SecurityQuestionsRepository;
import com.feisystems.icas.domain.reference.StateCodeRepository;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.security.AuthoritiesConstants;
import com.feisystems.icas.service.dto.IndividualProviderDto;

/**
 * The Class IndividualProviderDtoToIndividualProviderMapper.
 */
@Component
public class IndividualProviderDtoToIndividualProviderMapper implements DtoToDomainEntityMapper<IndividualProviderDto, IndividualProvider> {

	/** The individualProvider repository. */
	private IndividualProviderRepository individualProviderRepository;

	/** The administrative gender code repository. */
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	/** The Provider Taxonomy Code Repository. */
	private ProviderTaxonomyCodeRepository providerTaxonomyCodeRepository;

	private SecurityQuestionsRepository securityQuestionsRepository;

	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * Instantiates a new individualProvider dto to individualProvider mapper.
	 *
	 * @param individualProviderRepository
	 *            the individualProvider repository
	 * @param stateCodeRepository
	 *            the state code repository
	 * @param countryCodeRepository
	 *            the country code repository
	 * @param administrativeGenderCodeRepository
	 *            the administrative gender code repository
	 */
	@Autowired
	public IndividualProviderDtoToIndividualProviderMapper(IndividualProviderRepository individualProviderRepository, StateCodeRepository stateCodeRepository,
			ProviderTaxonomyCodeRepository providerTaxonomyCodeRepository, AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			SecurityQuestionsRepository securityQuestionsRepository) {

		super();

		this.individualProviderRepository = individualProviderRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
		this.providerTaxonomyCodeRepository = providerTaxonomyCodeRepository;
		this.securityQuestionsRepository = securityQuestionsRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.feisystems.icas.infrastructure.DtoToDomainEntityMapper#map(java.lang
	 * .Object)
	 */
	@Override
	public IndividualProvider map(IndividualProviderDto individualProviderDto) {
		IndividualProvider individualProvider = null;

		if (individualProviderDto.getUserName() != null) {
			individualProvider = individualProviderRepository.findByUserName(individualProviderDto.getUserName());
		} else {
			individualProvider = new IndividualProvider();
		}

		// Return null if no IndividualProvider found
		if (individualProvider == null) {
			return null;
		}

		individualProvider.setUserName(individualProviderDto.getUserName());

		// for Create
		if (individualProvider.getUserName() == null) {
			individualProvider.setUserName(individualProviderDto.getEmail());
			Authority authority = new Authority();
			authority.setName(AuthoritiesConstants.ROLE_USER);

			Set<Authority> set = new HashSet<>();
			set.add(authority);

			individualProvider.setAuthorities(set);

			SecurityQuestionsCode code1 = securityQuestionsRepository.findByCode(individualProviderDto.getSecurityQuestion1());
			SecurityQuestionsCode code2 = securityQuestionsRepository.findByCode(individualProviderDto.getSecurityQuestion2());

			individualProvider.setQuestion1(code1);
			individualProvider.setQuestion2(code2);

			String answer1 = individualProviderDto.getSecurityAnswer1();

			if (answer1 != null && !answer1.equals("")) {
				individualProviderDto.setSecurityAnswer1(passwordEncoder.encode(answer1));
			}

			String answer2 = individualProviderDto.getSecurityAnswer2();

			if (answer2 != null && !answer2.equals("")) {
				individualProviderDto.setSecurityAnswer2(passwordEncoder.encode(answer2));
			}

			individualProvider.setAnswer1(individualProviderDto.getSecurityAnswer1());
			individualProvider.setAnswer2(individualProviderDto.getSecurityAnswer2());

			individualProvider.setAccountNonLocked(true);
		}

		String credential = individualProviderDto.getCredential();
		if (credential != null && !credential.equals("")) {
			Credential newCredential = new Credential();
			newCredential.setCreatedDate(new Date());
			newCredential.setPassword(passwordEncoder.encode(individualProviderDto.getCredential()));
			newCredential.setUser(individualProvider);
			individualProvider.addCredential(newCredential);
		}
		individualProvider.setFirstName(individualProviderDto.getFirstName());
		individualProvider.setLastName(individualProviderDto.getLastName());
		individualProvider.setDateOfBirth(individualProviderDto.getDateOfBirth());
		individualProvider.setEmail(individualProviderDto.getEmail());

		individualProvider.setNamePrefixCode(individualProviderDto.getNamePrefixCode());
		individualProvider.setMiddleName(individualProviderDto.getMiddleName());
		individualProvider.setNameSuffix(individualProviderDto.getNameSuffix());

		if (StringUtils.hasText(individualProviderDto.getAdministrativeGenderCode())) {
			AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository.findByCode(individualProviderDto.getAdministrativeGenderCode());
			individualProvider.setAdministrativeGenderCode(administrativeGenderCode);
		} else {
			individualProvider.setAdministrativeGenderCode(null);
		}

		individualProvider.setNpi(individualProviderDto.getNpi());

		if (individualProviderDto.getProviderTaxonomyCode() != null && StringUtils.hasText(individualProviderDto.getProviderTaxonomyCode())) {
			ProviderTaxonomyCode providerTaxonomyCode = providerTaxonomyCodeRepository.findByCode(individualProviderDto.getProviderTaxonomyCode());
			individualProvider.setProviderTaxonomyCode(providerTaxonomyCode);
		} else {
			individualProvider.setProviderTaxonomyCode(null);
		}

		// Contact
		individualProvider.setWebsite(individualProviderDto.getWebsite());
		individualProvider.setMailingAddressTelephoneNumber(individualProviderDto.getMailingAddressTelephoneNumber());
		individualProvider.setMailingAddressFaxNumber(individualProviderDto.getMailingAddressFaxNumber());

		// Mailing Address
		individualProvider.setFirstLineMailingAddress(individualProviderDto.getFirstLineMailingAddress());
		individualProvider.setSecondLineMailingAddress(individualProviderDto.getSecondLineMailingAddress());
		individualProvider.setMailingAddressCityName(individualProviderDto.getMailingAddressCityName());
		individualProvider.setMailingAddressStateName(individualProviderDto.getMailingAddressStateName());
		individualProvider.setNonUSState(individualProviderDto.getNonUSState());
		individualProvider.setMailingAddressPostalCode(individualProviderDto.getMailingAddressPostalCode());
		individualProvider.setMailingAddressCountryCode(individualProviderDto.getMailingAddressCountryCode());

		return individualProvider;
	}

}
