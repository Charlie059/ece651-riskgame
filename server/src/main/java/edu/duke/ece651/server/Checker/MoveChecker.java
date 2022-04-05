package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;

import java.util.ArrayList;

public class MoveChecker extends ActionChecker{
    protected Map map;
    protected ArrayList<ArrayList<Integer>> moveUnits;
    protected final String from_name;
    protected final  String to_name;
    protected int totalCost;
    public MoveChecker(AccountID accountID,
                       GameHashMap gameHashMap,
                       AccountHashMap accountHashMap,
                       ArrayList<ArrayList<Integer>> _moveUnits,
                       String from,
                       String to,
                       GameID gameID,
                       Integer totalCost){
        super(gameHashMap, accountHashMap, accountID);
        this.moveUnits = _moveUnits;
        this.from_name = from;
        this.to_name = to;
        this.totalCost =totalCost;
        this.map = this.gameHashMap.get(gameID).getMap();
    }


    /**
     * check if ownerships are same and if path exists
     * @return
     */
    @Override
    public boolean doCheck() {
        boolean isValid;
        try {
            //check whether path exists
            isValid = map.isPathExist(accountID, from_name, to_name);
            //If path Exist, and totalCost is not wrong
            if (isValid && this.totalCost > 0){
                return true;
            }
            return false;
        }
        catch(IllegalArgumentException illegalArg){
            //output = illegalArg.getMessage();
            return false;
        }
    }

    public String getFrom_name() {
        return from_name;
    }

    public String getTo_name() {
        return to_name;
    }


    public Map getMap() {
        return map;
    }

    public int getTotalCost(){return this.totalCost;}
}
