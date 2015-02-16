package com.feisystems.icas.web;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Locale;
//import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.feisystems.icas.domain.provider.Authority;
import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.domain.provider.VerificationToken;
import com.feisystems.icas.infrastructure.ApplicationConfig;
import com.feisystems.icas.service.dto.ApplicationDto;
import com.feisystems.icas.service.dto.IdentityDto;
import com.feisystems.icas.service.dto.IndividualProviderDto;
import com.feisystems.icas.service.dto.IndividualProviderFeedbackServiceDto;
import com.feisystems.icas.service.dto.SecurityQuestionsDto;
import com.feisystems.icas.service.provider.IndividualProviderService;
import com.feisystems.icas.service.provider.VerificationTokenService;
import com.feisystems.icas.service.provider.services.IndividualProviderFeedbackServicesService;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/app")
public class AccountController {

	private final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private MessageSource messageSource;

	@Inject
	private IndividualProviderService individualProviderService;

	@Autowired
	VerificationTokenService verificationTokenService;

	@Autowired
	ApplicationConfig config;

	@Autowired
	IndividualProviderFeedbackServicesService feedBackServicesService;

	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * GET /rest/authenticate -> check if the user is authenticated, and return
	 * its login.
	 */
	@RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET, produces = "application/json")
	public String isAuthenticated(HttpServletRequest request) {
		log.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}

	/**
	 * GET /rest/account -> get the current user.
	 */
	@RequestMapping(value = "/rest/account", method = RequestMethod.GET, produces = "application/json")
	public IndividualProviderDto getAccount(HttpServletResponse response) {
		IndividualProvider individualProvider = individualProviderService.getUserWithAuthorities();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (individualProvider == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

//		List<String> roles = individualProvider.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList());
//		IndividualProviderDto individualProviderDto = new IndividualProviderDto(individualProvider.getId(), individualProvider.getUserName(), individualProvider.getFirstName(),
//				individualProvider.getLastName(), individualProvider.getEmail(), roles);
		
	    List<String> roles = new ArrayList<>();
		
		
		Set<Authority> authorities = individualProvider.getAuthorities();
		
		for(Authority authority : authorities) {
			roles.add(authority.getName());
		}
		
    	IndividualProviderDto individualProviderDto = new IndividualProviderDto(individualProvider.getId(), 
																				individualProvider.getUserName(), 
																				individualProvider.getFirstName(), 
																				individualProvider.getLastName(),
																				individualProvider.getEmail(), roles);
		
		return individualProviderDto;
	}

	/**
	 * POST /rest/account -> update the current user information.
	 */
	@RequestMapping(value = "/rest/account", method = RequestMethod.POST, produces = "application/json")
	public void saveAccount(@RequestBody IndividualProviderDto individualProviderDto) throws IOException {
		individualProviderService.updateUserInformation(individualProviderDto.getFirstName(), individualProviderDto.getLastName(), individualProviderDto.getEmail());
	}

	/**
	 * POST /rest/change_password -> changes the current user's password
	 */
	@RequestMapping(value = "/rest/account/change_password", method = RequestMethod.POST, produces = "application/json")
	public void changePassword(@RequestBody String password, HttpServletResponse response) throws IOException {
		if (password == null || password.equals("")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Password should not be empty");
		} else {
			individualProviderService.changePassword(password);
		}
	}

	/**
	 * GET sends back a json error message if the user is not authorized
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET, produces = "application/json")
	public ErrorInfo accessDeniedMessageHandler(HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = messageSource.getMessage("error.bad.authorization", null, locale);
		String errorURL = request.getRequestURL().toString();

		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@RequestMapping(value = "/public/identifyuser", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> identifyUser(@RequestBody IdentityDto dto) {
		IndividualProvider user = individualProviderService.findByUsernameAndDateOfBirth(dto.getUserName(), dto.getDateOfBirth());

		return new ResponseEntity<String>(getHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/public/verifyAnswers", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> verifyAnswers(@RequestBody IndividualProviderDto dto) {
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;

		IndividualProvider user = individualProviderService.findByUsername(dto.getUserName());

		if (compareStrings(dto.getSecurityAnswer1(), user.getAnswer1()) && compareStrings(dto.getSecurityAnswer2(), user.getAnswer2())) {
			httpStatus = HttpStatus.OK;

			verificationTokenService.sendEmailToken(dto.getUserName(), VerificationToken.VerificationTokenType.lostPassword);
		}

		return new ResponseEntity<String>(getHeaders(), httpStatus);
	}

	@RequestMapping(value = "/public/sendVerificationEmail", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> sendVerificationEmail(@RequestBody IndividualProviderDto dto) {
		verificationTokenService.sendEmailToken(dto.getUserName(), VerificationToken.VerificationTokenType.lostPasswordAndSecurityQuestions);

		return new ResponseEntity<String>(getHeaders(), HttpStatus.OK);
	}

	private boolean compareStrings(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	@RequestMapping(value = "/public/retrieveSecurityQuestions/{userName}/{dateOfBirth}", method = RequestMethod.GET, headers = "Accept=application/json")
	public SecurityQuestionsDto retrieveSecurityQuestions(@PathVariable("userName") String userName, @PathVariable("dateOfBirth") String dateOfBirth) {
		// TODO: Also use dob
		IndividualProvider user = individualProviderService.findByUsername(userName);

		SecurityQuestionsDto dto = new SecurityQuestionsDto();

		dto.setQuestion1(user.getQuestion1().getDisplayName());
		dto.setQuestion2(user.getQuestion2().getDisplayName());

		return dto;
	}

	@RequestMapping(value = "/public/register", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody IndividualProviderDto dto) {

		if (!dto.getCredential().equals(dto.getConfirmPassword()) || dto.getSecurityQuestion1().equals(dto.getSecurityQuestion2())) {
			return new ResponseEntity<String>(getHeaders(), HttpStatus.FORBIDDEN);
		}

		individualProviderService.saveIndividualProvider(dto);
		IndividualProvider user = individualProviderService.findByUsername(dto.getEmail());

		IndividualProviderFeedbackServiceDto feedbackServiceDto = new IndividualProviderFeedbackServiceDto();
		feedbackServiceDto.setIndividualprovider_id(user.getId());

		feedBackServicesService.saveIndividualProviderFeedbackServices(feedbackServiceDto);

		verificationTokenService.sendEmailToken(dto.getEmail(), VerificationToken.VerificationTokenType.emailRegistration);
		return new ResponseEntity<String>(getHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/public/verify/{base64EncodedToken}")
	public void verifyUser(HttpServletResponse httpServletResponse, @PathVariable String base64EncodedToken) throws IOException {
		VerificationToken token = verificationTokenService.verify(base64EncodedToken);

		if (token != null && token.isVerified()) {
			// happy path..
			httpServletResponse.sendRedirect(config.getLogin());
		} else {
			httpServletResponse.sendRedirect(config.getRegister());
		}
	}

	@RequestMapping(value = "/public/resetpassword", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> resetPassword(@RequestBody IndividualProviderDto dto) {

		if (!dto.getCredential().equals(dto.getConfirmPassword())) {
			return new ResponseEntity<String>(getHeaders(), HttpStatus.FORBIDDEN);
		}

		IndividualProvider user = verificationTokenService.retrieveUser(dto.getToken());

		individualProviderService.changePasswordForUser(dto.getCredential(), user.getUserName());
		verificationTokenService.sendEmailToken(user.getUserName(), VerificationToken.VerificationTokenType.emailConfirmation);
		return new ResponseEntity<String>(getHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/public/resetpasswordandsecurityquestions", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> resetPasswordAndSecurityQuestions(@RequestBody IndividualProviderDto dto) {

		if (!dto.getCredential().equals(dto.getConfirmPassword()) || dto.getSecurityQuestion1().equals(dto.getSecurityQuestion2())) {
			return new ResponseEntity<String>(getHeaders(), HttpStatus.FORBIDDEN);
		}

		IndividualProvider user = verificationTokenService.retrieveUser(dto.getToken());

		individualProviderService.changePasswordForUser(dto.getCredential(), user.getUserName());
		dto.setUserName(user.getUserName());
		individualProviderService.changeSecurityQuestions(dto);
		verificationTokenService.sendEmailToken(user.getUserName(), VerificationToken.VerificationTokenType.emailConfirmation);
		return new ResponseEntity<String>(getHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/public/environment", method = RequestMethod.GET)
	public ApplicationDto getEnvironment() {
		ApplicationDto dto = new ApplicationDto();
		dto.setEnvironment(config.getHostNameUrl());
		return dto;
	}

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return headers;
	}

}
