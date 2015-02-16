package com.feisystems.icas.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Returns a 401 error code (Unauthorized) to the client, when Ajax
 * authentication fails.
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private MessageSource messageSource;

	Locale locale = LocaleContextHolder.getLocale();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		response.setContentType("application/json");

		if (exception instanceof LockedException) {

			// String errorMessage =
			// messageSource.getMessage("exception.account.locked", null,
			// locale);

			response.setStatus(401);
			response.getWriter().print("Your account has been locked.");
		} else if (exception instanceof BadCredentialsException) {

			response.setStatus(401);
			response.getWriter().print("Invalid User Name and/or Password.");
		} else {

			response.setStatus(401);
			response.getWriter().print("Authentication Failed. Please contact the system administrator.");
		}

	}

}
