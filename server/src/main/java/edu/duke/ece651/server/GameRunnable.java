package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Territory;

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
     * check if this game has winner or loser(s)
     *
     * @param thisGame
     */
    //TODO: back to private
    public void checkWinOrLost(Game thisGame) {
        //check win
        boolean findWinner = true;
        Map map = thisGame.getMap();
        AccountID firstAcc = null;
        for (Territory t : map.getTerritoryList().values()) {
            //first iteration: set initial account
            if (firstAcc == null) {
                firstAcc = t.getOwnerId();
                continue;
            }
            if (t.getOwnerId() != firstAcc) {
                findWinner = false;
                break;
            }
        }
//        if (findWinner) {
//            //TODO: set this isWin of this player as true
//            Player winner = thisGame.getPlayerHashMap().getPlayerHashMap().get(firstAcc);
//            winner.setWon(true);
//            thisGame.setGameOver(true);
//        }

        //check lose for each player
        for (AccountID accountID : thisGame.getPlayerHashMap().getPlayerHashMap().keySet()) {
            Player player = thisGame.getPlayerHashMap().getPlayerHashMap().get(accountID);
            //check if the player owns any territory
            if (player.getMyTerritories().isEmpty() || player.getMyTerritories().size() == 0) {
                //TODO: set this isLose of this player as true
                player.setLose(true);
            }
        }
    }

    private void breed() {
        //Each territory add one level 0 unit
        for (Territory key : this.currGame.getMap().getTerritoryList().values()) {
            key.getUnits().get(0).setValue(key.getUnits().get(0).getValue() + 1);
        }

        //Each Food Resource increasing owned territories * cost * 4

        //Each Tech Resource increasing owned territories * cost * 8
        for (Player player : this.currGame.getPlayerHashMap().getPlayerHashMap().values()) {
            Integer foodBreed = 0;
            Integer techBreed = 0;
            for (Territory territory : player.getMyTerritories().values()) {
                foodBreed += territory.getCost() * 4;
                techBreed += territory.getCost() * 8;
            }
            player.setFoodResource(player.getFoodResource() + foodBreed);
            player.setTechResource(player.getTechResource() + techBreed);
        }
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

        int counter = 0;//Loop Counter
        //Do Game until thisGame is GameOver
        do {
            //Wait until all players are isCommitted
            synchronized (this) {
                while (!isCommitted()) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            //Change isCommited to False
            this.changeIsCommitted();

            //Do Combat Resolution
            CombatResolution combatResolution = new CombatResolution(this.gameHashMap, this.gameID);
            combatResolution.doCombat(0);//1: attacker wins, -1: defender wins, 0: random
            //Do Upgrade Tech Level
            this.currGame.getPlayerHashMap().updatePlayersTechLevel();
            //check win or lose-> decide whether to set game over
            checkWinOrLost(thisGame);
            //Breed
            //If first Loop, do not breed
            if (counter != 0) {
                this.breed();
            }
            counter++;
            //Change Combat Resolution status finished
            thisGame.getCountDownLatch().countDown();
            //thisGame.setCombatFinished(true);
        } while (!thisGame.getGameOver());


    }
}
