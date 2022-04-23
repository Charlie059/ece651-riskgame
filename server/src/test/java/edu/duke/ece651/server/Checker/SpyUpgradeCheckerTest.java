package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.Action;
import edu.duke.ece651.shared.IO.ClientActions.SpyUpgradeAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Spy;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SpyUpgradeCheckerTest {
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

        UUID spyUUID = spy.getSpyUUID();
        SpyUpgradeAction spyUpgradeAction = new SpyUpgradeAction(
            "a1",
            spyUUID,
            1);
        SpyUpgradeChecker spyUpgradeChecker = new SpyUpgradeChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                drew,
                spyUpgradeAction
        );

        assertEquals("SpyUpgrade Error: spy does not exist!", spyUpgradeChecker.doCheck());

        t.addSpy(spy);
        spyUpgradeChecker = new SpyUpgradeChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                drew,
                spyUpgradeAction
        );
        assertEquals("SpyUpgrade Error: cannot upgrade spy to same type!", spyUpgradeChecker.doCheck());

        spyUpgradeAction = new SpyUpgradeAction(
                "a1",
                spyUUID,
                2);
        spyUpgradeChecker = new SpyUpgradeChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                drew,
                spyUpgradeAction
        );
        assertEquals(null, spyUpgradeChecker.doCheck());

        spyUpgradeAction = new SpyUpgradeAction(
                "strange",
                spyUUID,
                2);
        spyUpgradeChecker = new SpyUpgradeChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                drew,
                spyUpgradeAction
        );
        assertEquals("SpyUpgrade Error: Territory does not exist!", spyUpgradeChecker.doCheck());

        spyUpgradeAction = new SpyUpgradeAction(
                "a1",
                spy2.getSpyUUID(),
                2);
        spyUpgradeChecker = new SpyUpgradeChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                drew,
                spyUpgradeAction
        );
        assertEquals("SpyUpgrade Error: cannot upgrade enemy's spy!", spyUpgradeChecker.doCheck());

        spyUpgradeAction = new SpyUpgradeAction(
                "a1",
                spy3.getSpyUUID(),
                2);
        drew.deleteCard(16);
        spyUpgradeChecker = new SpyUpgradeChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                map,
                drew,
                spyUpgradeAction
        );
        assertEquals("SpyUpgrade Error: player does not have any SpyUpgrade Card!", spyUpgradeChecker.doCheck());



    }
}