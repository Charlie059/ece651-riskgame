package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.HashMap;

public abstract class ActionChecker {

    protected HashMap<GameID, Game> gameHashMap;
    protected HashMap<AccountID, Account> accountHashMap;
    protected AccountID accountID;

    public ActionChecker(HashMap<GameID, Game> gameHashMap, HashMap<AccountID, Account> accountHashMap, AccountID accountID) {
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        this.accountID = accountID;
    }

    public abstract boolean doCheck();


}
