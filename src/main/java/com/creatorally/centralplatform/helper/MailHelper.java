package com.creatorally.centralplatform.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailHelper {

    @Autowired
    private JavaMailSender emailSender;


    public void mailUserToCreateCredentials(String mail, String link) {
        // Send mail to user to create credentials
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(mail);
            helper.setSubject("Approve Youtuber Account");
            helper.setText(getHtml(link), true);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getHtml(String link) {
        return "<html><body><p>You can click the following link:</p><a href=\"" + link + "\">" + link + "</a></body></html>";

    }

}
