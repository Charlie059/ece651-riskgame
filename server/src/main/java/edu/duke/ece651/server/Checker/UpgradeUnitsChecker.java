package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;

import java.util.ArrayList;

public class UpgradeUnitsChecker extends ActionChecker{
    private String where;
    private Integer newLevel;
    private Integer oldLevel;
    private Integer currentTechResource;
    private Player player;
    private GameID gameID;
    private ArrayList<Integer> UnitLevelUpgradeList;
    private int techCost;
    public UpgradeUnitsChecker(AccountID accountID,
                              GameHashMap gameHashMap,
                              AccountHashMap accountHashMap,
                              String where,
                              Integer _newLevel,
                              Integer _oldLevel,
                               GameID _gameID,
                               ArrayList<Integer> _UnitLevelUpgradeList
    ){
        super( gameHashMap,accountHashMap ,accountID);
        this.newLevel = _newLevel;
        this.oldLevel = _oldLevel;
        this.gameID = _gameID;
        Game game = gameHashMap.get(this.gameID);
        this.player = game.getPlayerHashMap().get(accountID);
        this.UnitLevelUpgradeList = _UnitLevelUpgradeList;
        this.techCost = getCost(this.oldLevel, this.newLevel);
        this.currentTechResource = this.player.getTechResource();
    }

    public int getCost(Integer oldLevel, Integer newLevel){
        int total_cost = 0;
        int count = oldLevel+1;
        if (this.newLevel > this.oldLevel && this.newLevel<= 6) {
            while (count <= newLevel) {
                total_cost += this.UnitLevelUpgradeList.get(count);
                count++;
            }
        }
        return total_cost;
    }
    @Override
    public boolean doCheck(){
        //max level: 6, cannot upgrade level-6 units
        boolean isEnoughTechResource = this.techCost <= this.currentTechResource;
        Territory where_terr = this.player.getMyTerritories().get(where);
        boolean isEnoughUnits = where_terr.getUnits().get(this.oldLevel).getValue() >= 1;
        boolean isNewLevelValid = this.newLevel > this.oldLevel && this.newLevel<= this.player.getCurrTechLevel();
        if (isEnoughTechResource && isEnoughUnits && isNewLevelValid){
            return true;
        }
        return false;
    }

    public int getTechCost(){return this.techCost;}

    public Player getPlayer() {
        return player;
    }
}
