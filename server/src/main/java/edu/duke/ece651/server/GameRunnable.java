package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Game;
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

    private void combatResolution() {

        //One Attack
            //Game->AttackHashMap:<AccountID, ArrayList<AttackActions>>
                    //AttackActions-> from, to , units
                        //units->ArrayList<ArrayList<Integer>>
                            // [ [0,3], [4,2], [1,2]]
                                // [level,number_of_units_in_this_level]
                    //Defender GameHashMap->Game->Map->TerritoryList->to.getUnits()
                        //Units-> ArrayList<Unit>, Unit->level, value
                            //[(0,0),(1,3),(2,1),(3,0),(4,0),(5,0),(6,0)]//Ascending Order with full level
            //DO SORT
                    //Attack units->
                        // [  [4,2], [1,2], [0,3] ]

                        //Round 1: Defender win | Defender win
                                //[(0,0),(1,3),(2,1),(3,0),(4,0),(5,0),(6,0)]
                                // [ [4,1], [1,2], [0,2] ]
                        //Round 2: Attacker win | Defender win
                                //[(0,0),(1,2),(2,1),(3,0),(4,0),(5,0),(6,0)]
                                // [ [4,1], [1,2], [0,1] ]
                        //Round 3: Defender win | Attacker win
                                //[(0,0),(1,2),(2,0),(3,0),(4,0),(5,0),(6,0)]
                                // [ [4,0], [1,2], [0,1] ]
                        //Round 4: Defender win | Attacker win
                                //[(0,0),(1,1),(2,0),(3,0),(4,0),(5,0),(6,0)]
                                // [ [4,0], [1,1], [0,1] ]
                        //Round 4: Defender win | Attacker win
                                //[(0,0),(1,1),(2,0),(3,0),(4,0),(5,0),(6,0)]
                                // [ [4,0], [1,1], [0,1] ]

        //Do Tech Upgrade
        this.currGame.getPlayerHashMap().updatePlayersTechLevel();
    }

    /**
     * Define the game runnable thread
     */
    @Override
    public void run() {
        Game thisGame = this.gameHashMap.get(gameID);


        while (thisGame.getPlayerHashMap().size() < thisGame.getNumOfPlayer()) {
        }
        assert(thisGame.getPlayerHashMap().size() == thisGame.getNumOfPlayer());

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
            this.combatResolution();
            //Change Combat Resolution status finished
            thisGame.setCombatFinished(true);
        } while (!thisGame.getGameOver());

    }
}
