package com.example.roughapi.service;

import com.example.roughapi.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    private final JavaMailSender emailSender;

    // @Value("${spring.mail.host}")
    private String host = "http://localhost:8000";

    @Override
    public void sendSimpleMailMessage(String name, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("New Account Verification");
            message.setFrom("priyanshu16095@gmail.com");
            message.setTo("2023359463.priyanshu@ug.sharda.ac.in");
            message.setText(EmailUtils.getEmailMessage(name, host, token));
            emailSender.send(message);
        } catch(Exception e) {
            throw new RuntimeException("Unable to send email");
        }
    }

    @Override
    public void sendMimeMessageWithAttachment(String name, String token) {

    }

    @Override
    public void sendMimeMessageWithEmbeddedImage(String name, String token) {

    }

    @Override
    public void sendMimeMessageWithEmbeddedFile(String name, String token) {

    }

    @Override
    public void sendHtmlEmail(String name, String token) {

    }

    @Override
    public void sendHtmlEmailWithEmbeddedFiles(String name, String token) {

    }
}
