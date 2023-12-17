package com.logo.logoWithMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    //Updating the team on the status of the main server
    public void sendEmail(String toEmail, String subject, String body ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("navehaisuper@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
                
        if (mailSender != null) {
            mailSender.send(message);
            System.out.println("Message sent successfully");
        } else {
            System.err.println("There is a problem sending the email to the team.");
        }
    }
}
