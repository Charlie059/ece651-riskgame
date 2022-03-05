package edu.duke.ece651.client;

import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Territory;

import java.util.HashMap;

public class Player {
    int id;
    HashMap<String, Territory> myTerritories;//all territories of the player
    Map wholeMap;
    /**
     * constructor
     * @param _id
     */
    public Player(int _id){
        id = _id;
        myTerritories = new HashMap<>();
        wholeMap = new Map(3);
        setInitialTerritories();
    }

    /**
     * set player's initial territory based on player's id and wholeMap's groups
     */
    public void setInitialTerritories(){
        for(int i = 0; i < wholeMap.getGroups().size(); i++){
            if (i+1 == id){
                for(int j = 0; j < wholeMap.getGroups().get(i).size(); j++){
                    String currTerrName = wholeMap.getGroups().get(i).get(j);
                    myTerritories.put(currTerrName, wholeMap.getTerritoryList().get(currTerrName));
                }
                break;
            }
        }
    }
}
