package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.SpyUpgradeAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;
import edu.duke.ece651.shared.map.Map;

public class SpyUpgradeChecker extends ActionChecker {
    SpyUpgradeAction spyUpgradeAction;
    Map map;
    Player player;

    public SpyUpgradeChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID, Map map, Player currplayer, SpyUpgradeAction spyUpgradeAction) {
        super(gameHashMap, accountHashMap, accountID);
        this.spyUpgradeAction = spyUpgradeAction;
        this.map = map;
        this.player = currplayer;
    }

    @Override
    public String doCheck() {
        //Check Player has one Upgrade chance
        if(!player.haveCard(new CardType().getSpecialSpyUpgrade().get(0))){
            //return false;
            this.errMessage = "SpyUpgrade Error: player does have SpyUpgrade Card!";
            return this.errMessage;
        }
        //The Spy Should be mine and should not be upgrade to same type
        //Check If UUID Exist
        if (map.getTerritoryList().containsKey(spyUpgradeAction.getFrom())) {
            if (map.getTerritoryList().get(spyUpgradeAction.getFrom()).
                    getSpy(spyUpgradeAction.getSpyUUID()) != null) {
                if (map.getTerritoryList().get(spyUpgradeAction.getFrom()).
                        getSpy(spyUpgradeAction.getSpyUUID()).getSpyOwnerAccountID().
                        getAccountID().equals(this.accountID.getAccountID())) {
                    //Spy Type should not be upgrade to same type
                    if (!map.getTerritoryList().get(spyUpgradeAction.getFrom()).
                            getSpy(spyUpgradeAction.getSpyUUID()).
                            getSpyType().equals(spyUpgradeAction.getType())) {
                        this.errMessage = null;
                        return this.errMessage;
                    }
                    else{
                        this.errMessage = "SpyUpgrade Error: cannot upgrade spy to same type!";
                    }
                }
                else{
                    this.errMessage = "SpyUpgrade Error: cannot upgrade enemy's spy!";
                }
            }
            else{
                this.errMessage = "SpyUpgrade Error: spy does not exist!";
            }
        }
        else{
            this.errMessage = "SpyUpgrade Error: Territory does not exist!";
        }
        return this.errMessage;
    }
}
