package edu.duke.ece651.client.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private final String username = "do_not_reply_mini_ups@outlook.com";
    private final String password = "testing321";
    private Properties props;
    private Session session;

    public EmailSender(){
        this.props = new Properties();
        this.props.put("mail.smtp.auth", "true");
        this.props.put("mail.smtp.starttls.enable", "true");
        this.props.put("mail.smtp.host", "smtp.office365.com");
        this.props.put("mail.smtp.port", "587");
        this.props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        this.session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    public void sendEmail(String toAddress, String verifyCode){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddress));
            message.setSubject("Risk Game's verification code");
            message.setText("Hi, your verification code is " + verifyCode);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
