package org.icddrb.otp_auth.service.impl;

import jakarta.mail.internet.MimeMessage;
import org.icddrb.otp_auth.model.EmailInfo;
import org.icddrb.otp_auth.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.icddrb.otp_auth.exception.BaseException;
import org.icddrb.otp_auth.utils.BeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private final static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTextEmail(EmailInfo emailInfo) {
        logger.info("sendTextEmail start");
        try {
            String checkValidation = new BeanValidator<EmailInfo>().checkValidation(emailInfo);
            if (checkValidation != null){
                throw new BaseException(checkValidation);
            }
            MailProperties mailProperties = new MailProperties();
            SimpleMailMessage message = new SimpleMailMessage();

            mailProperties.setUsername(username);
            mailProperties.setPassword(password);
            message.setTo(emailInfo.getEmailTo());
            message.setFrom(username);
            message.setSubject(emailInfo.getSubject());
            message.setText(emailInfo.getMessage());

            logger.info("mail infos " + new ObjectMapper().writeValueAsString(message));
            javaMailSender.send(message);
            logger.info("sendTextEmail done");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }

    public void sendHtmlEmail(EmailInfo emailInfo) {
        logger.info("sendHtmlEmail start");
        try {
            String checkValidation = new BeanValidator<EmailInfo>().checkValidation(emailInfo);
            if (checkValidation != null){
                throw new BaseException(checkValidation);
            }
            MailProperties mailProperties = new MailProperties();
            mailProperties.setUsername(username);
            mailProperties.setPassword(password);

            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper emailContent = new MimeMessageHelper(mail, true);
            emailContent.setSubject(emailInfo.getSubject());
            emailContent.setTo(emailInfo.getEmailTo());
            emailContent.setFrom(username);
            emailContent.setText(emailInfo.getMessage(), true);
            javaMailSender.send(mail);
            logger.info("sendHtmlEmail done");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
}