package edu.duke.ece651.client;

import edu.duke.ece651.shared.Map;

import java.util.HashMap;

public class AttackChecker extends MoveChecker{
    //constructor
    public AttackChecker(Map _map, HashMap<Integer, Integer> _moveUnits, String from, String to, int ID){
        super(_map, _moveUnits, from, to, ID);
    }

    /**
     * check if ownerships are correct and if two territories are adjacent
     * @param ID
     * @param from
     * @param to
     * @return
     */
    @Override
    public String doCheck(int ID, String from, String to) {
        String output = null;
        boolean isValid = false;
        try {
            isValid = map.isAdjacent(ID, from, to);
        }
        //incorrect ownerships
        catch(IllegalArgumentException illegalArg){
            output = illegalArg.getMessage();
            return output;
        }
        //not adjacent
        if (!isValid){
            output = "Attack Error: Territory \""+from_name+"\" and Territory \""+to_name+"\" are not adjacent!";
        }
        return output;
    }
}
