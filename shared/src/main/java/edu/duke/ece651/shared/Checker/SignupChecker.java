package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.HashMap;

/**
 * Check Signup Account should not exist
 * Exist: false
 * Not Exist: true
 */
public class SignupChecker extends ActionChecker{
    private String recvAccount;
    public SignupChecker(AccountID accountID, HashMap<Integer, Game> gameHashMap, HashMap<String, Account> accountHashMap, String recvAccount) {
        super(gameHashMap, accountHashMap,accountID);
        this.recvAccount = recvAccount;
    }

    @Override
    public boolean doCheck() {
        if (!this.accountHashMap.containsKey(this.recvAccount)) {
            return true;
        } else return false;
    }
}
