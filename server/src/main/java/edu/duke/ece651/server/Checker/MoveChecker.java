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
    protected GameID gameID;
    protected Player player;
    protected int totalCost;
    public MoveChecker(AccountID accountID,
                       GameHashMap gameHashMap,
                       AccountHashMap accountHashMap,
                       ArrayList<ArrayList<Integer>> _moveUnits,
                       String from,
                       String to,
                       GameID gameID){
        super(gameHashMap, accountHashMap, accountID);
        moveUnits = _moveUnits;
        from_name = from;
        to_name = to;
        this.gameID = gameID;
        Game game = gameHashMap.get(this.gameID);
        player = game.getPlayerHashMap().get(accountID);
        this.map = player.getWholeMap();
        this.totalCost = calTotalCost();
    }

    public int calTotalCost(){
            int path_cost = this.player.getWholeMap().shortestPathFrom(this.accountID, this.from_name, this.to_name);
            if (path_cost > 0) {
                int totalMoveUnitNum = 0;
                for (int i = 0; i < this.moveUnits.size(); i++) {
                    totalMoveUnitNum += this.moveUnits.get(i).get(1);
                }
                return totalMoveUnitNum * path_cost;
            }
            return -1;
            //the cost of each
            //move is (total size of territories moved through) * (number of units moved).
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

    public Player getPlayer(){return player;}

    public Map getMap() {
        return map;
    }

    public int getTotalCost(){return this.totalCost;}
}
