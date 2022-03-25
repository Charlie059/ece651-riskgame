package edu.duke.ece651.shared;

import edu.duke.ece651.shared.Action;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
    Convert action lists to JSON and send
 */
public class ClientJSON {
    private Integer playerID;
    private Boolean playAgain;
    private Integer playerNum;
    private Boolean techUpgrade;
    private ArrayList<Upgrade> upgradeList;
    private ArrayList<Action> actionList;
    private JSONObject clientJSON;

    public ClientJSON(Integer playerID,
                      Boolean playAgain,
                      Integer playerNum,
                      Boolean techUpgrade,
                      ArrayList<Upgrade> upgradeList,
                      ArrayList<Action> actionList) {
        this.playerID = playerID;
        this.playAgain = playAgain;
        this.playerNum = playerNum;
        this.techUpgrade = techUpgrade;
        this.upgradeList = upgradeList;
        this.actionList = actionList;
        this.clientJSON = new JSONObject();
        this.constructPlayerID();
    }

    public void constructPlayerID() {
        this.clientJSON.put("playerID", this.playerID);
    }

    public void constructPlayAgain() {
        this.clientJSON.put("playAgain", this.playAgain);
    }

    public void constructPlayerNum() {


    }
    /*
    public JSONObject convertTo() {
        JSONObject ans = new JSONObject();
        List<JSONObject> actionLists = new ArrayList<JSONObject>();
        for (int i = 0; i < this.actionList.size(); i++) {
            JSONObject action = new JSONObject();
            action.put("actionType", this.actionList.get(i).getActionName());
            action.put("from", this.actionList.get(i).getFrom() == null ? JSONObject.NULL : this.actionList.get(i).getFrom().getName());
            action.put("to", this.actionList.get(i).getTo() == null ? JSONObject.NULL : this.actionList.get(i).getTo().getName());

            List<JSONObject> unitsList = new ArrayList<>();

            HashMap<Integer, Integer> unitMap = this.actionList.get(i).getUnitNumber();
            for (var entry : unitMap.entrySet()) {
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                JSONObject units = new JSONObject();
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
    }*/


}
