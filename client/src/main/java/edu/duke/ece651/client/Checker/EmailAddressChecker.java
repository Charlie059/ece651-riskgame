package edu.duke.ece651.client.Checker;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailAddressChecker extends Checker{
    @Override
    public boolean doCheck(String[] userInput){
        // Get the email address
        String emailAddress = userInput[0];
        return isValidEmailAddress(emailAddress);
    }


    /**
     * Official method from the Java Mail
     * @param email
     * @return
     */
    private static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
