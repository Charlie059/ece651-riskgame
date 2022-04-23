package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.SpyDeployAction;
import edu.duke.ece651.shared.IO.ClientActions.SpyMoveAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Spy;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpyMoveCheckerTest {
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

        SpyMoveAction spyMoveAction = new SpyMoveAction(spy.getSpyUUID(), "a1", "b1");
        SpyMoveChecker spyMoveChecker = new SpyMoveChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                spyMoveAction,
                10,
                100
        );
        assertEquals("SpyMove Error: spy does not exist!", spyMoveChecker.doCheck());

        t.addSpy(spy);
        spyMoveChecker = new SpyMoveChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                spyMoveAction,
                10,
                100
        );
        assertEquals(null, spyMoveChecker.doCheck());

        spyMoveAction = new SpyMoveAction(spy2.getSpyUUID(), "a1", "b1");
        spyMoveChecker = new SpyMoveChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                spyMoveAction,
                10,
                100
        );
        assertEquals("SpyMove Error: cannot move enemy's spy!", spyMoveChecker.doCheck());

        spyMoveAction = new SpyMoveAction(spy.getSpyUUID(), "b1", "b2");
        spyMoveChecker = new SpyMoveChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                spyMoveAction,
                10,
                0
        );
        assertEquals("SpyMove Error: does not have enough Food Resource!", spyMoveChecker.doCheck());

        spyMoveAction = new SpyMoveAction(spy.getSpyUUID(), "STRANGE", "b2");
        spyMoveChecker = new SpyMoveChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                spyMoveAction,
                10,
                100
        );
        assertEquals("SpyMove Error: territory From does not exist!", spyMoveChecker.doCheck());

//        System.out.println(spyMoveChecker.doCheck());


    }
}