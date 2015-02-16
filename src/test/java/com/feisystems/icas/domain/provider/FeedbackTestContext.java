package com.feisystems.icas.domain.provider;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.feisystems.icas.service.provider.services.IndividualProviderFeedbackServicesService;
import com.feisystems.icas.web.IndividualProviderFeedbackController;

@Configuration
@EnableWebMvc
public class FeedbackTestContext extends WebMvcConfigurerAdapter {

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
	public IndividualProviderFeedbackController individualProviderFeedbackController() {
		return new IndividualProviderFeedbackController();
	}

	@Bean
	public IndividualProviderFeedbackServicesService individualProviderFeedbackServicesService() {
		return Mockito.mock(IndividualProviderFeedbackServicesService.class);
	}

}
