package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.UnitDeployAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

public class UnitDeployChecker extends ActionChecker{
    Game thisGame;
    Player thisPlayer;
    UnitDeployAction unitDeployAction;

    public UnitDeployChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID, GameID gameID, UnitDeployAction unitDeployAction) {
        super(gameHashMap, accountHashMap, accountID);
        thisGame = this.gameHashMap.get(gameID);
        thisPlayer = thisGame.getPlayerHashMap().get(accountID);
        this.unitDeployAction = unitDeployAction;
    }


    @Override
    public String doCheck() {
        //Check If territory is mine(Deploy can deploy to Mine territory)
        if(thisGame.getMap().getTerritoryList().containsKey(unitDeployAction.getTo())){
            if(thisGame.getMap().getTerritoryList().get(unitDeployAction.getTo()).getOwnerId() == accountID){
                this.errMessage=null;
                return errMessage;
            }
        }

        this.errMessage = "UnitDeploy Error: Territory is not your Territory!";
        return errMessage;
    }
}
