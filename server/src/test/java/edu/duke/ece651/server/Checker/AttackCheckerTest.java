package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Territory;
import edu.duke.ece651.shared.map.Unit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AttackCheckerTest {
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
        ArrayList<ArrayList<Integer>> attackUnits = new ArrayList<>();
        ArrayList< Unit > currFromUnits = new ArrayList<>();
        currFromUnits.add(new Unit().setLevel(0).setValue(0));
        currFromUnits.add(new Unit().setLevel(1).setValue(0));
        currFromUnits.add(new Unit().setLevel(2).setValue(0));
        currFromUnits.add(new Unit().setLevel(3).setValue(0));
        currFromUnits.add(new Unit().setLevel(4).setValue(0));
        currFromUnits.add(new Unit().setLevel(5).setValue(0));
        currFromUnits.add(new Unit().setLevel(6).setValue(0));
        AttackChecker attackChecker = new AttackChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                attackUnits,
                "a1",
                "b3",
                new GameID(1),
                10,
                currFromUnits,
         100
        );
        assertEquals("Attack Error: cannot attack non-adjacent territory!", attackChecker.doCheck());

        attackChecker = new AttackChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                attackUnits,
                "a1",
                "strange",
                new GameID(1),
                10,
                currFromUnits,
                100
        );
        assertEquals(null,attackChecker.doCheck());

        attackChecker = new AttackChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                attackUnits,
                "b2",
                "b1",
                new GameID(1),
                10,
                currFromUnits,
                100
        );
        assertEquals("Attack Error: cannot attack your own territory!", attackChecker.doCheck());

        attackChecker = new AttackChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                attackUnits,
                "a1",
                "b1",
                new GameID(1),
                1000,
                currFromUnits,
                100
        );
        assertEquals("Attack Error: you don't have enough food resource!", attackChecker.doCheck());

        ArrayList<Integer> u = new ArrayList<>();
        u.add(0);
        u.add(1);
        attackUnits.add(u);
        attackChecker = new AttackChecker(
                gameHashMap,
                accountHashMap,
                drewAccount,
                attackUnits,
                "a1",
                "b1",
                new GameID(1),
                10,
                currFromUnits,
                100
        );
        assertEquals("Attack Error: you don't have enough units!", attackChecker.doCheck());
    }
}