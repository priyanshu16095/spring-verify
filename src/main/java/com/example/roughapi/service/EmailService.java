package com.example.roughapi.service;


public interface EmailService {
    void sendSimpleMailMessage(String name, String token);
    void sendMimeMessageWithAttachment(String name, String token);
    void sendMimeMessageWithEmbeddedImage(String name, String token);
    void sendMimeMessageWithEmbeddedFile(String name, String token);
    void sendHtmlEmail(String name, String token);
    void sendHtmlEmailWithEmbeddedFiles(String name, String token);
}
