package edu.duke.ece651.server.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.ArrayList;
import java.util.HashMap;

public class UpgradeTechChecker extends ActionChecker{
    private Integer nextLevel;
    private Integer currTechResource;
    private ArrayList<Integer> TechLevelUpgradeList;
    private ArrayList<Integer> UnitLevelUpgradeList;

    public UpgradeTechChecker(AccountID accountID,
                              HashMap<GameID, Game> gameHashMap,
                              HashMap<AccountID, Account> playerHashMap,
                              Integer _nextLevel,
                              Integer _currTechResource,
                              ArrayList<Integer> _TechLevelUpgradeList
                              ){
        super( gameHashMap,accountHashMap ,accountID);
        nextLevel = _nextLevel;
        currTechResource = _currTechResource;
        TechLevelUpgradeList = _TechLevelUpgradeList;
        UnitLevelUpgradeList = _UnitLevelUpgradeList;
    }


    @Override
    public boolean doCheck(){
        if (nextLevel <= 6){
            int cost = TechLevelUpgradeList.get(nextLevel);
            if (cost > currTechResource){
                //player don't have enough tech resource
                return false;
            }
            else{
                return true;
            }
        }else{
            return false;
        }
    }

}
