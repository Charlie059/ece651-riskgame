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
        this.playerID = accountID.getAccountID();
        this.gameID = gameID;
        Game game = gameHashMap.get(this.gameID);
        player = game.getPlayerHashMap().get(accountID);
        this.map = player.getWholeMap();
    }

    /**
     * check if ownerships are same and if path exists
     * @return
     */
    @Override
    public boolean doCheck() {
        boolean isValid;
        try {
            //isValid = map.isPathExist(playerID, from_name, to_name);
        }
        catch(IllegalArgumentException illegalArg){
            //output = illegalArg.getMessage();
            return false;
        }
        //return isValid;
        return false;
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
}
