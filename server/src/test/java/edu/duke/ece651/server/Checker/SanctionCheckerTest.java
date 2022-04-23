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

class SanctionCheckerTest {
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

        SanctionChecker sanctionChecker = new SanctionChecker(
                this.gameHashMap,
                this.accountHashMap,
                drewAccount,
                ericAccount,
                drew,
                new GameID(1)
                );

        String errMessage = sanctionChecker.doCheck();
        assertEquals("Sanction Error: player does have any Sanction Card!", errMessage);

        drew.addCard(12);
        sanctionChecker = new SanctionChecker(
                this.gameHashMap,
                this.accountHashMap,
                drewAccount,
                ericAccount,
                drew,
                new GameID(1)
        );

        errMessage = sanctionChecker.doCheck();
        assertEquals(null, errMessage);

        drew.addCard(12);
        AccountID strangeAccount = new AccountID("Strange");
        sanctionChecker = new SanctionChecker(
                this.gameHashMap,
                this.accountHashMap,
                drewAccount,
                strangeAccount,
                drew,
                new GameID(1)
        );

        errMessage = sanctionChecker.doCheck();
        assertEquals("Sanction Error: invalid enemy accountID!", errMessage);
    }
}