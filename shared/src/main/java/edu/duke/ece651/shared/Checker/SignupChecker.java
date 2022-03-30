package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;

import java.util.HashMap;

/**
 * Check Signup Account should not exist
 * Exist: false
 * Not Exist: true
 */
public class SignupChecker extends ActionChecker{
    private String recvAccount;
    public SignupChecker(HashMap<Integer, Game> gameHashMap, HashMap<String, Player> playerHashMap, String recvAccount) {
        super(gameHashMap, playerHashMap);
        this.recvAccount = recvAccount;
    }

    @Override
    boolean doCheck() {
        if (!this.playerHashMap.containsKey(this.recvAccount)) {
            return true;
        } else return false;
    }
}
