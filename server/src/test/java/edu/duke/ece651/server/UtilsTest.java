package edu.duke.ece651.server;

import edu.duke.ece651.server.IO.MockClient;
import edu.duke.ece651.server.IO.MockServer;
import edu.duke.ece651.server.Wrapper.*;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class UtilsTest {
    @Test
    public void StructureTest() throws IOException {
        //Server.java
        Server server = new Server(12339);
        server.getServersocket();
        //Game.java
        Game game = new Game(2);
        game.setGameOver(true);
        game.getCombatFinished();
        //Player.java
        Player player = new Player(new AccountID("abcde"), new GameID(1), game.getMap());
        player.setCurrentGameID(new GameID(1));
        player.setLose(false);
        player.setWon(false);
        player.setTotalDeployment(5);
        //GameRunnable.java
        game.getPlayerHashMap().put(new AccountID("abcde"),player);
        GameHashMap gameHashMap = new GameHashMap();
        gameHashMap.put(new GameID(1),game);
        AccountHashMap accountHashMap = new AccountHashMap();
        for(Territory territory: game.getMap().getTerritoryList().values()){
            territory.setOwner(new AccountID("abcde"));
        }
        GameRunnable gameRunnable = new GameRunnable(gameHashMap,accountHashMap,new GameID(1));
        gameRunnable.checkWinOrLost(game);
        //GameController.java
        GameController gameController = new GameController();
//        gameController.acceptNewClient();
//        MockClient client = new MockClient(1651, "127.0.0.1");
//        MockServer server1 = new MockServer(1651);
//        Socket clientSocket = server1.acceptClient();
        //CommunicatorRunnable.java
        CommunicatorRunnable communicatorRunnable = new CommunicatorRunnable(null, gameHashMap, accountHashMap, new GameRunnableHashMap());
        //Warpper:
        accountHashMap.size();
        accountHashMap.keySet();
        accountHashMap.containsValue(new Account());
        AttackHashMap attackHashMap = new AttackHashMap();
        attackHashMap.get(new AccountID("abcde"));
        attackHashMap.size();
        attackHashMap.keySet();
        attackHashMap.containsValue(new Account());
        CommittedHashMap committedHashMap = new CommittedHashMap();
        committedHashMap.get(new AccountID("abcde"));
        committedHashMap.size();
        committedHashMap.keySet();
        committedHashMap.containsKey(new AccountID("abcde"));
        gameHashMap.size();
        gameHashMap.keySet();
        gameHashMap.containsValue(game);
        gameHashMap.findOpenGameList();
        PlayerHashMap playerHashMap = new PlayerHashMap();
        playerHashMap.keySet();
        playerHashMap.containsValue(player);

    }
}
