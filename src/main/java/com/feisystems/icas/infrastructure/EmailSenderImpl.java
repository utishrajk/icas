package com.feisystems.icas.infrastructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;

public class EmailSenderImpl {

    @Autowired
    private transient MailSender mailTemplate;

    public void sendMessage(String mailFrom, String subject, String mailTo, String message) {
        org.springframework.mail.SimpleMailMessage mailMessage = new org.springframework.mail.SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setSubject(subject);
        mailMessage.setTo(mailTo);
        mailMessage.setText(message);
        mailTemplate.send(mailMessage);
    }
}
