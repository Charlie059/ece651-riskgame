package edu.duke.ece651.client;

import edu.duke.ece651.shared.*;

import java.util.HashMap;

public class MoveChecker {
    protected Map map;
    protected HashMap<Integer, Integer> moveUnits;
    protected final String from_name;
    protected final  String to_name;
    protected int playerID;
    public MoveChecker(Map _map, HashMap<Integer, Integer> _moveUnits, String from, String to, int ID){
        map = _map;
        moveUnits = _moveUnits;
        from_name = from;
        to_name = to;
        playerID = ID;
    }


    //TODO: check same ownership
    //TODO: check path exist

    /**
     * check if ownerships are same and if path exists
     * @param ID
     * @param from
     * @param to
     * @return
     */
    public String doCheck(int ID, String from, String to) {
        String output = null;
        boolean isValid = false;
        try {
            isValid = map.isPathExist(ID, from, to);
        }
        catch(IllegalArgumentException illegalArg){
            output = illegalArg.getMessage();
            return output;
        }
        if (!isValid){
            output = "Move Error: there is no path b/w \"" + from_name +"\" Territory and \""+ to_name +"\" Territory!";
        }
        return output;
    }

    public String getFrom_name() {
        return from_name;
    }

    public String getTo_name() {
        return to_name;
    }
}
