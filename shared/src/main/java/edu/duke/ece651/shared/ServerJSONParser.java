package edu.duke.ece651.shared;
import org.json.*;

import java.util.HashMap;

public class ServerJSONParser implements JSONParser{
    private JSONObject JSONObj;
    private final String source;
    private Map myMap;
    private int myID;
    HashMap<String, Territory> myTerritories;
    public ServerJSONParser(String src, Map playerMap, int playerID, HashMap<String, Territory> playerTerritories){
        source = src;
        JSONObj = new JSONObject(source);
        myMap = playerMap;
        myID = playerID;
        myTerritories = playerTerritories;
    }
    /*
    {
  "map": [
    {
      "name": "a1",
      "ownerID": "1",
      "units": [
        {
          "level": 1,
          "value": 3
        },
        {
          "level": 2,
          "value": 5
        }
      ]
    },
    {
      "name": "a2",
      "ownerID": "2",
      "units": [
        {
          "level": 1,
          "value": 3
        },
        {
          "level": 2,
          "value": 5
        }
      ]
    }
  ]
     */

    @Override
    public void doParse(){
        JSONArray getArray = JSONObj.getJSONArray("map");
        for(int i = 0; i < getArray.length(); i++){
            JSONObject getObject = getArray.getJSONObject(i);
            String terrName = getObject.getString("name");
            if (myMap.getTerritoryList().get(terrName) != null){
                Territory t = myMap.getTerritoryList().get(terrName);
                int original_owner = t.getOwner();
                int new_owner = getObject.getInt("ownerID");
                //check if ownership changed
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
                //if ()
            }
            else{
                throw new IllegalArgumentException("Territory name "+ terrName + " is not found!" );
            }
        }
    }
}
