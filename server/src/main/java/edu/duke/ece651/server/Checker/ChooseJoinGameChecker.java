package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.server.Game;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

public class ChooseJoinGameChecker extends ActionChecker{
    private GameID userInputGameID;

    public ChooseJoinGameChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID, GameID userInputGameID) {
        super(gameHashMap, accountHashMap, accountID);
        this.userInputGameID = userInputGameID;
    }

    @Override
    public boolean doCheck() {
        // Check if the game exist
        if(this.gameHashMap.containsKey(userInputGameID)){
            // Get the game
            Game game = this.gameHashMap.get(userInputGameID);

            // Check if player num is not full
            if(game.getPlayerHashMap().size()<game.getNumOfPlayer()) return true;
            else return false;
        }
        else return false;
    }
}
