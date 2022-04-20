package edu.duke.ece651.client.Checker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailAddressCheckerTest {

    @Test
    void doCheck() {
        EmailAddressChecker emailAddressChecker = new EmailAddressChecker();
        String emailAdd1 = "cat@qq.com";
        String[] email = new String[1];
        email[0] = emailAdd1;
        assertTrue(emailAddressChecker.doCheck(email));

        String emailAdd2 = "cat00qq.com";
        String[] emai2 = new String[1];
        emai2[0] = emailAdd2;
        assertFalse(emailAddressChecker.doCheck(emai2));

    }
}