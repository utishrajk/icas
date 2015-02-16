package com.feisystems.icas.infrastructure;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.icas.domain.provider.IndividualProvider;
import com.feisystems.icas.domain.provider.IndividualProviderRepository;
import com.feisystems.icas.domain.provider.VerificationToken;
import com.feisystems.icas.infrastructure.ApplicationConfig;
import com.feisystems.icas.infrastructure.EmailServiceTokenModel;
import com.feisystems.icas.infrastructure.MailSenderService;
import com.feisystems.icas.infrastructure.MailSenderServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring/applicationContext.xml")
@Transactional
@WebAppConfiguration
public class MailSenderServiceImplTest {

	private MailSenderService mailSenderService;

	private MockJavaMailSender mailSender;

	@Autowired
	VelocityEngine velocityEngine;

	@Autowired
	ApplicationConfig config;

	@Autowired
	IndividualProviderRepository userRepository;

	@Before
	public void setUpServices() {
		mailSender = new MockJavaMailSender();
		// mailSenderService is initialized by Spring as
		// @Service("mailsenderService") which is simulated here
		mailSenderService = new MailSenderServiceImpl(mailSender, velocityEngine);
		((MailSenderServiceImpl) mailSenderService).setConfig(config);
	}

	@Test
	public void sendConfirmationEmail() throws Exception {
		IndividualProvider user = retrieveIndividualProvider();
		VerificationToken token = new VerificationToken(user, VerificationToken.VerificationTokenType.emailConfirmation, 5);
		mailSenderService.sendVerificationEmail(new EmailServiceTokenModel(user, token, config.getHostNameUrl()));
		assertOnMail(user, token);
	}

	@Test
	public void sendRegistrationEmail() throws Exception {
		IndividualProvider user = retrieveIndividualProvider();
		VerificationToken token = new VerificationToken(user, VerificationToken.VerificationTokenType.emailRegistration, 5);
		mailSenderService.sendRegistrationEmail(new EmailServiceTokenModel(user, token, config.getHostNameUrl()));
		assertOnMail(user, token);
	}

	@Test
	public void sendLostPasswordEmail() throws Exception {
		IndividualProvider user = retrieveIndividualProvider();
		VerificationToken token = new VerificationToken(user, VerificationToken.VerificationTokenType.lostPassword, 5);
		mailSenderService.sendLostPasswordEmail(new EmailServiceTokenModel(user, token, config.getHostNameUrl()));
		assertOnMail(user, token);
	}

	@Test
	public void sendLostPasswordAndSecurityQuestionsEmail() throws Exception {
		IndividualProvider user = retrieveIndividualProvider();
		VerificationToken token = new VerificationToken(user, VerificationToken.VerificationTokenType.lostPasswordAndSecurityQuestions, 5);
		mailSenderService.sendLostPasswordAndSecurityQuestionsEmail(new EmailServiceTokenModel(user, token, config.getHostNameUrl()));
		assertOnMail(user, token);
	}

	private IndividualProvider retrieveIndividualProvider() {
		IndividualProvider user = new IndividualProvider();
		user.setFirstName("Tommy");
		user.setLastName("Ng");
		user.setEmail("tommy.ng@mailinator.com");
		return user;
	}

	private void assertOnMail(IndividualProvider user, VerificationToken token) throws MessagingException, IOException {
		List<MimeMessage> messages = mailSender.getMessages();
		assertThat(messages.size(), equalTo(1));
		MimeMessage message = messages.get(0);
		assertThat(message.getAllRecipients()[0].toString(), equalTo((user.getEmail())));
	}

}
