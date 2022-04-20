package edu.duke.ece651.client;


import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.client.IO.MockServer;
import edu.duke.ece651.client.Model.*;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;
import org.assertj.core.condition.Join;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelTest {

    @Test
    void validateLogin() throws IOException, ClassNotFoundException {
        MockServer mockServer = new MockServer(1651);
        // Create a new Thread
        Thread t = new Thread(() -> {
            try {
                mockServer.acceptClient();

                //1
                sendRSPLoginSuccess(mockServer);
                sendOtherObject(mockServer);
                Thread.sleep(100);
                //2
                sendRSPSignUpSuccess(mockServer);
                sendOtherObject(mockServer);
                Thread.sleep(100);
                //3
                sendRSPNewGameSuccess(mockServer, true);
                Thread.sleep(100);
                //4
                sendRSPDoUpgradeTech(mockServer);
                Thread.sleep(100);
                //5
                sendOtherObject(mockServer);
                Thread.sleep(100);
                //6
                sendRSPNewGameFailure(mockServer);
                Thread.sleep(100);
                //7
                sendRSPAttackSuccess(mockServer);
                Thread.sleep(100);
                //8
                sendOtherObject(mockServer);
                Thread.sleep(100);
                //9
                sendRSPDoUpgradeUnitSuccess(mockServer);
                Thread.sleep(100);
                //10
                sendRSPDoMoveSuccess(mockServer);
                Thread.sleep(100);
                //11
                sendRSPDoCommitSuccess(mockServer);
                Thread.sleep(100);
                //12
                sendRSPDoDeploySuccess(mockServer);
                Thread.sleep(100);
                //13
                sendRSPSwitchGameSuccess(mockServer);
                Thread.sleep(100);
                //14
                sendRSPJoinGameSuccess(mockServer);
                Thread.sleep(100);
                //15
                sendRSPJoinAction(mockServer);
                Thread.sleep(100);
                //16
                sendOtherObject(mockServer);
                Thread.sleep(100);
                //17
                sendSwitchListAction(mockServer);
                Thread.sleep(100);

                //18 send signupSuccess
                sendRSPSignUpSuccess(mockServer);
                Thread.sleep(100);




            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        // 1 Client send LoginAction and recv RSPLoginSuccess
        LoginModel loginModel = new LoginModel();
        assertEquals(true, loginModel.validateLogin("123","Abcab23@qqa123",false));
        assertEquals(true, loginModel.validateLogin("123","Abcab23@qqa123",true));
        // Client send LoginAction and recv other Response
        loginModel.validateLogin("123","Abcab23@qqa123",false);


        // 2 Client send Signup Action and recv RSPSignupSuccess
        SignupModel signupModel = new SignupModel();
        assertEquals(true, signupModel.signUp("123","Abcab23@qqa123",false));
        signupModel.signUp("123","Abcab23@qqa123",false);
        signupModel.signUp("123","Abcab23@qqa123",true);


        // 3 Client send NewGame Action and recv RSPNewGameSuccess
        GameModel gameModel = GameModel.getInstance();
        assertEquals(true, gameModel.startNewGame("3",true));
        gameModel.startNewGame("3",false);

        // 4 Client send upgradeTech Action and recv RSPupgradeTechSuccess
        assertEquals(true, gameModel.doUpgradeTech(false));
        gameModel.doUpgradeTech(true); // debugmode

        // 5 Client send upgradeTech Action and recv others
        assertEquals(false, gameModel.doUpgradeTech(false));

        // 6 newGame fail
        assertEquals(false, gameModel.startNewGame("3",false));

        // 7 doAttack success
        String attackInfo[] = {"b1","a1","0","1"};
        assertEquals(true, gameModel.doAttack(attackInfo,false));
        gameModel.doAttack(attackInfo,true);
        testGetter(gameModel);

        // 8 doAttack fail
        assertEquals(false, gameModel.doAttack(attackInfo,false));

        // 9 doUpgradeUnit success
        String uogradeUnitInfo[] = {"b1","0","1","1"};
        assertEquals(true, gameModel.doUpgradeUnit(uogradeUnitInfo,false));
        assertEquals(true, gameModel.doUpgradeUnit(uogradeUnitInfo,true));

        // 10 domove success
        String moveInfo[] = {"b1","b2","0","1"};
        assertEquals(true, gameModel.doMove(moveInfo,false));
        assertEquals(true, gameModel.doMove(moveInfo,true));

        // 11 docommit success
        assertEquals(true, gameModel.doCommit(false));
        assertEquals(true, gameModel.doCommit(true));

        // 12 doDeploy success
        assertEquals(true, gameModel.doDeploy("b1",1,false));
        assertEquals(true, gameModel.doDeploy("b1",1,true));

        // 13 doDeploy success
        assertEquals(true, gameModel.switchGame(1,false));
        assertEquals(true, gameModel.switchGame(1,true));

        // 14 joinGame success
        assertEquals(true, gameModel.joinGame(1,false));
        assertEquals(true, gameModel.joinGame(1,true));

        // 15 joinAction
        JoinGameModel joinGameModel = new JoinGameModel();
        assertEquals(2, joinGameModel.getGameLists(false).get(0).getGameID());
        assertEquals(1, joinGameModel.getGameLists(true).get(0).getGameID());

        // 16 joinAction
        assertEquals(null, joinGameModel.getGameLists(false));

        // 17 switchList
        SwitchGameModel switchGameModel = new SwitchGameModel();
        assertEquals(2, switchGameModel.getGameLists(false).get(0).getGameID());
        assertEquals(1, switchGameModel.getGameLists(true).get(0).getGameID());


        // 18 test new signup
        SignupModel signupModel1 = new SignupModel();
        // First time -> send an email
        signupModel1.signUp("pad128g@icloud.com", "abcABC123456@@@ad5518", "", false);
        // Second time -> input code
        signupModel1.signUp("pad128g@icloud.com", "abcABC123456@@@ad5518", "abc123", false);

        mockServer.close();
    }

    private void testGetter(GameModel gameModel) {
        // GetEnemyTerrList and getMyTerrList
        HashSet<String> myTerr = new HashSet<>();
        myTerr.add("b1");
        myTerr.add("b2");
        myTerr.add("b3");
        assertEquals(myTerr, gameModel.getMyTerrList());


        // Get ClientPlayerPacket
        assertEquals(gameModel.getClientPlayerPacket(), gameModel.getClientPlayerPacket());

        // Test get playerID
        assertEquals("abc" , gameModel.getPlayerID());

        // Test get getEnemyTerrList
        HashSet<String> enemyTerrName = new HashSet<>();
        enemyTerrName.add("a1");
        enemyTerrName.add("a2");
        enemyTerrName.add("a3");
        assertEquals(enemyTerrName, gameModel.getEnemyTerrList());

        // Test getFoodRes
        assertEquals(90, gameModel.getFoodRes());

        // Test getTechlevel
        assertEquals(1, gameModel.getTechlevel());

        // Test getTechRes
        assertEquals(80, gameModel.getTechRes());

        // Test getTerrUnits , doUpgradeTech
        gameModel.getTerrUnits("b1", new ArrayList<>());
        gameModel.getTerrUnits("a1", new ArrayList<>());
    }

    private void sendRSPDoUpgradeTech(MockServer mockServer) throws IOException, ClassNotFoundException {
        assertEquals(UpgradeTechAction.class, mockServer.recvObject().getClass());
        RSPUpgradeTechSuccess rspUpgradeTechSuccess = new RSPUpgradeTechSuccess();
        rspUpgradeTechSuccess.setTechCost(10);
        mockServer.sendObject(rspUpgradeTechSuccess);
    }


    private void sendRSPSignUpSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send otherObject
        assertEquals(SignUpAction.class, mockServer.recvObject().getClass());
        mockServer.sendObject(new RSPSignupSuccess());
    }

    private void sendOtherObject(MockServer mockServer) throws IOException, ClassNotFoundException {
        mockServer.recvObject().getClass();
        // Test send otherObject
        mockServer.sendObject(new LoginAction(new AccountID("a"),"12"));
    }

    private void sendRSPLoginSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send RSPLoginSuccess
        assertEquals(LoginAction.class, mockServer.recvObject().getClass());
        mockServer.sendObject(new RSPLoginSuccess());
    }


    private void sendRSPNewGameSuccess(MockServer mockServer, boolean sendPacket) throws IOException, ClassNotFoundException {
        // Test send RSPNewGameSuccess
        assertEquals(NewGameAction.class, mockServer.recvObject().getClass());
        ClientPlayerPacket clientPlayerPacket = GameModel.getInstance().mockData();
        if(sendPacket) mockServer.sendObject(new RSPNewGameSuccess(clientPlayerPacket));
        else mockServer.sendObject(new RSPNewGameSuccess());
    }

    private void sendRSPNewGameFailure(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send RSPNewGameSuccess
        assertEquals(NewGameAction.class, mockServer.recvObject().getClass());
        mockServer.sendObject(new RSPAttackFail());
    }

    private void sendRSPAttackSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send RSPAttackSuccess
        assertEquals(AttackAction.class, mockServer.recvObject().getClass());
        mockServer.sendObject(new RSPAttackSuccess("b1","a1",new ArrayList<ArrayList<Integer>>(),0));
    }

    private void sendRSPDoUpgradeUnitSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send UpgradeUnitsAction
        assertEquals(UpgradeUnitsAction.class, mockServer.recvObject().getClass());
        RSPUpgradeUnitsSuccess rspUpgradeUnitsSuccess = new RSPUpgradeUnitsSuccess();
        rspUpgradeUnitsSuccess.setTechCost(0);
        rspUpgradeUnitsSuccess.setNewLevel(1);
        rspUpgradeUnitsSuccess.setOldLevel(0);
        rspUpgradeUnitsSuccess.setWhere("b1");
        mockServer.sendObject(rspUpgradeUnitsSuccess);
    }


    private void sendRSPDoMoveSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send UpgradeUnitsAction
        assertEquals(MoveAction.class, mockServer.recvObject().getClass());
        RSPMoveSuccess rspMoveSuccess = new RSPMoveSuccess("b1","b2",new ArrayList<ArrayList<Integer>>(),0);
        mockServer.sendObject(rspMoveSuccess);
    }

    private void sendRSPDoCommitSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send CommitAction
        assertEquals(CommitAction.class, mockServer.recvObject().getClass());
        ClientPlayerPacket clientPlayerPacket = GameModel.getInstance().mockData();
        RSPCommitSuccess rspCommitSuccess = new RSPCommitSuccess(clientPlayerPacket);
        mockServer.sendObject(rspCommitSuccess);
    }


    private void sendRSPDoDeploySuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send DoDeploy
        assertEquals(DeployAction.class, mockServer.recvObject().getClass());
        RSPDeploySuccess rspCommitSuccess = new RSPDeploySuccess();
        mockServer.sendObject(rspCommitSuccess);
    }


    private void sendRSPSwitchGameSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send switch
        assertEquals(ChooseSwitchGameAction.class, mockServer.recvObject().getClass());
        ClientPlayerPacket clientPlayerPacket = GameModel.getInstance().mockData();
        RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess = new RSPChooseSwitchGameSuccess(clientPlayerPacket);
        mockServer.sendObject(rspChooseSwitchGameSuccess);
    }

    private void sendRSPJoinGameSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send join
        assertEquals(ChooseJoinGameAction.class, mockServer.recvObject().getClass());
        ClientPlayerPacket clientPlayerPacket = GameModel.getInstance().mockData();
        RSPChooseJoinGameSuccess rspChooseJoinGameSuccess = new RSPChooseJoinGameSuccess(clientPlayerPacket);
        mockServer.sendObject(rspChooseJoinGameSuccess);
    }

    private void sendRSPJoinAction(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send join
        assertEquals(JoinAction.class, mockServer.recvObject().getClass());
        ArrayList<GameID> gameIDArrayList = new ArrayList<>();
        gameIDArrayList.add(new GameID(2));
        RSPOpenGameList rspOpenGameList = new RSPOpenGameList(gameIDArrayList);
        mockServer.sendObject(rspOpenGameList);
    }


    private void sendSwitchListAction(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send switch list
        assertEquals(SwitchGameAction.class, mockServer.recvObject().getClass());
        ArrayList<GameID> gameIDArrayList = new ArrayList<>();
        gameIDArrayList.add(new GameID(2));
        RSPSwitchGameList rspSwitchGameList = new RSPSwitchGameList(gameIDArrayList);
        mockServer.sendObject(rspSwitchGameList);
    }

}