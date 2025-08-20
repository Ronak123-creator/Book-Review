package com.onlinebook.onlineBookStore.Services.auth;

import com.onlinebook.onlineBookStore.Entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(UserInfo userInfo, String subject, String message){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userInfo.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);

    }
}
