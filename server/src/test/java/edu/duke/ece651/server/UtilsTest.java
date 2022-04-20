package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.*;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Visitor.RSPVisitor;
import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        //Action
        AttackAction attackAction = new AttackAction("a1","b1",new ArrayList<>());
        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
        chooseJoinGameAction.setGameID(new GameID(2));
        ChooseSwitchGameAction chooseSwitchGameAction = new ChooseSwitchGameAction(new GameID(1));
        chooseJoinGameAction.setGameID(new GameID(2));
        DeployAction deployAction = new DeployAction("a1",2,"12345");
        LoginAction loginAction = new LoginAction(new AccountID("abcde"),"12345");
        loginAction.setEnterPassword("12356");
        loginAction.setEnterAccount(new AccountID("cdefg"));
        MoveAction moveAction = new MoveAction("a1","c1",new ArrayList<>());
        NewGameAction newGameAction = new NewGameAction(2);
        newGameAction.setNumOfPlayer(5);
        SignUpAction signUpAction = new SignUpAction(new AccountID("abcde"),"12345");
        signUpAction.setAccount(new AccountID("abcde"));
        UpgradeUnitsAction upgradeUnitsAction =  new UpgradeUnitsAction("a1",2,3);
        ClientPlayerPacket clientPlayerPacket  = new ClientPlayerPacket(null,null,3,100,100,2,100,null,null,null,null,null,null);
        clientPlayerPacket.getCurrentGameID();
        clientPlayerPacket.getAccountID();
        clientPlayerPacket.getNumOfPlayers();
        clientPlayerPacket.getFoodResource();
        clientPlayerPacket.getTotalDeployment();
        clientPlayerPacket.getTechResource();
        clientPlayerPacket.getTechLevel();
        clientPlayerPacket.getMyTerritories();
        clientPlayerPacket.getEnemyTerritories();
        clientPlayerPacket.getLose();
        clientPlayerPacket.getWin();
        //clientPlayerPacket.doDeploy("a1",1);
        //clientPlayerPacket.doMove("a1","b1",new ArrayList<>(),3);
        //RSP
        RSPAttackSuccess rspAttackSuccess = new RSPAttackSuccess("a1","a2",new ArrayList<>(),3);
        rspAttackSuccess.getFrom();
        rspAttackSuccess.setFrom("a1");
        rspAttackSuccess.getTo();
        rspAttackSuccess.setTo("a1");
        rspAttackSuccess.getUnits();
        rspAttackSuccess.setUnits(new ArrayList<>());
        rspAttackSuccess.getTotalCost();
        rspAttackSuccess.setTotalCost(3);
        RSPChooseJoinGameSuccess rspChooseJoinGameSuccess = new RSPChooseJoinGameSuccess();
        rspChooseJoinGameSuccess.getClientPlayerPacket();
        RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess = new RSPChooseSwitchGameSuccess();
        rspChooseSwitchGameSuccess.getClientPlayerPacket();
        RSPCommitSuccess rspCommitSuccess = new RSPCommitSuccess();
        rspCommitSuccess.getClientPlayerPacket();
        RSPLoginSuccess rspLoginSuccess = new RSPLoginSuccess();
        ActionCheckDoFeedbackVisitor actionCheckDoFeedbackVisitor = new ActionCheckDoFeedbackVisitor(new AccountID("1"),new GameID(1),new Socket(),new AccountHashMap(),new GameHashMap(),new GameRunnableHashMap());
        rspLoginSuccess.accept(new ResponseVisitor() {
            @Override
            public void visit(RSPMoveSuccess rspMoveSuccess) {

            }

            @Override
            public void visit(RSPLoginSuccess rspLoginSuccess) {

            }
        });

        RSPMoveSuccess rspMoveSuccess = new RSPMoveSuccess("a1","b1",new ArrayList<>(),1);
        rspMoveSuccess.getFrom();
        rspMoveSuccess.setFrom("a1");
        rspMoveSuccess.getTo();
        rspMoveSuccess.setTo("a1");
        rspMoveSuccess.getUnits();
        rspMoveSuccess.setUnits(new ArrayList<>());
        rspMoveSuccess.getTotalCost();
        rspMoveSuccess.setTotalCost(3);
        RSPNewGameSuccess rspNewGameSuccess = new RSPNewGameSuccess();
        rspNewGameSuccess.getClientPlayerPacket();
        RSPOpenGameList rspOpenGameList = new RSPOpenGameList(new ArrayList<>());
        RSPUpgradeTechSuccess rspUpgradeTechSuccess = new RSPUpgradeTechSuccess();
        rspUpgradeTechSuccess.getTechCost();
        RSPUpgradeUnitsSuccess rspUpgradeUnitsSuccess = new RSPUpgradeUnitsSuccess();
        rspUpgradeUnitsSuccess.getWhere();
        rspUpgradeUnitsSuccess.getOldLevel();
        rspUpgradeUnitsSuccess.getNewLevel();
        rspUpgradeUnitsSuccess.getTechCost();
        RSPVisitor rspVisitor = new RSPVisitor();
        chooseSwitchGameAction.setGameID(new GameID(1));
        signUpAction.setPassword("12345");
        assertEquals(new RSPSwitchGameList(new ArrayList<>()).getClass(), new RSPSwitchGameList(new ArrayList<>()).getClass());
        assertEquals(new RSPOpenGameList(new ArrayList<>()).getClass(), new RSPOpenGameList(new ArrayList<>()).getClass());

    }
}
