package edu.duke.ece651.client.Checker;

public class PasswordChecker extends Checker {
    private final String PW_PATTERN = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
    @Override
    public boolean doCheck(String[] userInput) {
        return userInput[0].matches(PW_PATTERN);
    }
}
