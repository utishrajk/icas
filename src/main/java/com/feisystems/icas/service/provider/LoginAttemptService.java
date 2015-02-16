package com.feisystems.icas.service.provider;

import com.feisystems.icas.domain.provider.LoginAttempt;

public interface LoginAttemptService {

	void updateFailAttempts(String username);

	void resetFailAttempts(String username);

	LoginAttempt getUserAttempt(String username);

}
