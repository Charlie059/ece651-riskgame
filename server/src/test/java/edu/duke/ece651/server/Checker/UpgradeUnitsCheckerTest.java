package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeUnitsCheckerTest {
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
        Territory t = game.getMap().getTerritoryList().get("a1");
        t.addUnitLevel(1, 3, t.getUnits());
        UpgradeUnitsChecker upgradeUnitsChecker = new UpgradeUnitsChecker(
                        drewAccount,
                        gameHashMap,
                        accountHashMap,
                        t,
                        2,
                        1,
                        10,
                        100,
                        2
                );
        assertEquals(null, upgradeUnitsChecker.doCheck());

        upgradeUnitsChecker = new UpgradeUnitsChecker(
                drewAccount,
                gameHashMap,
                accountHashMap,
                t,
                2,
                1,
                10,
                100,
                1
        );
        assertEquals("UpgradeUnit Error: new level is invalid!", upgradeUnitsChecker.doCheck());


        upgradeUnitsChecker = new UpgradeUnitsChecker(
                drewAccount,
                gameHashMap,
                accountHashMap,
                t,
                2,
                1,
                10,
                0,
                2
        );
        assertEquals("UpgradeUnit Error: does not have enough Tech Resource!", upgradeUnitsChecker.doCheck());

        upgradeUnitsChecker = new UpgradeUnitsChecker(
                drewAccount,
                gameHashMap,
                accountHashMap,
                t,
                2,
                0,
                10,
                0,
                2
        );
        assertEquals("UpgradeUnit Error: unit to upgrade does not exist!", upgradeUnitsChecker.doCheck());
        System.out.println(upgradeUnitsChecker.doCheck());


    }
}