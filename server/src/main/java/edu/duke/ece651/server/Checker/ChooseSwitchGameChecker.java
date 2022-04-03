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
    public boolean doCheck() {
        //Check if such game exist
        if(this.gameHashMap.containsKey(this.enterGameID)){
            // Check if enterGameID indicate itself game
            return this.gameHashMap.get(enterGameID).getPlayerHashMap().containsKey(this.accountID);
        }
        return false;
    }
}
