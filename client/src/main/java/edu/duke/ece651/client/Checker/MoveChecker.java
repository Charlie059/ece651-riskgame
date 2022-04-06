package edu.duke.ece651.client.Checker;

import edu.duke.ece651.client.Model.GameModel;

import java.util.HashSet;

public class MoveChecker implements Checker {

    @Override
    public boolean doCheck(String[] userInput) {
        // Check null
        for (int i = 0; i < userInput.length; i++) {
            if(userInput[i] == null) return false;
        }

        HashSet<String> myTerrList = GameModel.getInstance().getMyTerrList();

        if(!myTerrList.contains(userInput[0])) return false; // check terrFrom
        if(!myTerrList.contains(userInput[1])) return false; // check terrTo


        if(!checkNum(userInput[2], 0, 7)) return false; // check if level between 0 - 7
        if(!checkNum(userInput[3], 1, Integer.MAX_VALUE)) return false; // check if num is positive
        return true;
    }

    private boolean checkNum(String userInputNumber, int left, int right){
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
