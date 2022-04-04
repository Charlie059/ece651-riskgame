package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

public class CommitChecker extends ActionChecker{
   GameID currGameID;
    public CommitChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID, GameID gameID) {
        super(gameHashMap, accountHashMap, accountID);
    this.currGameID = gameID;
    }

    @Override
    public boolean doCheck() {
        //Check if Deploy has finished and use up all
        if(this.gameHashMap.get(this.currGameID).getPlayerHashMap().get(this.accountID).getTotalDeployment()>0){
            return false;
        }
        return true;
    }
}
