package com.Flight_registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Value("${email.username}")
    private String emailUsername;

    public void sendReservationEmail(String recipientEmail, String subject, String text, File ticketFile) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailUsername);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(text);

            // Attach the ticket PDF to the email
            FileSystemResource fileResource = new FileSystemResource(ticketFile);
            helper.addAttachment("Ticket.pdf", fileResource);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

