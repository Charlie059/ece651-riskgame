package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.UnitDeployAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitDeployCheckerTest {
    //Game.java
    Game game = new Game(2);
    GameHashMap gameHashMap = new GameHashMap();
    AccountHashMap accountHashMap = new AccountHashMap();

    private void createGame() {
        game.setGameOver(true);
        game.getCombatFinished();
        Player drew = new Player(new AccountID("Drew"), new GameID(1), game.getMap());
        Player eric = new Player(new AccountID("Eric"), new GameID(1), game.getMap());
        game.getPlayerHashMap().put(new AccountID("Drew"),drew);
        game.getPlayerHashMap().put(new AccountID("Eric"),eric);
        gameHashMap.put(new GameID(1),game);
        //set Territory
        for(Territory territory: game.getMap().getTerritoryList().values()){
            territory.setOwner(new AccountID("Drew"));
        }
        game.getMap().getTerritoryList().get("b1").setOwner(new AccountID("Eric"));
        game.getMap().getTerritoryList().get("b2").setOwner(new AccountID("Eric"));
        game.getMap().getTerritoryList().get("b3").setOwner(new AccountID("Eric"));
    }

    @Test
    void doCheck() {
        createGame();
        Map map = game.getMap();
        AccountID drewAccount = new AccountID("Drew");
        Player drew = game.getPlayerHashMap().get(drewAccount);
        GameID gameID = new GameID(1);
        UnitDeployAction unitDeployAction = new UnitDeployAction("a1");

        UnitDeployChecker unitDeployChecker = new UnitDeployChecker(
                this.gameHashMap,
                this.accountHashMap,
                drewAccount,
                gameID,
                unitDeployAction
                );
        String errMessage = unitDeployChecker.doCheck();
        assertEquals("UnitDeploy Error: player does have any UnitDeploy Card!", errMessage);

        drew.addCard(17);
        unitDeployChecker = new UnitDeployChecker(
                this.gameHashMap,
                this.accountHashMap,
                drewAccount,
                gameID,
                unitDeployAction
        );
        errMessage = unitDeployChecker.doCheck();
        assertEquals(null, errMessage);

        drew.addCard(17);
        unitDeployAction = new UnitDeployAction("strange");
        unitDeployChecker = new UnitDeployChecker(
                this.gameHashMap,
                this.accountHashMap,
                drewAccount,
                gameID,
                unitDeployAction
        );
        errMessage = unitDeployChecker.doCheck();
        assertEquals("UnitDeploy Error: Territory is not your Territory!", errMessage);
    }
}