package edu.duke.ece651.client.Checker;

import edu.duke.ece651.client.Model.GameModel;

import java.util.HashSet;

public class AttackChecker extends Checker {

    /**
     * Check if user input attack action is valid
     * @param userInput terrFrom, terrTo, selectLevel, selectNum
     * @return true for pass checker
     */
    @Override
    public boolean doCheck(String[] userInput) {
        // Check null
        for (int i = 0; i < userInput.length; i++) {
            if(userInput[i] == null) return false;
        }

        HashSet<String> myTerrList = GameModel.getInstance().getMyTerrList();
//        HashSet<String> enemyTerrList = GameModel.getInstance().getEnemyTerrList();

        if(!myTerrList.contains(userInput[0])) return false; // check terrFrom
//        if(!enemyTerrList.contains(userInput[1])) return false; // check terrTo


        if(!checkNum(userInput[2], 0, 7)) return false; // check if level between 0 - 7
        if(!checkNum(userInput[3], 1, Integer.MAX_VALUE)) return false; // check if num is positive
        return true;
    }

}
