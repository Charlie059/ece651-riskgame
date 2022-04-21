package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.CloakTerritoryAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.map.Map;

public class CloakingTerritoryActionChecker extends ActionChecker {

    Player player;
    Map map;
    Integer cost;
    CloakTerritoryAction cloakTerritoryAction;
    public CloakingTerritoryActionChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID, Integer cost, Map map, Player player, CloakTerritoryAction cloakTerritoryAction) {
        super(gameHashMap, accountHashMap, accountID);
        this.player = player;
        this.map = map;
        this.cost = cost;
        this.cloakTerritoryAction = cloakTerritoryAction;
    }

    @Override
    public String doCheck() {
        //Check if Enough Tech Level
        if(player.getCurrTechLevel()>=3){
            //Check if Enough Tech Resource
            if(player.getTechResource()>=cost){
                //Check if Territory Exist
                if(map.getTerritoryList().get(cloakTerritoryAction.getFrom())!=null){
                    //Check if Territory is mine
                    if(map.getTerritoryList().get(cloakTerritoryAction.getFrom()).getOwnerId().equals(this.accountID)){
                        //Check if Territory has been Cloaked
                        if(!map.getTerritoryList().get(cloakTerritoryAction.getFrom()).isCloaked()){
                            this.errMessage = null;
                            return this.errMessage;
                        }
                        else{
                            //false case
                            this.errMessage = "ClockingTerritory Error: territory has been cloaked!";
                        }
                    }
                    else{
                        //false case
                        this.errMessage = "ClockingTerritory Error: cannot cloak enemy's territory!";
                    }
                }
                else{
                    //false case
                    this.errMessage = "ClockingTerritory Error: territory does not exist!";
                }
            }
            else{
                //false case
                this.errMessage = "ClockingTerritory Error: does not have enough Tech Resource!";
            }
        }
        else{
            //false case
            this.errMessage = "ClockingTerritory Error: Tech Level should be larger than 2!";
        }
        return this.errMessage;
    }
}
