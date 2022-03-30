package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.map.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveChecker extends ActionChecker{
    protected Map map;
    protected ArrayList<ArrayList<Integer>> moveUnits;
    protected final String from_name;
    protected final  String to_name;
    protected int playerID;
    public MoveChecker(HashMap<Integer, Game> gameHashMap, HashMap<String, Player> playerHashMap, Map _map, ArrayList<ArrayList<Integer>> _moveUnits, String from, String to, int ID){
        super(gameHashMap, playerHashMap);
        map = _map;
        moveUnits = _moveUnits;
        from_name = from;
        to_name = to;
        playerID = ID;
    }

    /**
     * check if ownerships are same and if path exists
     * @return
     */
    @Override
    public boolean doCheck() {
        boolean isValid;
        try {
            isValid = map.isPathExist(playerID, from_name, to_name);
        }
        catch(IllegalArgumentException illegalArg){
            //output = illegalArg.getMessage();
            return false;
        }
        return isValid;
    }

    public String getFrom_name() {
        return from_name;
    }

    public String getTo_name() {
        return to_name;
    }
}
