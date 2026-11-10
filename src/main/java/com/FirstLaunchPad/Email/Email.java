package com.FirstLaunchPad.Email;

import java.nio.file.Path;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

final class Email {
    private Email() {
    }

    static void send(FirReport report, String recipient, Path attachment) throws Exception {
        final String user = required("FIR_MAIL_USER");
        final String credential = required("FIR_MAIL_CREDENTIAL");
        String host = valueOrDefault("FIR_MAIL_HOST", "smtp.gmail.com");
        String port = valueOrDefault("FIR_MAIL_PORT", "465");

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, credential);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject("FIR registration confirmation: " + report.firNumber());

        MimeBodyPart body = new MimeBodyPart();
        body.setContent(EmailTemplate.render(report), "text/html; charset=UTF-8");
        MimeBodyPart file = new MimeBodyPart();
        file.attachFile(attachment.toFile());

        MimeMultipart parts = new MimeMultipart();
        parts.addBodyPart(body);
        parts.addBodyPart(file);
        message.setContent(parts);
        Transport.send(message);
    }

    private static String required(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(name + " is not configured.");
        }
        return value;
    }

    private static String valueOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}
