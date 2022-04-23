package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.SpyDeployAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Spy;
import edu.duke.ece651.shared.map.Territory;
import edu.duke.ece651.shared.map.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpyDeployCheckerTest {
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
        AccountID ericAccount = new AccountID("Eric");
        Player drew = game.getPlayerHashMap().get(drewAccount);

        Territory t = game.getMap().getTerritoryList().get("a1");

        Spy spy = new Spy(drewAccount);
        Spy spy2 = new Spy(ericAccount);
        Spy spy3 = new Spy(drewAccount);
        t.addSpy(spy2);
        t.addSpy(spy3);
        SpyDeployAction deployAction = new SpyDeployAction("a1", "a1");

        SpyDeployChecker spyDeployChecker = new SpyDeployChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                new GameID(1),
                deployAction
        );
        assertEquals("SpyDeploy Error: territory From does not have enough lv 1 unit", spyDeployChecker.doCheck());

        t.addUnitLevel(1, 3, t.getUnits());
        spyDeployChecker = new SpyDeployChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                new GameID(1),
                deployAction
        );
        assertEquals(null, spyDeployChecker.doCheck());

        deployAction = new SpyDeployAction("strange", "a1");
        spyDeployChecker = new SpyDeployChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                new GameID(1),
                deployAction
        );
        assertEquals("SpyDeploy Error: territory does not exist!", spyDeployChecker.doCheck());


        deployAction = new SpyDeployAction("a1", "a1");
        drew.setTechResource(0);
        spyDeployChecker = new SpyDeployChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                new GameID(1),
                deployAction
        );
        assertEquals("SpyDeploy Error: does not have enough Tech Resource!", spyDeployChecker.doCheck());

        deployAction = new SpyDeployAction("b1", "a1");
        drew.setTechResource(100);
        spyDeployChecker = new SpyDeployChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                new GameID(1),
                deployAction
        );
        assertEquals("SpyDeploy Error: cannot deploy spy on enemy's territory", spyDeployChecker.doCheck());

    }
}