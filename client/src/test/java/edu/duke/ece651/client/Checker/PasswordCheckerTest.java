package edu.duke.ece651.client.Checker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordCheckerTest {

    @Test
    public void doCheck() {
        Checker passwordChecker = new PasswordChecker();
        String[] pw = new String[1];
        pw[0] = "ABCDEFG";
        assertFalse(passwordChecker.doCheck(pw));

        pw[0] = "0123456789";
        assertFalse(passwordChecker.doCheck(pw));

        pw[0] = "ABCDE01234";
        assertFalse(passwordChecker.doCheck(pw));

        pw[0] = "abcde01234";
        assertFalse(passwordChecker.doCheck(pw));

        pw[0] = "abcde01234!@#$%";
        assertFalse(passwordChecker.doCheck(pw));

        pw[0] = "Abcab23@qqa123";
        assertTrue(passwordChecker.doCheck(pw));

    }
}