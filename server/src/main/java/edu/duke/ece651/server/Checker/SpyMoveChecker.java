package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.SpyMoveAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Spy;

public class SpyMoveChecker extends ActionChecker {
    SpyMoveAction spyMoveAction;
    Integer cost;
    Integer currFoodResource;
    Map map;

    public SpyMoveChecker(
            GameHashMap gameHashMap,
            AccountHashMap accountHashMap,
            AccountID accountID,
            Map map,
            SpyMoveAction spyMoveAction,
            Integer cost,
            Integer currFoodResource
    ) {
        super(gameHashMap, accountHashMap, accountID);
        this.spyMoveAction = spyMoveAction;
        this.cost = cost;
        this.currFoodResource = currFoodResource;
        this.map = map;
    }

    @Override
    public String doCheck() {
        //Check Food Resource enough to move
        if (cost <= currFoodResource) {
            //Check territory does not exist!
            if (map.getTerritoryList().containsKey(spyMoveAction.getFrom())) {
                //Check If UUID Exist
                if (map.getTerritoryList().get(spyMoveAction.getFrom()).
                        getSpy(spyMoveAction.getSpyUUID()) != null) {
                    //And Belongs to me
                    if (map.getTerritoryList().get(spyMoveAction.getFrom()).
                            getSpy(spyMoveAction.getSpyUUID()).
                            getSpyOwnerAccountID().getAccountID().
                            equals(this.accountID.getAccountID())) {
                        this.errMessage = null;
                        return this.errMessage;
                    }
                    else{
                        this.errMessage = "SpyMove Error: cannot move enemy's spy!";
                    }
                }
                else{
                    this.errMessage = "SpyMove Error: spy does not exist!";
                }
            }
            else{
                this.errMessage = "SpyMove Error: territory From does not exist!";
            }
        }
        else{
            this.errMessage = "SpyMove Error: does not have enough Food Resource!";
        }
        return this.errMessage;
    }
}
