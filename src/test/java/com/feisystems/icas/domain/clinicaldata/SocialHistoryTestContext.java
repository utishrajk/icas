package com.feisystems.icas.domain.clinicaldata;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.feisystems.icas.service.clinicaldata.SocialHistoryService;
import com.feisystems.icas.service.reference.ActStatusCodeService;
import com.feisystems.icas.service.reference.BodySiteCodeService;
import com.feisystems.icas.service.reference.SocialHistoryTypeCodeService;
import com.feisystems.icas.web.ActStatusCodeController;
import com.feisystems.icas.web.SocialHistoryController;
import com.feisystems.icas.web.SocialHistoryTypeCodeController;

@Configuration
@EnableWebMvc
public class SocialHistoryTestContext {
	
	private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
	}
	
	@Bean
	public SocialHistoryController socialHistoryController() {
		return new SocialHistoryController();
	}

	@Bean
	public SocialHistoryTypeCodeController socialHistoryTypeCodeController() {
		return new SocialHistoryTypeCodeController();
	}

	@Bean
	public ActStatusCodeController actStatusCodeController() {
		return new ActStatusCodeController();
	}
	
	@Bean
	public SocialHistoryService socialHistoryService() {
		return Mockito.mock(SocialHistoryService.class);
	}

	@Bean
	public SocialHistoryTypeCodeService socialHistoryTypeCodeService() {
		return Mockito.mock(SocialHistoryTypeCodeService.class);
	}

	@Bean
	public ActStatusCodeService actStatusCodeService() {
		return Mockito.mock(ActStatusCodeService.class);
	}
	
}
