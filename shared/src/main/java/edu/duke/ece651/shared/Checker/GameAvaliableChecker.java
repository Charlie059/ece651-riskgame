package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.HashMap;

public class GameAvaliableChecker extends ActionChecker {


    public GameAvaliableChecker(AccountID accountID, HashMap<Integer, Game> gameHashMap, HashMap<String, Account> playerHashMap) {
        super(gameHashMap, playerHashMap, accountID);
    }

    @Override
    public boolean doCheck() {
        return false;
    }

}
