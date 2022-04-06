package edu.duke.ece651.client.Checker;

import edu.duke.ece651.client.Model.GameModel;

import java.util.HashSet;

public class UpgradeChecker extends Checker{
    @Override
    public boolean doCheck(String[] userInput) {
        // Check null
        for (int i = 0; i < userInput.length; i++) {
            if(userInput[i] == null) return false;
        }

        HashSet<String> myTerrList = GameModel.getInstance().getMyTerrList();

        if(!myTerrList.contains(userInput[0])) return false; // check terrFrom

        if(!checkNum(userInput[1], 0, 7)) return false; // check if selectCurLevel is between 0 - 7
        if(!checkNum(userInput[2], 1, 1)) return false; // check if num is 1
        if(!checkNum(userInput[3], 0, 7)) return false; // check if selectUpgradeLevel is between 0 - 7
        return true;
    }
}
