package com.feisystems.icas.service.provider;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.domain.provider.IndividualProviderRepository;
import com.feisystems.icas.domain.provider.LoginAttempt;
import com.feisystems.icas.domain.provider.LoginAttemptRepository;

@Service
@Transactional
public class LoginAttemptServiceImpl implements LoginAttemptService {

	@Autowired
	IndividualProviderRepository individualProviderRepository;

	@Autowired
	LoginAttemptRepository loginAttemptRepository;

	private static final int MAX_ATTEMPTS = 2;

	@Override
	public void updateFailAttempts(String username) {
		LoginAttempt loginAttempt = loginAttemptRepository.findByUsername(username);

		if (loginAttempt == null) {
			loginAttempt = new LoginAttempt();
			loginAttempt.setAttempts(1);

			loginAttempt.setUsername(username);
		} else {

			int currentAttempts = loginAttempt.getAttempts();
			currentAttempts++;
			loginAttempt.setAttempts(currentAttempts);

			if (loginAttempt.getAttempts() >= MAX_ATTEMPTS) {

				IndividualProvider user = individualProviderRepository.findByUserName(username);
				user.setAccountNonLocked(false);
				individualProviderRepository.save(user);
			}

		}

		loginAttempt.setLastModified(new Date());
		loginAttemptRepository.save(loginAttempt);
	}

	@Override
	public void resetFailAttempts(String username) {
		LoginAttempt loginAttempt = loginAttemptRepository.findByUsername(username);
		if (loginAttempt != null) {
			loginAttempt.setAttempts(0);
			loginAttempt.setLastModified(new Date());
			loginAttemptRepository.save(loginAttempt);

			IndividualProvider user = individualProviderRepository.findByUserName(username);
			user.setAccountNonLocked(true);
		}
	}

	@Override
	public LoginAttempt getUserAttempt(String username) {
		LoginAttempt loginAttemp = loginAttemptRepository.findByUsername(username);
		return loginAttemp;
	}

}
