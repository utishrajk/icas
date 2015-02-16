package com.feisystems.icas.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.feisystems.icas.exceptions.AlreadyVerifiedException;
import com.feisystems.icas.exceptions.ClinicalDataNotFoundException;
import com.feisystems.icas.exceptions.DataNotFoundException;
import com.feisystems.icas.exceptions.PasswordAlreadyUsedException;
import com.feisystems.icas.exceptions.TokenHasExpiredException;
import com.feisystems.icas.exceptions.TokenNotFoundException;
import com.feisystems.icas.exceptions.UserNotFoundException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@ControllerAdvice
public class RestExceptionProcessor {

	@Autowired
	private MessageSource messageSource;

	Locale locale = LocaleContextHolder.getLocale();

	@ExceptionHandler(ClinicalDataNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo patientNotFound(HttpServletRequest req, ClinicalDataNotFoundException ex) {
		String errorMessage = messageSource.getMessage("error.clinicaldatanotfound", null, locale);

		errorMessage += ex.getPatientId();
		String errorURL = req.getRequestURL().toString();

		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo requestMethodNotSupported(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
		String errorMessage = messageSource.getMessage("error.bad.url", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(NoSuchRequestHandlingMethodException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo requestHandlingMethodNotSupported(HttpServletRequest req, NoSuchRequestHandlingMethodException ex) {
		String errorMessage = messageSource.getMessage("error.bad.url", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo requestHandlingNoHandlerFound(HttpServletRequest req, NoHandlerFoundException ex) {
		String errorMessage = messageSource.getMessage("error.bad.url", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo userNotFound(HttpServletRequest req, UserNotFoundException ex) {
		String errorMessage = messageSource.getMessage("error.bad.url", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(AlreadyVerifiedException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo alreadyVerified(HttpServletRequest req, AlreadyVerifiedException ex) {
		String errorMessage = messageSource.getMessage("error.bad.url", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(TokenNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo tokenNotFound(HttpServletRequest req, TokenNotFoundException ex) {
		String errorMessage = messageSource.getMessage("error.bad.url", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(TokenHasExpiredException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo tokenHasExpired(HttpServletRequest req, TokenHasExpiredException ex) {
		String errorMessage = messageSource.getMessage("error.bad.url", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo tokenNotFound(HttpServletRequest req, DataNotFoundException ex) {
		String errorMessage = messageSource.getMessage("exception.used.password", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(PasswordAlreadyUsedException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorInfo tokenNotFound(HttpServletRequest req, PasswordAlreadyUsedException ex) {
		String errorMessage = messageSource.getMessage("exception.used.password", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(LockedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorInfo lockedException(HttpServletRequest req, LockedException ex) {
		String errorMessage = messageSource.getMessage("exception.account.locked", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorInfo badCredentialsException(HttpServletRequest req, BadCredentialsException ex) {
		String errorMessage = messageSource.getMessage("exception.invalid.login", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorInfo authenticationException(HttpServletRequest req, AuthenticationException ex) {
		String errorMessage = messageSource.getMessage("exception.authentication.failed", null, locale);
		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
		return errorInfo;
	}

	@ExceptionHandler(JpaSystemException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorInfo integrityConstraintViolated(HttpServletRequest req, JpaSystemException ex) {
		String errorMessage = messageSource.getMessage("exception.jpasystemexception", null, locale);
		if (ex.getMostSpecificCause().getClass().equals(MySQLIntegrityConstraintViolationException.class)) {
			errorMessage = messageSource.getMessage("exception.duplicate.username", null, locale);
		}

		String errorURL = req.getRequestURL().toString();
		ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);

		return errorInfo;
	}

};
