package edu.duke.ece651.server;

import edu.duke.ece651.server.IO.MockClient;
import edu.duke.ece651.server.IO.MockServer;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.map.Map;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ActionCheckDoFeedbackVistorTest {

    /**
     * Create GameController AccountHashMap
     *
     * @return AccountHashMap defalut Account:abcde Password:12345
     */
    private AccountHashMap createAccountHashMap() {
        AccountHashMap accountHashMap = new AccountHashMap();
        Account account = new Account();
        account.setPassword("12345");
        accountHashMap.put(new AccountID("abcde"), account);
        return accountHashMap;
    }

    /**
     * Create GameController GameHashMap
     *
     * @return GameHashMap default GameID:1 numOfPlayer: 5
     */
    private GameHashMap createGameHashMap() {
        Game game = new Game(5);
        GameHashMap gameHashMap = new GameHashMap();
        gameHashMap.put(new GameID(1), game);
        return gameHashMap;
    }


    @Test
    void test_visitSignupSuccess() throws IOException, ClassNotFoundException {
        //==============================COMMUNICATOR==================================//
        // AccountID = null
        // GameID = null
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(clientSocket, gameHashMap, accountHashMap, 1);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//

        //ACTION
        AccountID newAccountID = new AccountID("cdefg");
        SignUpAction SignUpAction = new SignUpAction(newAccountID, "23456");
        //send action
        mockClient.sendObject(SignUpAction);

        //ASSERT
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPSignupSuccess().getClass());
        assertTrue(accountHashMap.containsKey(new AccountID("cdefg")));
        assertEquals(accountHashMap.get(new AccountID("cdefg")).getPassword(), "23456");
        mockServer.closeSocket();
    }

    @Test
    void test_visitSignupFailed() throws IOException, ClassNotFoundException {

        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(clientSocket, gameHashMap, accountHashMap, 1);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//

        //ACTION
        SignUpAction SignUpAction = new SignUpAction(new AccountID("abcde"), "12345");
        mockClient.sendObject(SignUpAction);

        //ASSERT
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPSignupFail().getClass());
        mockServer.closeSocket();
    }


    @Test
    void test_VisitLoginSuccess() throws IOException, ClassNotFoundException, InterruptedException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(4444);
        MockClient mockClient = new MockClient(4444, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap, 1);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//
        //ACTION
        LoginAction loginAction = new LoginAction(new AccountID("abcde"), "12345");
        mockClient.sendObject(loginAction);

        //ASSERT
        Response trueResponse = (Response) mockClient.recvObject();
        t.join();
        assertEquals(trueResponse.getClass(), new RSPLoginSuccess().getClass());
        assertEquals(task.getAccountID(), new AccountID("abcde"));
        mockServer.closeSocket();
    }

    @Test
    void test_VisitLoginFail() throws IOException, ClassNotFoundException {

        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(4444);
        MockClient mockClient = new MockClient(4444, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(clientSocket, gameHashMap, accountHashMap, 1);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//
        //Signin Action
        LoginAction loginAction = new LoginAction(new AccountID("abcde"), "12346");
        mockClient.sendObject(loginAction);
        Response trueResponse = (Response) mockClient.recvObject();
        //ASSERT
        assertEquals(trueResponse.getClass(), new RSPLoginFail().getClass());
        mockServer.closeSocket();
    }

    @Test
    void test_VisitLogout() throws IOException, ClassNotFoundException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(4567);
        MockClient mockClient = new MockClient(4567, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap, 1);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//
        currAccountID.setAccountID("mnbvc");
        //Signin Action
        LogoutAction logoutAction = new LogoutAction();

        mockClient.sendObject(logoutAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPLogoutSuccess().getClass());
        mockServer.closeSocket();
    }

    @Test
    void test_VisitNewGameJoinChooseSuccess() throws IOException, ClassNotFoundException, InterruptedException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
