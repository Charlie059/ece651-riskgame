package edu.duke.ece651.shared;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientJSONParser implements JSONParser{
    private String JSONString;
    private ArrayList<Action> actionArrayList;
    private Integer playerID;
    private Map map;

    public ClientJSONParser(String JSONString, Map map){
        this.JSONString = JSONString;
        this.actionArrayList = new ArrayList<>();
        this.map = map;
    }

    public ArrayList<Action> getActionArrayList() {
        return actionArrayList;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    @Override
    public void doParse() {
        JSONObject obj = new JSONObject(this.JSONString);
        // Set Player ID
        this.playerID = obj.getInt("playerID");
        JSONArray actionArr = obj.getJSONArray("actions");

        // Get action array
        for(int i = 0; i < actionArr.length(); i++){
            String actionType = actionArr.getJSONObject(i).getString("actionType");
            String from = actionArr.getJSONObject(i).isNull("from")? null : actionArr.getJSONObject(i).getString("from");
            String to = actionArr.getJSONObject(i).getString("to");
            JSONArray unitsArr = actionArr.getJSONObject(i).getJSONArray("units");

            // create unitHashMap
            HashMap<Integer, Integer> unitHashMap = new HashMap<>();
            for(int j = 0; j < unitsArr.length(); j++){
                Integer level = unitsArr.getJSONObject(j).getInt("level");
                Integer value = unitsArr.getJSONObject(j).getInt("value");
                unitHashMap.put(level,value);
            }

            // create action
            Action action;
            if (actionType.equals("deploy")) action = new DeployAction(this.map.getTerritoryList().get(to), unitHashMap);
            else if(actionType.equals("attack")) action = new AttackAction(this.map.getTerritoryList().get(from) ,this.map.getTerritoryList().get(to), unitHashMap);
            else action = new MoveAction(this.map.getTerritoryList().get(from) ,this.map.getTerritoryList().get(to), unitHashMap);

            // add action into the actionList
            actionArrayList.add(action);
        }

    }
}
