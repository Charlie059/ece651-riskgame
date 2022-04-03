package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.PlayerHashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class UpgradeTechChecker extends ActionChecker{
    private Integer nextLevel;
    private Integer currTechResource;
    private ArrayList<Integer> TechLevelUpgradeList;
    private int cost;
    public UpgradeTechChecker(AccountID accountID,
                              GameHashMap gameHashMap,
                              AccountHashMap accountHashMap,
                              Integer _nextLevel,
                              Integer _currTechResource,
                              ArrayList<Integer> _TechLevelUpgradeList
                              ){
        super( gameHashMap,accountHashMap ,accountID);
        nextLevel = _nextLevel;
        currTechResource = _currTechResource;
        TechLevelUpgradeList = _TechLevelUpgradeList;
        cost = TechLevelUpgradeList.get(nextLevel);
    }


    @Override
    public boolean doCheck(){
        //valid iff nextLevel <= 6 and player has enough tech resource
        if (nextLevel <= 6 && cost <= currTechResource) {
            return true;
        }
        return false;
    }

    public int getCost(){return cost;}

}
