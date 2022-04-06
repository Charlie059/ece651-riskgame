package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.ClientActions.AttackAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Deal with Combat Resolution
 */
public class GameRunnable implements Runnable {
    // Database
    private GameHashMap gameHashMap;//GameID, Game
    private volatile AccountHashMap accountHashMap;//AccountID Account
    private GameID gameID;
    private volatile Boolean isCombatResolutionFinished;
    private Game currGame;

    public GameRunnable(GameHashMap gameHashMap, AccountHashMap accountHashMap, GameID gameID) {
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        this.gameID = gameID;
        this.currGame = this.gameHashMap.get(this.gameID);
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
        thisGame.getCommittedHashMap().resetCommittedHashmap();
    }



    /**
     * Define the game runnable thread
     */
    @Override
    public void run() {
        Game thisGame = this.gameHashMap.get(gameID);


        while (thisGame.getPlayerHashMap().size() < thisGame.getNumOfPlayer()) {
        }
        assert (thisGame.getPlayerHashMap().size() == thisGame.getNumOfPlayer());

//        synchronized (this){
        thisGame.setBegin(true);
//            this.notifyAll();
//        }


        //Do Game until thisGame is GameOver
        do {
            //Wait until all players are isCommitted
            while (!isCommitted()) {
            }
            //Change isCommited to False
            this.changeIsCommitted();

            //Do Combat Resolution
            CombatResolution combatResolution = new CombatResolution(this.gameHashMap,this.gameID);
            combatResolution.doCombat(0);//1: attacker wins, -1: defender wins, 0: random
            //Do Upgrade Tech Level
            this.currGame.getPlayerHashMap().updatePlayersTechLevel();
            //Change Combat Resolution status finished
            thisGame.setCombatFinished(true);
        } while (!thisGame.getGameOver());

    }
}
