package com.feisystems.icas.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({ "classpath:/META-INF/spring/database.properties" })
public class ApplicationConfig {

	private final static String ENVIRONMENT = "environment";
	private final static String LOGIN = "login";
	private final static String REGISTER = "register";

	private final static String TOKEN_EXPIRY_TIME = "token.timeToLive.inMinutes";
	private final static String EMAIL_SERVICES_FROM_ADDRESS = "email.services.fromAddress";
	private final static String EMAIL_SERVICES_REPLYTO_ADDRESS = "email.services.replyTo";
	private final static String EMAIL_SERVICES_VERIFICATION_EMAIL_SUBJECT_TEXT = "email.services.emailVerificationSubjectText";
	private final static String EMAIL_SERVICES_REGISTRATION_EMAIL_SUBJECT_TEXT = "email.services.emailRegistrationSubjectText";
	private final static String EMAIL_SERVICES_LOST_PASSWORD_SUBJECT_TEXT = "email.services.lostPasswordSubjectText";

	@Autowired
	protected Environment environment;

	public String getHostNameUrl() {
		return environment.getProperty(ENVIRONMENT);
	}

	public String getLogin() {
		return environment.getProperty(LOGIN);
	}

	public String getRegister() {
		return environment.getProperty(REGISTER);
	}

	public int getTokenExpiryTime() {
		return Integer.parseInt(environment.getProperty(TOKEN_EXPIRY_TIME));
	}

	public String getEmailVerificationSubjectText() {
		return environment.getProperty(EMAIL_SERVICES_VERIFICATION_EMAIL_SUBJECT_TEXT);
	}

	public String getEmailRegistrationSubjectText() {
		return environment.getProperty(EMAIL_SERVICES_REGISTRATION_EMAIL_SUBJECT_TEXT);
	}

	public String getLostPasswordSubjectText() {
		return environment.getProperty(EMAIL_SERVICES_LOST_PASSWORD_SUBJECT_TEXT);
	}

	public String getEmailFromAddress() {
		return environment.getProperty(EMAIL_SERVICES_FROM_ADDRESS);
	}

	public String getEmailReplyToAddress() {
		return environment.getProperty(EMAIL_SERVICES_REPLYTO_ADDRESS);
	}

}
