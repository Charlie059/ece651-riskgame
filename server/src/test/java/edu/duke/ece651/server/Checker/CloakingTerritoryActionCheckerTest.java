package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.IO.ClientActions.CloakTerritoryAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloakingTerritoryActionCheckerTest {
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
        CloakTerritoryAction cloakTerritoryAction = new CloakTerritoryAction("a1");
        CloakingTerritoryActionChecker cloakingTerritoryActionChecker = new CloakingTerritoryActionChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                20,
                map,
                drew,
                cloakTerritoryAction
        );
        String errMessage = cloakingTerritoryActionChecker.doCheck();
        assertEquals("ClockingTerritory Error: Tech Level should be larger than 2!", errMessage);

        drew.setCurrTechLevel(3);
        cloakingTerritoryActionChecker = new CloakingTerritoryActionChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                20,
                map,
                drew,
                cloakTerritoryAction
        );
        errMessage = cloakingTerritoryActionChecker.doCheck();
        assertEquals(null, errMessage);

        cloakTerritoryAction = new CloakTerritoryAction("a1");
        map.getTerritoryList().get("a1").setCloak();
        cloakingTerritoryActionChecker = new CloakingTerritoryActionChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                20,
                map,
                drew,
                cloakTerritoryAction
        );
        errMessage = cloakingTerritoryActionChecker.doCheck();
        assertEquals("ClockingTerritory Error: territory has been cloaked!", errMessage);



        cloakTerritoryAction = new CloakTerritoryAction("b1");
        cloakingTerritoryActionChecker = new CloakingTerritoryActionChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                20,
                map,
                drew,
                cloakTerritoryAction
        );
        errMessage = cloakingTerritoryActionChecker.doCheck();
        assertEquals("ClockingTerritory Error: cannot cloak enemy's territory!", errMessage);


        cloakTerritoryAction = new CloakTerritoryAction("strange");
        cloakingTerritoryActionChecker = new CloakingTerritoryActionChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                20,
                map,
                drew,
                cloakTerritoryAction
        );
        errMessage = cloakingTerritoryActionChecker.doCheck();
        assertEquals("ClockingTerritory Error: territory does not exist!", errMessage);

        drew.setTechResource(0);
        cloakingTerritoryActionChecker = new CloakingTerritoryActionChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                20,
                map,
                drew,
                cloakTerritoryAction
        );
        errMessage = cloakingTerritoryActionChecker.doCheck();
        assertEquals("ClockingTerritory Error: does not have enough Tech Resource!", errMessage);

    }
}