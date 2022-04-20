package edu.duke.ece651.client.Email;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailSenderTest {

    @Test
    void sendEmail() {
        EmailSender emailSender = new EmailSender();
        emailSender.sendEmail("pad128g@icloud.com", "aaa");
    }
}