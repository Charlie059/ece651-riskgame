package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.PlayerHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class AttackChecker extends ActionChecker{
    public AttackChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID) {
        super(gameHashMap, accountHashMap, accountID);
    }

    /**
     * check if ownerships are correct and if two territories are adjacent
     * @return
     */
    @Override
    public boolean doCheck() {
        boolean isValid = false;
        try {
          //  isValid = map.isAdjacent(playerID, from_name, to_name);
        }
        //incorrect ownerships
        catch(IllegalArgumentException illegalArg){
            //output = illegalArg.getMessage();
            return false;
        }
        //not adjacent
        return isValid;
    }
}
