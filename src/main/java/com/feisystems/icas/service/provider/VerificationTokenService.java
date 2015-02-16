package com.feisystems.icas.service.provider;

import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.domain.provider.VerificationToken;


public interface VerificationTokenService {
	
	public VerificationToken sendEmailToken(String userName, VerificationToken.VerificationTokenType type);
	
	public VerificationToken verify(String base64EncodedToken);
	
	public IndividualProvider retrieveUser(String base64EncodedToken);
	
}