package com.feisystems.icas.domain.patient;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.feisystems.icas.service.patient.PatientService;
import com.feisystems.icas.service.reference.AdministrativeGenderCodeService;
import com.feisystems.icas.service.reference.RaceCodeService;
import com.feisystems.icas.service.reference.StateCodeService;
import com.feisystems.icas.web.PatientController;
import com.feisystems.icas.web.RaceCodeController;
import com.feisystems.icas.web.StateCodeController;

@Configuration
@EnableWebMvc
public class PatientTestContext extends WebMvcConfigurerAdapter {

	private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

		messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
		messageSource.setUseCodeAsDefaultMessage(true);

		return messageSource;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public PatientController patientController() {
		return new PatientController();
	}

	@Bean
	public RaceCodeController receCodeController() {
		return new RaceCodeController();
	}

	@Bean
	public StateCodeController stateCodeController() {
		return new StateCodeController();
	}

	@Bean
	public PatientService patientService() {
		return Mockito.mock(PatientService.class);
	}

	@Bean
	public AdministrativeGenderCodeService administrativeGenderCodeService() {
		return Mockito.mock(AdministrativeGenderCodeService.class);
	}

	@Bean
	public RaceCodeService raceCodeService() {
		return Mockito.mock(RaceCodeService.class);
	}

	@Bean
	public StateCodeService stateCodeService() {
		return Mockito.mock(StateCodeService.class);
	}

}
