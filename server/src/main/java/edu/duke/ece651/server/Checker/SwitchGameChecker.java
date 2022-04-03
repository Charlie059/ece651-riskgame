package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.HashMap;

public class SwitchGameChecker extends ActionChecker {
    public SwitchGameChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID) {
        super(gameHashMap, accountHashMap, accountID);
    }



    // Check what ???
    @Override
    public boolean doCheck() {

        return false;
    }
}
