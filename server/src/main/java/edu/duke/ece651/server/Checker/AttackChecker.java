package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.PlayerHashMap;
import edu.duke.ece651.shared.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class AttackChecker extends ActionChecker{
    protected Map map;
    protected ArrayList<ArrayList<Integer>> attackUnits;
    protected final String from_name;
    protected final  String to_name;
    protected int totalCost;

    public AttackChecker(GameHashMap gameHashMap,
                         AccountHashMap accountHashMap,
                         AccountID accountID,
                         ArrayList<ArrayList<Integer>> _attackUnits,
                         String from,
                         String to,
                         GameID gameID,
                         Integer totalCost) {
        super(gameHashMap, accountHashMap, accountID);
        this.attackUnits = _attackUnits;
        this.from_name = from;
        this.to_name = to;
        this.totalCost =totalCost;
        this.map = this.gameHashMap.get(gameID).getMap();
    }

    /**
     * check if ownerships are correct and if two territories are adjacent
     * @return
     */
    @Override
    public boolean doCheck() {
        boolean isValid = false;
        try {
            //check if from and to are adjacent
            isValid = map.isAdjacent(this.accountID, from_name, to_name);
            return isValid;
        }
        //incorrect ownerships
        catch(IllegalArgumentException illegalArg){
            //output = illegalArg.getMessage();
            return false;
        }
    }
}
