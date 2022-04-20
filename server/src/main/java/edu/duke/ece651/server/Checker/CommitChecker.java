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
    public String doCheck() {
        //Check if Deploy has finished and use up all
        if(this.gameHashMap.get(this.currGameID).getPlayerHashMap().get(this.accountID).getTotalDeployment()>0){
            //false case
            this.errMessage = "Deploy Error: Total deployment is not used up!";
            return this.errMessage;
        }
        //true case
        this.errMessage = null;
        return this.errMessage;
    }
}
