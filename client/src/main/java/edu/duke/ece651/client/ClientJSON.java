package edu.duke.ece651.client;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
    Convert action lists to JSON and send
 */
public class ClientJSON {
    private Integer playerID;
    private ArrayList<Action> actionList;

    public ClientJSON(Integer playerID, ArrayList<Action> actionList){
        this.playerID = playerID;
        this.actionList = actionList;
    }

    /**
     * Convert actionList to JSONObject
     * @return
     */
    public JSONObject convertTo(){
        JSONObject ans = new JSONObject();
        List<JSONObject> actionLists = new ArrayList<JSONObject> ();
        for(int i = 0; i < this.actionList.size(); i++){
            JSONObject action = new JSONObject();
            action.put("actionType", this.actionList.get(i).getActionName());
            action.put("from", this.actionList.get(i).getFrom() == null ? JSONObject.NULL : this.actionList.get(i).getFrom().getName());
            action.put("to", this.actionList.get(i).getTo() == null ? JSONObject.NULL : this.actionList.get(i).getTo().getName());

            List<JSONObject> unitsList = new ArrayList<> ();
            JSONObject units = new JSONObject();
            HashMap<Integer, Integer> unitMap = this.actionList.get(i).getUnitNumber();
            for (var entry : unitMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                units.put("level", key);
                units.put("value", value);
                unitsList.add(units);
            }
            action.put("units", unitsList);
            actionLists.add(action);

        }
        ans.put("playerID", this.playerID);
        ans.put("actions", actionLists);

        return ans;
    }


}
