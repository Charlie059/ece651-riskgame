package edu.duke.ece651.client.Checker;

public class NewGameChecker extends Checker{

    @Override
    public boolean doCheck(String[] userInput) {
        String numOfPlayerUsrInput = userInput[0];

        // Check if user input contains char
        for(int i = 0; i < numOfPlayerUsrInput.length() ; i++){
            if(!Character.isDigit(numOfPlayerUsrInput.charAt(i))) return false;
        }

        // Try to convert userInput to Int and check the range between 2 to 5
        try {
            int N = Integer.parseInt(numOfPlayerUsrInput);
            if(N < 2 || N > 5){
                return false;
            }
        } // If NumberFormatException
        catch (NumberFormatException n){
            return false;
        }
        return true;
    }
}