//        GameHashMap gameHashMap = this.createGameHashMap();
        GameHashMap gameHashMap = new GameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient1 = new MockClient(12345, "127.0.0.1");
        MockClient mockClient2 = new MockClient(12345, "127.0.0.1");
        Socket clientSocket1 = mockServer.acceptClient();
        Socket clientSocket2 = mockServer.acceptClient();
        //Host
        AccountID hostAccountID = new AccountID("abcde");
        CommunicatorRunnable hostTask = new CommunicatorRunnable(hostAccountID, null, clientSocket1, accountHashMap, gameHashMap, 1);
        Thread CommunicatorThread1 = new Thread(hostTask);
        CommunicatorThread1.start();
        //Joiner
        AccountID joinerAccountID = new AccountID("cdefg");
        CommunicatorRunnable joinerTask = new CommunicatorRunnable(joinerAccountID, null, clientSocket2, accountHashMap, gameHashMap, 2);
        Thread CommunicatorThread2 = new Thread(joinerTask);
        CommunicatorThread2.start();

        ////==============================TESTBENCH==================================//
        //New Game Action
        NewGameAction newGameAction = new NewGameAction(2);
//H1        //Host New Game
        mockClient1.sendObject(newGameAction);

//J1       //Joiner join Game
        JoinAction joinAction = new JoinAction();
        mockClient2.sendObject(joinAction);
        Response responsejoiner1 = (Response) mockClient2.recvObject();
        ArrayList<GameID> gameIDArrayList = new ArrayList<>();
        gameIDArrayList.add(new GameID(1));
        gameIDArrayList.add(new GameID(2));
        assertSame(new RSPOpenGameList(gameIDArrayList).getClass(), responsejoiner1.getClass());
        //Join the Choose Game
        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
//J2
        mockClient2.sendObject(chooseJoinGameAction);
        Response responsejoiner2 = (Response) mockClient2.recvObject();
        //assertEquals(new RSPChooseJoinGameSuccess().getClass(), responsejoiner2.getClass());
        //========================Once all player joined==========================//
        //once all player joined
