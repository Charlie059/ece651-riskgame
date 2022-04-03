package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.Collections;
import java.util.HashMap;

public abstract class ActionChecker {

    protected volatile GameHashMap gameHashMap;
    protected volatile AccountHashMap accountHashMap;
    protected volatile AccountID accountID;

    public ActionChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID) {
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        this.accountID = accountID;
    }

    public abstract boolean doCheck();


}
