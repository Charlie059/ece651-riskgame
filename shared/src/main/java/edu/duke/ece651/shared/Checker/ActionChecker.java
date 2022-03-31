package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.HashMap;

public abstract class ActionChecker {

    protected HashMap<Integer, Game> gameHashMap;
    protected HashMap<String, Account> accountHashMap;
    protected AccountID accountID;

    public ActionChecker(HashMap<Integer, Game> gameHashMap, HashMap<String, Account> accountHashMap, AccountID accountID) {
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        this.accountID = accountID;
    }

    abstract boolean doCheck();


}
