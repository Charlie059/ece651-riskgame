package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class AttackChecker extends MoveChecker{
    public AttackChecker(AccountID accountID, HashMap<Integer, Game> gameHashMap, HashMap<String, Account> playerHashMap, Map _map, ArrayList<ArrayList<Integer>> _moveUnits, String from, String to, int ID){
        super(accountID,gameHashMap, playerHashMap, _map, _moveUnits, from, to, ID);
    }
    /**
     * check if ownerships are correct and if two territories are adjacent
     * @return
     */
    @Override
    public boolean doCheck() {
        boolean isValid = false;
        try {
            isValid = map.isAdjacent(playerID, from_name, to_name);
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
