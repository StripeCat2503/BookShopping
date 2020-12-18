/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.utils;

import com.app.constants.GmailConstant;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author DuyNK
 */
public class EmailUtils {

    public static boolean sendEmail(String recipient, String subject, String content) {
        boolean sendSuccess = false;
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", GmailConstant.SMTP_HOST);
        properties.put("mail.smtp.port", GmailConstant.SMTP_PORT);
        properties.put("mail.smtp.ssl.enable", GmailConstant.SMTP_SSL);
        properties.put("mail.smtp.auth", GmailConstant.SMTP_AUTH);

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GmailConstant.SENDER, GmailConstant.PASSWORD);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(GmailConstant.SENDER));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(content, "text/html; charset=UTF-8");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            sendSuccess = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            
        }

        return sendSuccess;
    }
}
