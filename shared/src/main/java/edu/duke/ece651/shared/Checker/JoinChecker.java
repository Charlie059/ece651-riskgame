package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.HashMap;

/**
 * Check 1. Have this game 2. is not full
 */
public class JoinChecker extends ActionChecker {
    private GameID enterGameID;

    public JoinChecker(HashMap<GameID, Game> gameHashMap, HashMap<AccountID, Account> accountHashMap, AccountID accountID, GameID enterGameID) {
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
