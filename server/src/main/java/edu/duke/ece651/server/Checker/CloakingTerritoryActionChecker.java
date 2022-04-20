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
        if(player.getCurrTechLevel()<3){
            //false case
            this.errMessage = "ClockingTerritory Error: Tech Level should be larger than 2!";
            return this.errMessage;
        }
        //Check if Enough Tech Resource
        if(player.getTechResource()<cost){
            //false case
            this.errMessage = "ClockingTerritory Error: does not have enough Tech Resource!";
            return this.errMessage;
        }
        //Check if Territory Exist
        if(map.getTerritoryList().get(cloakTerritoryAction.getFrom())==null){
            //false case
            this.errMessage = "ClockingTerritory Error: territory does not exist!";
            return this.errMessage;
        }
        //Check if Territory is mine
        if(!map.getTerritoryList().get(cloakTerritoryAction.getFrom()).getOwnerId().equals(this.accountID)){
            //false case
            this.errMessage = "ClockingTerritory Error: cannot cloak your own territory!";
            return this.errMessage;
        }
        //Check if Territory has been Cloaked
        if(map.getTerritoryList().get(cloakTerritoryAction.getFrom()).isCloaked()){
            //false case
            this.errMessage = "ClockingTerritory Error: territory has been cloaked!";
            return this.errMessage;
        }
        //true case
        this.errMessage = null;
        return this.errMessage;
    }
}
