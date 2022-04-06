package edu.duke.ece651.client.Checker;

public abstract class Checker {
    protected boolean doCheck(String[] userInput){return true;}

    protected boolean checkNum(String userInputNumber, int left, int right){
        // Check if user input contains char
        for(int i = 0; i < userInputNumber.length() ; i++){
            if(!Character.isDigit(userInputNumber.charAt(i))) return false;
        }
        // Try to convert userInput to Int and check the range between left to right
        try {
            int N = Integer.parseInt(userInputNumber);
            if(N < left || N > right){
                return false;
            }
        } // If NumberFormatException
        catch (NumberFormatException n){
            return false;
        }
        return true;
    }
}
