package edu.duke.ece651.shared;
import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerJSONParser implements JSONParser{
    private JSONObject JSONObj;
    private final String source;
    private Map myMap;
    private int myID;
    HashMap<String, Territory> myTerritories;
    /**
     * constructor
     * @param src
     * @param playerMap
     * @param playerID
     * @param playerTerritories
     */
    public ServerJSONParser(String src, Map playerMap, int playerID, HashMap<String, Territory> playerTerritories){
        source = src;
        JSONObj = new JSONObject(source);
        myMap = playerMap;
        myID = playerID;
        myTerritories = playerTerritories;
    }
    public JSONObject getJSONObj(){
        return JSONObj;
    }
    /**
     * helper method:
     * check if the ownership of current territory t has been changed
     * @param new_owner
     * @param t
     */
    public void checkOwnership(int new_owner, Territory t){
        int original_owner = t.getOwner();
        if (new_owner != original_owner){
            t.setOwner(new_owner);
            //if current player wins this territory
            if (new_owner == myID){
                myTerritories.put(t.getName(), t);
            }
            //if current player loses this territory
            else if (original_owner == myID){
                myTerritories.remove(t.getName(), t);
            }
        }
    }

    /**
     * helper method: Check if the number of units in each level of territory t has been changed
     * if changed, update the Units map in territory t (i.e., remove or add)
     * @param getUnitArr
     * @param t
     */
    public void checkNumOfUnitsChanged(JSONArray getUnitArr, Territory t){
        //go through each level of units
        for(int index = 0; index < getUnitArr.length(); index++){
            JSONObject getUnit = getUnitArr.getJSONObject(index);
            int level = getUnit.getInt("level");
            Integer level_Integer = level;
            int numOfNewUnits = getUnit.getInt("value");
            //if the level does not exist
            if (t.getUnits().get(level_Integer) == null){
                t.getUnits().put(level_Integer, new ArrayList<>());
            }
            //if number of units are not equal
            if (numOfNewUnits != t.getUnits().get(level_Integer).size()){

                int numOfOldUnits = t.getUnits().get(level_Integer).size();
                if (numOfNewUnits > numOfOldUnits){
                    int diff = numOfNewUnits - numOfOldUnits;
                    while(diff != 0){
                        Unit u = new Unit(level);
                        t.addUnit(u);
                        diff --;
                    }
                }
                else{
                    int diff = numOfOldUnits - numOfNewUnits;
                    while(diff != 0){
                        t.removeUnit(level);
                        diff --;
                    }
                }

            }
        }
    }

    /**
     * parse the updated map in JSON format received from Server node
     */
    @Override
    public void doParse(){
        JSONArray getArray = JSONObj.getJSONArray("map");
        //go through all territories in the map
        for(int i = 0; i < getArray.length(); i++){
            JSONObject getObject = getArray.getJSONObject(i);
            String terrName = getObject.getString("name");
            if (myMap.getTerritoryList().get(terrName) != null){
                Territory t = myMap.getTerritoryList().get(terrName);
                int new_owner = getObject.getInt("ownerID");
                //check if ownership changed
                checkOwnership(new_owner, t);
                //check if number of units changed
                JSONArray getUnitArr = getObject.getJSONArray("units");
                checkNumOfUnitsChanged(getUnitArr, t);
            }
            else{
                throw new IllegalArgumentException("Territory name "+ terrName + " is not found!" );
            }
        }
    }
}
