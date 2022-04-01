package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveChecker extends ActionChecker{
    protected Map map;
    protected ArrayList<ArrayList<Integer>> moveUnits;
    protected final String from_name;
    protected final  String to_name;
    protected String playerID;
    protected Integer gameID;
    public MoveChecker(AccountID accountID, HashMap<GameID, Game> gameHashMap, HashMap<AccountID, Account> accountHashMap, ArrayList<ArrayList<Integer>> _moveUnits, String from, String to, Integer gameID){
        super(gameHashMap, accountHashMap, accountID);
        moveUnits = _moveUnits;
        from_name = from;
        to_name = to;
        this.playerID = accountID.getaccountID();
        this.gameID = gameID;
        Game game = this.gameHashMap.get(this.gameID);
        Player player = game.getPlayerHashMap().get(this.playerID);
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
