package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.ArrayList;

public class UpgradeTechChecker extends ActionChecker{
    private ArrayList<Integer> TechLevelUpgradeList;
    private int cost;
    private int nextLevel;
    private GameID gameID;
    private int currTechResource;
    private Player player;
    public UpgradeTechChecker(AccountID accountID,
                              GameHashMap gameHashMap,
                              AccountHashMap accountHashMap,
                              ArrayList<Integer> _TechLevelUpgradeList,
                              GameID gameID
                              ){
        super( gameHashMap,accountHashMap ,accountID);
        this.gameID = gameID;
        Game game = this.gameHashMap.get(this.gameID);
        this.player = game.getPlayerHashMap().get(this.accountID);
        this.nextLevel = player.getCurrTechLevel()+1;
        this.currTechResource = player.getTechResource();
        this.TechLevelUpgradeList = _TechLevelUpgradeList;
        this.cost = TechLevelUpgradeList.get(nextLevel);
    }


    @Override
    public boolean doCheck(){
        //valid iff nextLevel <= 6 and player has enough tech resource
        //only upgrade tech level once for each round
        if (nextLevel <= 6 && cost <= currTechResource && (!this.player.isTechLevelUpgrade())) {
            return true;
        }
        return false;
    }

    public int getCost(){return cost;}

    public int getNextLevel(){return this.nextLevel;}
}
