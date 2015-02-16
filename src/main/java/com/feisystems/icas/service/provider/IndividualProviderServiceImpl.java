package com.feisystems.icas.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.provider.Credential;
import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.domain.provider.IndividualProviderRepository;
import com.feisystems.icas.domain.reference.SecurityQuestionsCode;
import com.feisystems.icas.domain.reference.SecurityQuestionsRepository;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.exceptions.PasswordAlreadyUsedException;
import com.feisystems.icas.exceptions.UserNotFoundException;
import com.feisystems.icas.infrastructure.DtoToDomainEntityMapper;
import com.feisystems.icas.security.SecurityUtils;
import com.feisystems.icas.service.dto.IndividualProviderDto;

@Service
@Transactional
public class IndividualProviderServiceImpl implements IndividualProviderService {

	private final Logger log = LoggerFactory.getLogger(IndividualProviderServiceImpl.class);

	@Inject
	private PasswordEncoder passwordEncoder;

	@Autowired
	IndividualProviderRepository individualProviderRepository;

	@Autowired
	SecurityQuestionsRepository securityQuestionsRepository;

	/** The model mapper. */
	@Autowired
	private ModelMapper modelMapper;

	/** The individualProvider profile dto to individualProvider mapper. */
	@Autowired
	private DtoToDomainEntityMapper<IndividualProviderDto, IndividualProvider> individualProviderDtoToIndividualProviderMapper;

	public IndividualProviderDto findIndividualProvider(Long id) {
		IndividualProvider individualProvider = individualProviderRepository.findOne(id);
		if (individualProvider != null) {
			return modelMapper.map(individualProvider, IndividualProviderDto.class);
		}
		throw new ClinicalDataNotFoundException(1L);
	}

	public IndividualProvider updateIndividualProvider(IndividualProviderDto individualProviderDto) {
		IndividualProvider individualProvider = individualProviderDtoToIndividualProviderMapper.map(individualProviderDto);
		if (individualProvider != null) {
			// TODO: Need to investigate
			// this.validatePassword(individualProviderDto.getCredential(),
			// individualProvider);
			return individualProviderRepository.save(individualProvider);
		}
		throw new ClinicalDataNotFoundException(individualProviderDto.getId());
	}

	/** {@inheritDoc} */
	public List<IndividualProviderDto> findAllIndividualProviders() {
		List<IndividualProviderDto> individualProviderProfileDtoList = new ArrayList<>();
		List<IndividualProvider> individualProviderList = individualProviderRepository.findAll();

		if (individualProviderList != null && individualProviderList.size() > 0) {
			for (IndividualProvider individualProvider : individualProviderList) {
				IndividualProviderDto individualProviderDto = modelMapper.map(individualProvider, IndividualProviderDto.class);
				individualProviderProfileDtoList.add(individualProviderDto);
			}
			return individualProviderProfileDtoList;
		}
		throw new IllegalArgumentException("No Care Manager Found");
	}

	public IndividualProvider findByUsername(String username) {
		IndividualProvider user = individualProviderRepository.findByUserName(username);

		if (user == null) {
			throw new UserNotFoundException("Username is not valid");
		}

		return user;
	}

	public void deleteIndividualProvider(IndividualProvider individualProvider) {
		individualProviderRepository.delete(individualProvider);
	}

	public void saveIndividualProvider(IndividualProviderDto individualProviderDto) {
		IndividualProvider individualProvider = individualProviderDtoToIndividualProviderMapper.map(individualProviderDto);
		// Not required because it's only called by register so it will have no
		// password
		// this.validatePassword(individualProviderDto.getCredential(),
		// individualProvider);
		individualProviderRepository.save(individualProvider);
	}

	public void updateUserInformation(String firstName, String lastName, String email) {
		IndividualProvider currentUser = individualProviderRepository.findByUserName(SecurityUtils.getCurrentLogin());
		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setEmail(email);
		individualProviderRepository.save(currentUser);
		log.debug("Changed Information for IndividualProvider: {}", currentUser);
	}

	public void changePassword(String password) {
		this.changePasswordForUser(password, SecurityUtils.getCurrentLogin());
	}

	public void changePasswordForUser(String rawPassword, String userName) {
		IndividualProvider individualProvider = individualProviderRepository.findByUserName(userName);
		this.validatePassword(rawPassword, individualProvider);

		Credential newCredential = new Credential();
		newCredential.setCreatedDate(new Date());
		newCredential.setPassword(passwordEncoder.encode(rawPassword));
		newCredential.setUser(individualProvider);
		individualProvider.addCredential(newCredential);

		individualProviderRepository.save(individualProvider);
		log.debug("Changed password for IndividualProvider: {}", individualProvider);
	}

	public void changeSecurityQuestions(IndividualProviderDto dto) {
		IndividualProvider individualProvider = individualProviderRepository.findByUserName(dto.getUserName());

		SecurityQuestionsCode code1 = securityQuestionsRepository.findByCode(dto.getSecurityQuestion1());
		SecurityQuestionsCode code2 = securityQuestionsRepository.findByCode(dto.getSecurityQuestion2());

		individualProvider.setQuestion1(code1);
		individualProvider.setQuestion2(code2);
		individualProvider.setAnswer1(passwordEncoder.encode(dto.getSecurityAnswer1()));
		individualProvider.setAnswer2(passwordEncoder.encode(dto.getSecurityAnswer2()));
		individualProviderRepository.save(individualProvider);
	}

	@Transactional(readOnly = true)
	public IndividualProvider getUserWithAuthorities() {
		IndividualProvider currentUser = individualProviderRepository.findByUserName(SecurityUtils.getCurrentLogin());
		currentUser.getAuthorities().size(); // eagerly load the association
		return currentUser;
	}

	@Override
	public IndividualProvider findByUsernameAndDateOfBirth(String userName, Date dateOfBirth) {
		IndividualProvider user = individualProviderRepository.findByUserNameAndDateOfBirth(userName, dateOfBirth);

		if (user == null) {
			throw new UserNotFoundException("Username is not valid");
		}

		return user;
	}

	private void validatePassword(String newPassword, IndividualProvider individualProvider) {
		if (newPassword != null && !newPassword.equals("")) {
			for (Credential oldCredential : individualProvider.getCredentials()) {
				if (compareStrings(newPassword, oldCredential.getPassword())) {
					throw new PasswordAlreadyUsedException("Password already used");
				}
			}
		}
	}

	private boolean compareStrings(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
