package edu.duke.ece651.server;

public class GameRunnable implements Runnable{
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
