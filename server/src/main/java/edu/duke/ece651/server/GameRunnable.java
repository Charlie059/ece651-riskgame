package edu.duke.ece651.server;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.HashMap;

/**
 * Deal with Combat Resolution
 */
public class GameRunnable implements Runnable {
    // Database
    private HashMap<GameID, Game> gameHashMap;//GameID, Game
    private HashMap<AccountID, Account> accountHashMap;//AccountID Account
    private GameID gameID;

    public GameRunnable(HashMap<GameID, Game> gameHashMap, HashMap<AccountID, Account> accountHashMap, GameID gameID) {
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        this.gameID = gameID;
    }


    /**
     * Check if all player is committed
     *
     * @return flase for at least one of player is not committed
     */
    private Boolean isCommitted() {
        if (this.gameHashMap.get(gameID).getCommittedHashMap().containsValue(false)) return false;
        else return true;
    }

    /**
     * Change isCommitted Hashmap in thisGame to false
     */
    private void changeIsCommitted() {
        Game thisGame = this.gameHashMap.get(gameID);
        for (AccountID key : thisGame.getCommittedHashMap().keySet()) {
            thisGame.getCommittedHashMap().put(key, false);
        }
    }

    private void combatResolution() {

    }

    /**
     * Define the game runnable thread
     */
    @Override
    public void run() {
        Game thisGame = this.gameHashMap.get(gameID);

        //Wait Until All Player Joined
        while (thisGame.getPlayerHashMap().size() < thisGame.getNumOfPlayer()) {
        }


        //Do Game until thisGame is GameOver
        do {
            //Wait until all players are isCommitted
            while (!isCommitted()) {
            }
            //Change isCommited to False
            this.changeIsCommitted();
            //Do Combat Resolution
            this.combatResolution();
            //Change Combat Resolution status finished
            thisGame.setCombatFinished(true);
        } while (!thisGame.getGameOver());

    }
}
