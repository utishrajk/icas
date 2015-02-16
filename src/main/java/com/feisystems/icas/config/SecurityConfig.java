package com.feisystems.icas.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

import com.feisystems.icas.security.AjaxAuthenticationFailureHandler;
import com.feisystems.icas.security.AjaxAuthenticationSuccessHandler;
import com.feisystems.icas.security.AjaxLogoutSuccessHandler;
import com.feisystems.icas.security.AuthoritiesConstants;
import com.feisystems.icas.security.Http401UnauthorizedEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Inject
	private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

	@Inject
	private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

	@Inject
	private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

	@Inject
	private Http401UnauthorizedEntryPoint authenticationEntryPoint;

	@Autowired
	@Qualifier("customAuthenticationProvider")
	private AuthenticationProvider authenticationProvider;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/src/main/webapp/**").antMatchers("/assets/**").antMatchers("/template*js");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
//				.addFilterAfter(new CsrfTokenGeneratorFilter(), CsrfFilter.class)
				 .csrf().disable()

				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedPage("/app/403")
				.and()

				.formLogin()
				.loginProcessingUrl("/app/authentication")
				.successHandler(ajaxAuthenticationSuccessHandler)
				.failureHandler(ajaxAuthenticationFailureHandler)
				.usernameParameter("j_username")
				.passwordParameter("j_password").permitAll()
				.and()

				.logout().logoutUrl("/app/logout")

				.logoutSuccessHandler(ajaxLogoutSuccessHandler)
				.deleteCookies("JSESSIONID").permitAll()
				.and()

				.authorizeRequests()

				.antMatchers("/").permitAll()

				.antMatchers("/medlineplus*").authenticated().antMatchers("/medlineplus/**").authenticated()

				.antMatchers("/app/rest/authenticate").permitAll()

				.antMatchers("/allergys").permitAll()

				.antMatchers("/app/public/**").permitAll().antMatchers("/app/**").authenticated()

				.antMatchers("/patients*").authenticated().antMatchers("/patients/**").authenticated()

				.antMatchers("/resultorganizers*").authenticated().antMatchers("/resultorganizers/**").authenticated()

				.antMatchers("/codes*").authenticated().antMatchers("/codes/**").authenticated()

				.antMatchers("/result*").authenticated().antMatchers("/result/**").authenticated()
				
				.antMatchers("/medications*").authenticated().antMatchers("/medications/**").authenticated()
				

				.antMatchers("/problems*").authenticated().antMatchers("/problems/**").authenticated()

				.antMatchers("/outcomes*").authenticated().antMatchers("/outcomes/**").authenticated()

				.antMatchers("/socialhistorys*").authenticated().antMatchers("/socialhistorys/**").authenticated()

				.antMatchers("/procedureobservations*").authenticated().antMatchers("/procedureobservations/**").authenticated()

				.antMatchers("/decisionservice*").authenticated().antMatchers("/decisionservice/**").authenticated()

				.antMatchers("/sms*").authenticated().antMatchers("/sms/**").authenticated()

				.antMatchers("/recommendation*").authenticated().antMatchers("/recommendation/**").authenticated()

				.antMatchers("/individualproviders*").authenticated().antMatchers("/individualproviders/**").authenticated()

				.antMatchers("/individualprovidersfeedback*").authenticated().antMatchers("/individualprovidersfeedback/**").authenticated()

				.antMatchers("/organizationalproviders*").hasRole(AuthoritiesConstants.ADMIN).antMatchers("/organizationalproviders/**")
				.hasRole(AuthoritiesConstants.ADMIN);

	}

	@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
	private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
	}

}