//H1
        RSPNewGameSuccess Responsehost1 = (RSPNewGameSuccess) mockClient1.recvObject();
        assertEquals(Responsehost1.getClass(), new RSPNewGameSuccess().getClass());
        assertEquals(new GameID(1),Responsehost1.getClientPlayerPacket().getCurrentGameID());
        assertEquals(2,Responsehost1.getClientPlayerPacket().getNumOfPlayers());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).getMyTerritories().getClass(),Responsehost1.getMyTerritories().getClass());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).getFoodResource(),Responsehost1.getFoodResource());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).getTechResource(),Responsehost1.getTechResource());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).getCurrTechLevel(),Responsehost1.getCurrTechLevel());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).isLose(),Responsehost1.isLose());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).isWon(),Responsehost1.isWon());

        Map currentMap = gameHashMap.get(new GameID(1)).getMap();
        String hostTerritoryName = currentMap.getGroups().get(0).get(0);
        //assert Map's territory has been set
        assertEquals(currentMap.getTerritoryList().get(hostTerritoryName).getOwnerId(), hostAccountID);
        //assert Player know his territory
        CommunicatorThread1.join();
        CommunicatorThread2.join();
        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(joinerAccountID).getMyTerritories().size(),3);
        mockServer.closeSocket();
    }




    @Test
    void test_VisitSwitchChooseGame() throws IOException, ClassNotFoundException, InterruptedException {

        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");

        //new Player in currGame
        Player host1 = new Player(currAccountID, currGameID, gameHashMap.get(currGameID).getMap());
        gameHashMap.get(currGameID).getPlayerHashMap().put(currAccountID, host1);

        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap, 2);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//


        //Create New Game 2
        Game game2 = new Game(5);
        game2.getPlayerHashMap().put(currAccountID, host1);
        gameHashMap.put(new GameID(2), game2);
        GameID switchToGameID = new GameID(2);


        // Client Send Switch Game Action
        SwitchGameAction switchGameAction = new SwitchGameAction();
        mockClient.sendObject(switchGameAction);

        // Client Receive ArrayList
        Response response = (Response) mockClient.recvObject();
        // Validate Response ArrayList
        ArrayList<GameID> gameIDArrayList = new ArrayList<>();
        gameIDArrayList.add(new GameID(1));
        gameIDArrayList.add(new GameID(2));
        assertEquals(response.getClass(), RSPSwitchGameList.class);
        assertEquals(response, new RSPSwitchGameList(gameIDArrayList));
        //Before Switch, currGameID is still gameID 1
        assertEquals(task.getGameID(), new GameID(1));


        //Client Send ChooseSwitchGameAction to CHoose GameID(2)
        ChooseSwitchGameAction chooseSwitchGameAction = new ChooseSwitchGameAction(switchToGameID);
        mockClient.sendObject(chooseSwitchGameAction);//chooseSwitchGameAction);

        //Client Recv Success
        Response response2 = (Response) mockClient.recvObject();
        assertEquals(RSPChooseSwitchGameSuccess.class, response2.getClass());
        //Validate Now GameID is Game 2
        //Wait until Second Loop CommunicationRunnation is finished and has changed the GameID of this Runnable
        t.join();
        assertEquals(chooseSwitchGameAction.getGameID(), switchToGameID);
        mockServer.closeSocket();
    }

    @Test
    void test_VisitCommit_VisitDeploy() throws IOException, ClassNotFoundException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");

        //new Game Start with host
        Game currGame = gameHashMap.get(currGameID);
        Player host = new Player(currAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(currAccountID, host);
        currGame.setOwnership(currAccountID);
        host.assignMyTerritories();
        currGame.getCommittedHashMap().put(currAccountID, false);
        GameRunnable gameRunnable = new GameRunnable(gameHashMap, accountHashMap, currGameID);
        Thread gameThread = new Thread(gameRunnable);
        gameThread.start();

        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap, 6);
        Thread CommunicatorThread = new Thread(task);
        CommunicatorThread.start();
        ////==============================TESTBENCH==================================//

        //DeploySuccess
        DeployAction deployAction = new DeployAction();
        deployAction.setDeployUnits(1).setTo("a1");
        mockClient.sendObject(deployAction);
        Response response = (Response) mockClient.recvObject();
        //ASSERT
        assertEquals(response.getClass(), new RSPDeploySuccess().getClass());
        assertEquals(currGame.getMap().getTerritoryList().get("a1").getOwnerId(), currAccountID);

        //Deploy Fail on Not my territory
        deployAction.setDeployUnits(1).setTo("b1");
        mockClient.sendObject(deployAction);
        Response response1 = (Response) mockClient.recvObject();
        assertEquals(new RSPDeployFail().getClass(), response1.getClass());

        //Deploy Fail on Not enough Deployment
        deployAction.setDeployUnits(40).setTo("a2");
        mockClient.sendObject(deployAction);
        Response response2 = (Response) mockClient.recvObject();
        assertEquals(new RSPDeployFail().getClass(), response2.getClass());
        mockServer.closeSocket();

        //Commit Fail on Not finished Deployment
        CommitAction commitAction = new CommitAction();
        mockClient.sendObject(commitAction);
        Response response3 = (Response) mockClient.recvObject();
        assertEquals(new RSPCommitFail().getClass(), response3.getClass());

        //Deploy Finish
        deployAction.setDeployUnits(14).setTo("a1");
        mockClient.sendObject(deployAction);
        Response response4 = (Response) mockClient.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response4.getClass());

        //Commit Success on fininshed Deployment
        mockClient.sendObject(commitAction);
        Response response5 = (Response) mockClient.recvObject();
        assertEquals(new RSPCommitFail().getClass(), response3.getClass());
        mockServer.closeSocket();
    }

    @Test
    void test_VisitCommit_VisitUpgradeTechLevel() throws IOException, ClassNotFoundException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient1 = new MockClient(12345, "127.0.0.1");
        MockClient mockClient2 = new MockClient(12345, "127.0.0.1");
        Socket clientSocket1 = mockServer.acceptClient();
        Socket clientSocket2 = mockServer.acceptClient();
        GameID currGameID = new GameID(2);


        //new Game numOfPlayer = 2 for testing,
        AccountID hostAccountID = new AccountID("abcde");
        AccountID joinerAccountID = new AccountID("cdefg");
        //Host
        Game currGame = new Game(2);
        gameHashMap.put(new GameID(2), currGame);
        Player host = new Player(hostAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(hostAccountID, host);
        currGame.setOwnership(hostAccountID);
        host.assignMyTerritories();
        currGame.getCommittedHashMap().put(hostAccountID, false);
        GameRunnable gameRunnable = new GameRunnable(gameHashMap, accountHashMap, currGameID);
        Thread gameThread = new Thread(gameRunnable);
        gameThread.start();
        CommunicatorRunnable task1 = new CommunicatorRunnable(hostAccountID, currGameID, clientSocket1, accountHashMap, gameHashMap, 5);
        Thread CommunicatorThread1 = new Thread(task1);
        CommunicatorThread1.start();
        //Player joined
        Player joiner = new Player(joinerAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(joinerAccountID, joiner);
        currGame.setOwnership(joinerAccountID);
        joiner.assignMyTerritories();
        CommunicatorRunnable task2 = new CommunicatorRunnable(joinerAccountID, currGameID, clientSocket2, accountHashMap, gameHashMap, 5);
        Thread CommunicatorThread2 = new Thread(task2);
        CommunicatorThread2.start();

        ////==============================TESTBENCH==================================//

        //---------------deploy----------------/
        //player1
        DeployAction deployAction = new DeployAction();
        deployAction.setTo("a1").setDeployUnits(6);
        mockClient1.sendObject(deployAction);
        Response response1_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response1_1.getClass());
        //player2
        deployAction.setTo("b1").setDeployUnits(6);
        mockClient2.sendObject(deployAction);
        Response response1_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response1_2.getClass());
        //----------------commit----------------/
        CommitAction commitAction = new CommitAction();
        mockClient1.sendObject(commitAction);
        Response response2_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response2_1.getClass());

        mockClient2.sendObject(commitAction);
        Response response2_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response2_2.getClass());

        //------------TechUpgrade------------/
        //Start Resource 100
        //level 1 -> level 2 cost = 50
        //Upgrade to level 1 success
        UpgradeTechAction upgradeTechAction = new UpgradeTechAction();
        mockClient1.sendObject(upgradeTechAction);
        Response response3_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPUpgradeTechSuccess().getClass(), response3_1.getClass());

        //Update Fail because has done one
        mockClient1.sendObject(upgradeTechAction);
        Response response3_2 = (Response) mockClient1.recvObject();
        assertEquals(new RSPUpgradeTechFail().getClass(), response3_2.getClass());

        //----------CurrLevel unchanged, Tech Resources decrease------/
        //Host Upgrade 1 level
        //Host Tech Resource decrease
        //Host currLevel remain unchanged
        //Joiner didn't upgrade
        assertEquals(1, host.getCurrTechLevel());
        assertEquals(1, joiner.getCurrTechLevel());
        assertEquals(50, host.getTechResource());
        assertEquals(100, joiner.getTechResource());

        //----------Commit, CurrentLevel Changed------/
        //host commit
        mockClient1.sendObject(commitAction);
        Response response4_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response4_1.getClass());
        //joiner commit
        mockClient2.sendObject(commitAction);
        Response response4_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response4_2.getClass());
        mockServer.closeSocket();
        //Current Tech level of host changed
        assertEquals(2, host.getCurrTechLevel());
        mockServer.closeSocket();
    }


    @Test
    void test_VisitUpgradeUnit() throws IOException, ClassNotFoundException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient1 = new MockClient(12345, "127.0.0.1");
        MockClient mockClient2 = new MockClient(12345, "127.0.0.1");
        Socket clientSocket1 = mockServer.acceptClient();
        Socket clientSocket2 = mockServer.acceptClient();
        GameID currGameID = new GameID(2);


        //new Game numOfPlayer = 2 for testing,
        AccountID hostAccountID = new AccountID("abcde");
        AccountID joinerAccountID = new AccountID("cdefg");
        //Host
        Game currGame = new Game(2);
        gameHashMap.put(new GameID(2), currGame);
        Player host = new Player(hostAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(hostAccountID, host);
        currGame.setOwnership(hostAccountID);
        host.assignMyTerritories();
        currGame.getCommittedHashMap().put(hostAccountID, false);
        GameRunnable gameRunnable = new GameRunnable(gameHashMap, accountHashMap, currGameID);
        Thread gameThread = new Thread(gameRunnable);
        gameThread.start();
        CommunicatorRunnable task1 = new CommunicatorRunnable(hostAccountID, currGameID, clientSocket1, accountHashMap, gameHashMap, 5);
        Thread CommunicatorThread1 = new Thread(task1);
        CommunicatorThread1.start();
        //Player joined
        Player joiner = new Player(joinerAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(joinerAccountID, joiner);
        currGame.setOwnership(joinerAccountID);
        joiner.assignMyTerritories();
        CommunicatorRunnable task2 = new CommunicatorRunnable(joinerAccountID, currGameID, clientSocket2, accountHashMap, gameHashMap, 5);
        Thread CommunicatorThread2 = new Thread(task2);
        CommunicatorThread2.start();

        ////==============================TESTBENCH==================================//
        //---------------deploy----------------/
        //player1
        DeployAction deployAction = new DeployAction();
        deployAction.setTo("a1").setDeployUnits(6);
        mockClient1.sendObject(deployAction);
        Response response1_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response1_1.getClass());
        //player2
        deployAction.setTo("b1").setDeployUnits(6);
        mockClient2.sendObject(deployAction);
        Response response1_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response1_2.getClass());
        //----------------commit----------------/
        CommitAction commitAction = new CommitAction();
        mockClient1.sendObject(commitAction);
        Response response2_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response2_1.getClass());

        mockClient2.sendObject(commitAction);
        Response response2_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response2_2.getClass());

        //---------------Unit Upgrade--------------/
        UpgradeUnitsAction hostUpgradeUnitsAction = new UpgradeUnitsAction();
        hostUpgradeUnitsAction.setOldLevel(0).setNewLevel(1).setWhere("a1");
        UpgradeUnitsAction joinerUpgradeUnitsAction = new UpgradeUnitsAction();
        joinerUpgradeUnitsAction.setOldLevel(0).setNewLevel(1).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse1 = (Response) mockClient1.recvObject();
        Response joinerResponse1 = (Response) mockClient2.recvObject();
        //Assert upgrade success
        assertEquals(new RSPUpgradeUnitsSuccess().getClass(), hostResponse1.getClass());
        assertEquals(new RSPUpgradeUnitsSuccess().getClass(), joinerResponse1.getClass());
        host.setCurrTechLevel(6);
        joiner.setCurrTechLevel(6);

        //---------------Unit Upgrade do not have enough unit--------------/
        hostUpgradeUnitsAction.setOldLevel(2).setNewLevel(3).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(2).setNewLevel(3).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse2 = (Response) mockClient1.recvObject();
        Response joinerResponse2 = (Response) mockClient2.recvObject();
        //Assert upgrade fail
        assertEquals(new RSPUpgradeUnitsFail().getClass(), hostResponse2.getClass());
        assertEquals(new RSPUpgradeUnitsFail().getClass(), joinerResponse2.getClass());

        //---------------Unit Upgrade do not have enough resource--------------/
        hostUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse3 = (Response) mockClient1.recvObject();
        Response joinerResponse3 = (Response) mockClient2.recvObject();
        //Assert upgrade fail
        assertEquals(new RSPUpgradeUnitsFail().getClass(), hostResponse3.getClass());
        assertEquals(new RSPUpgradeUnitsFail().getClass(), joinerResponse3.getClass());
        mockServer.closeSocket();
    }

    @Test
    void testVisitMove() throws IOException, ClassNotFoundException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient1 = new MockClient(12345, "127.0.0.1");
        MockClient mockClient2 = new MockClient(12345, "127.0.0.1");
        Socket clientSocket1 = mockServer.acceptClient();
        Socket clientSocket2 = mockServer.acceptClient();
        GameID currGameID = new GameID(2);


        //new Game numOfPlayer = 2 for testing,
        AccountID hostAccountID = new AccountID("abcde");
        AccountID joinerAccountID = new AccountID("cdefg");
        //Host
        Game currGame = new Game(2);
        gameHashMap.put(new GameID(2), currGame);
        Player host = new Player(hostAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(hostAccountID, host);
        currGame.setOwnership(hostAccountID);
        host.assignMyTerritories();
        currGame.getCommittedHashMap().put(hostAccountID, false);
        GameRunnable gameRunnable = new GameRunnable(gameHashMap, accountHashMap, currGameID);
        Thread gameThread = new Thread(gameRunnable);
        gameThread.start();
        CommunicatorRunnable task1 = new CommunicatorRunnable(hostAccountID, currGameID, clientSocket1, accountHashMap, gameHashMap, 4);
        Thread CommunicatorThread1 = new Thread(task1);
        CommunicatorThread1.start();
        //Player joined
        Player joiner = new Player(joinerAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(joinerAccountID, joiner);
        currGame.setOwnership(joinerAccountID);
        joiner.assignMyTerritories();
        CommunicatorRunnable task2 = new CommunicatorRunnable(joinerAccountID, currGameID, clientSocket2, accountHashMap, gameHashMap, 4);
        Thread CommunicatorThread2 = new Thread(task2);
        CommunicatorThread2.start();

        ////==============================TESTBENCH==================================//
        //---------------deploy----------------/
        //player1
        DeployAction deployAction = new DeployAction();
        deployAction.setTo("a1").setDeployUnits(6);
        mockClient1.sendObject(deployAction);
        Response response1_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response1_1.getClass());
        //player2
        deployAction.setTo("b1").setDeployUnits(6);
        mockClient2.sendObject(deployAction);
        Response response1_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response1_2.getClass());
        //----------------commit----------------/
        CommitAction commitAction = new CommitAction();
        mockClient1.sendObject(commitAction);
        Response response2_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response2_1.getClass());

        mockClient2.sendObject(commitAction);
        Response response2_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response2_2.getClass());
        //----------------move-------------------/
        ArrayList<Integer> level0Move = new ArrayList<>();
        level0Move.add(0);//level
        level0Move.add(2);//num
        ArrayList<ArrayList<Integer>> units = new ArrayList<>();
        units.add(level0Move);
        MoveAction moveAction = new MoveAction();
        moveAction.setFrom("a1").setTo("a2").setUnits(units);
        mockClient1.sendObject(moveAction);
        moveAction.setFrom("b1").setTo("b2").setUnits(units);
        mockClient2.sendObject(moveAction);
        Response hostResponse = (Response) mockClient1.recvObject();
        Response joinerResponse = (Response) mockClient2.recvObject();
        //assert move success
        assertEquals(new RSPMoveSuccess("a1", "a2", units,3).getClass(), hostResponse.getClass());
        assertEquals(new RSPMoveSuccess("b1", "b2", units,3).getClass(), joinerResponse.getClass());
        assertEquals(4, host.getMyTerritories().get("a1").getUnits().get(0).getValue());
        assertEquals(2, host.getMyTerritories().get("a2").getUnits().get(0).getValue());
        //assert move fail
        //--------------------move-------------------/
        mockServer.closeSocket();
        mockClient1.sendObject(moveAction);
        mockClient2.sendObject(moveAction);
        Response hostResponse1 = (Response) mockClient1.recvObject();
        Response joinerResponse1 = (Response) mockClient2.recvObject();
        assertEquals(new RSPMoveFail().getClass(),hostResponse1.getClass());
        mockServer.closeSocket();
    }



    @Test
    void testVisitAttack() throws IOException, ClassNotFoundException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient1 = new MockClient(12345, "127.0.0.1");
        MockClient mockClient2 = new MockClient(12345, "127.0.0.1");
        Socket clientSocket1 = mockServer.acceptClient();
        Socket clientSocket2 = mockServer.acceptClient();
        GameID currGameID = new GameID(2);


        //new Game numOfPlayer = 2 for testing,
        AccountID hostAccountID = new AccountID("abcde");
        AccountID joinerAccountID = new AccountID("cdefg");
        //Host
        Game currGame = new Game(2);
        gameHashMap.put(new GameID(2), currGame);
        Player host = new Player(hostAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(hostAccountID, host);
        currGame.setOwnership(hostAccountID);
        host.assignMyTerritories();
        currGame.getCommittedHashMap().put(hostAccountID, false);
        GameRunnable gameRunnable = new GameRunnable(gameHashMap, accountHashMap, currGameID);
        Thread gameThread = new Thread(gameRunnable);
        gameThread.start();
        CommunicatorRunnable task1 = new CommunicatorRunnable(hostAccountID, currGameID, clientSocket1, accountHashMap, gameHashMap, 4);
        Thread CommunicatorThread1 = new Thread(task1);
        CommunicatorThread1.start();
        //Player joined
        Player joiner = new Player(joinerAccountID, currGameID, currGame.getMap());
        currGame.getPlayerHashMap().put(joinerAccountID, joiner);
        currGame.setOwnership(joinerAccountID);
        joiner.assignMyTerritories();
        CommunicatorRunnable task2 = new CommunicatorRunnable(joinerAccountID, currGameID, clientSocket2, accountHashMap, gameHashMap, 4);
        Thread CommunicatorThread2 = new Thread(task2);
        CommunicatorThread2.start();

        ////==============================TESTBENCH==================================//
        //---------------deploy----------------/
        //player1
        DeployAction deployAction = new DeployAction();
        deployAction.setTo("a1").setDeployUnits(6);
        mockClient1.sendObject(deployAction);
        Response response1_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response1_1.getClass());
        //player2
        deployAction.setTo("b1").setDeployUnits(6);
        mockClient2.sendObject(deployAction);
        Response response1_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response1_2.getClass());
        //----------------commit----------------/
        CommitAction commitAction = new CommitAction();
        mockClient1.sendObject(commitAction);
        Response response2_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response2_1.getClass());

        mockClient2.sendObject(commitAction);
        Response response2_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response2_2.getClass());
        //----------------attack----------------/
        AttackAction attackAction = new AttackAction();
        ArrayList<Integer> level0attack = new ArrayList<>();
        level0attack.add(0);//level
        level0attack.add(2);//num
        ArrayList<ArrayList<Integer>> units = new ArrayList<>();
        units.add(level0attack);
        attackAction.setTo("b1").setFrom("a1").setUnits(units);

        mockClient1.sendObject(attackAction);
        Response response = (Response) mockClient1.recvObject();
        assertEquals(new RSPAttackSuccess("a1","b1",units,3).getClass(),response.getClass());
        //----------------attack Fail--------------/
        ArrayList<Integer> level0attack1 = new ArrayList<>();
        level0attack1.add(0);//level
        level0attack1.add(10);//num
        ArrayList<ArrayList<Integer>> units1 = new ArrayList<>();
        units1.add(level0attack1);
        attackAction.setTo("b1").setFrom("a1").setUnits(units1);
        mockClient1.sendObject(attackAction);
        Response response1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPAttackFail().getClass(),response1.getClass());
        mockServer.closeSocket();
    }

    @Test
    void testVisit() {
    }
}