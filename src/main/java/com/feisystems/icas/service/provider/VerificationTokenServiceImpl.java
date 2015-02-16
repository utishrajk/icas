package com.feisystems.icas.service.provider;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.domain.provider.IndividualProviderRepository;
import com.feisystems.icas.domain.provider.VerificationToken;
import com.feisystems.icas.domain.provider.VerificationTokenRepository;
import com.feisystems.icas.exceptions.AlreadyVerifiedException;
import com.feisystems.icas.exceptions.TokenHasExpiredException;
import com.feisystems.icas.exceptions.TokenNotFoundException;
import com.feisystems.icas.exceptions.UserNotFoundException;
import com.feisystems.icas.infrastructure.ApplicationConfig;
import com.feisystems.icas.infrastructure.EmailServiceTokenModel;
import com.feisystems.icas.infrastructure.EmailServicesGateway;

@Service("verificationTokenService")
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

	IndividualProviderRepository individualProviderRepository;

	VerificationTokenRepository tokenRepository;

	EmailServicesGateway emailServicesGateway;

	ApplicationConfig config;

	@Autowired
	public VerificationTokenServiceImpl(IndividualProviderRepository individualProviderRepository, EmailServicesGateway emailServicesGateway, VerificationTokenRepository tokenRepository) {
		this.individualProviderRepository = individualProviderRepository;
		this.emailServicesGateway = emailServicesGateway;
		this.tokenRepository = tokenRepository;
	}

	public VerificationToken sendEmailToken(String userName, VerificationToken.VerificationTokenType type) {
		IndividualProvider user = ensureUserIsLoaded(userName);
		VerificationToken token = null;

		if (user != null) {
			token = user.getActiveToken(type);
			if (token == null) {
				token = new VerificationToken(user, type, config.getTokenExpiryTime());
				user.addVerificationToken(token);
				if (!token.getTokenType().equals(VerificationToken.VerificationTokenType.emailConfirmation)) {
					individualProviderRepository.save(user);
				}
			}
			emailServicesGateway.sendVerificationToken(new EmailServiceTokenModel(user, token, getConfig().getHostNameUrl()));
		}

		return token;
	}

	public VerificationToken verify(String base64EncodedToken) {
		VerificationToken token = loadToken(base64EncodedToken);

		token.setVerified(true);
		token.getUser().setVerified(true);
		individualProviderRepository.save(token.getUser());
		return token;
	}

	private VerificationToken loadToken(String base64EncodedToken) {
		String rawToken = new String(Base64.decodeBase64(base64EncodedToken));
		VerificationToken token = tokenRepository.findByToken(rawToken);

		if (token == null) {
			throw new TokenNotFoundException("Token Not Found");
		}
		if (token.hasExpired()) {
			throw new TokenHasExpiredException("Token has expired");
		}

		return token;
	}

	public IndividualProvider retrieveUser(String base64EncodedToken) {
		VerificationToken token = loadToken(base64EncodedToken);

		if (token.isVerified()) {
			throw new AlreadyVerifiedException("Token already verified");
		}
		token.setVerified(true);

		return token.getUser();
	}

	IndividualProvider ensureUserIsLoaded(String userName) {
		IndividualProvider user = individualProviderRepository.findByUserName(userName);

		if (user == null) {
			throw new UserNotFoundException("Username Not Found");
		}

		return user;
	}

	@Autowired
	public void setConfig(ApplicationConfig config) {
		this.config = config;
	}

	public ApplicationConfig getConfig() {
		return this.config;
	}

}
