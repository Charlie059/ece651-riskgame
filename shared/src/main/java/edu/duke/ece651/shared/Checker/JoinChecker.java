package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.HashMap;

public class JoinChecker extends ActionChecker {
    private Integer enterGameID;

    public JoinChecker(HashMap<Integer, Game> gameHashMap, HashMap<String, Account> accountHashMap, AccountID accountID, Integer enterGameID) {
        super(gameHashMap, accountHashMap, accountID);
        this.enterGameID = enterGameID;
    }

    @Override
    public boolean doCheck() {
        if (gameHashMap.containsKey(enterGameID) &&
                gameHashMap.get(enterGameID).getPlayerHashMap().size() < gameHashMap.get(enterGameID).getNumOfPlayer()) {
            return true;
        } else {
            return false;
        }

    }
}
