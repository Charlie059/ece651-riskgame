package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.HashMap;

public class AuthentificationChecker extends ActionChecker {

    private String recvAccount;
    private String recvPassword;

    public AuthentificationChecker(AccountID accountID,HashMap<Integer, Game> gameHashMap, HashMap<String, Account> playerHashMap, String recvAccount, String recvPassword) {
        super(gameHashMap, playerHashMap, accountID);
        this.recvAccount = recvAccount;
        this.recvPassword = recvPassword;
    }

    @Override
    public boolean doCheck() {
        //Firstly check account exist in Database
        if (this.playerHashMap.containsKey(this.recvAccount)) {
            Account account = this.playerHashMap.get(this.recvAccount);
            //Secondly check account password is correct
            if (!account.getPassword().equals(this.recvPassword)) return false;
            else return true;
        } else return false;
    }
}
