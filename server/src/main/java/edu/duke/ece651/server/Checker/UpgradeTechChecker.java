package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.PlayerHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class UpgradeTechChecker extends ActionChecker{
    private Integer nextLevel;
    private Integer currTechResource;
    private Boolean isTechUpgraded;
    private ArrayList<Integer> TechLevelUpgradeList;
    private Player player;
    private int cost;
    public UpgradeTechChecker(AccountID accountID,
                              GameHashMap gameHashMap,
                              AccountHashMap accountHashMap,
                              Boolean isTechUpgraded,
                              ArrayList<Integer> _TechLevelUpgradeList,
                              GameID gameID
                              ){
        super( gameHashMap,accountHashMap ,accountID);
        this.isTechUpgraded = isTechUpgraded;
        this.player = gameHashMap.get(gameID).getPlayerHashMap().get(this.accountID);
        this.nextLevel = this.player.
        this.TechLevelUpgradeList = _TechLevelUpgradeList;
        this.cost = TechLevelUpgradeList.get(nextLevel);
    }


    @Override
    public boolean doCheck(){
        //valid iff nextLevel <= 6 and player has enough tech resource
        if (!this.isTechUpgraded && nextLevel <= 6 && cost <= currTechResource) {
            return true;
        }
        return false;
    }

    public int getCost(){return cost;}

}
