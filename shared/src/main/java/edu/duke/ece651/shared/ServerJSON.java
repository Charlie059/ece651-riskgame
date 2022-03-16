package edu.duke.ece651.shared;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerJSON {
    private Map map;

    public ServerJSON(Map map){
        this.map = map;
    }

    /**
     * Convert map to the JSONObject
     * @return JSONObject
     */
    public JSONObject convertTo(){
        JSONObject ans = new JSONObject();
        List<JSONObject> territoryListJSON = new ArrayList<>();
        HashMap<String, Territory> territoryList =  this.map.getTerritoryList();

        // Get each territory from territoryList
        for(var entry: territoryList.entrySet()){
            JSONObject territoryObject = new JSONObject();
            Territory t = entry.getValue();
            String territoryName = t.getName();
            Integer ownID = t.getOwner();
            territoryObject.put("name", territoryName);
            territoryObject.put("ownerID", ownID);

            HashMap<Integer, ArrayList<Unit>> units = t.getUnits();//level, num of units of this level

            ArrayList<JSONObject> unitObjectsArr = new ArrayList<>();

            for(var entry_units: units.entrySet()){
                Integer level = entry_units.getKey();
                ArrayList<Unit> unitsArr = entry_units.getValue();

                JSONObject unitObject = new JSONObject();
                unitObject.put("level", level);
                unitObject.put("value", unitsArr.size());
                unitObjectsArr.add(unitObject);
            }
            territoryObject.put("units", unitObjectsArr);
            territoryListJSON.add(territoryObject);
        }
        ans.put("map", territoryListJSON);
        return ans;
    }

}
