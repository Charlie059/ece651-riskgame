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

public class AttackChecker extends MoveChecker{
    public AttackChecker(AccountID accountID, GameHashMap gameHashMap, AccountHashMap accountHashMap, ArrayList<ArrayList<Integer>> _moveUnits, String from, String to, GameID ID){
        super(accountID,gameHashMap, accountHashMap, _moveUnits, from, to, ID);
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
