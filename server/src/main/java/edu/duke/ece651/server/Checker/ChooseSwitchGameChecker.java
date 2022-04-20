package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

public class ChooseSwitchGameChecker extends ActionChecker{
    private GameID enterGameID;
    public ChooseSwitchGameChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID, GameID enterGameID) {
        super(gameHashMap, accountHashMap, accountID);
        this.enterGameID = enterGameID;
    }

    @Override
    public String doCheck() {
        //Check if such game exist
        if(this.gameHashMap.containsKey(this.enterGameID)){
            // Check if enterGameID indicate itself game
            if (this.gameHashMap.get(enterGameID).getPlayerHashMap().containsKey(this.accountID)){
                //true case
                this.errMessage = null;
                return this.errMessage;
            }
            else{
                //false case
                this.errMessage = "ChooseSwitchGame Error: game ID does not match!";
                return errMessage;
            }
        }
        //fase case
        this.errMessage = "ChooseSwitchGame Error: game ID does not exist!";
        return errMessage;
    }
}
