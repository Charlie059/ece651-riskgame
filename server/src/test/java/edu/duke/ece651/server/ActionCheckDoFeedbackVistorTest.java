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
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID,currGameID,clientSocket, accountHashMap,gameHashMap,1);
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
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap,1);
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
    void test_VisitNewGameSuccess() throws IOException, ClassNotFoundException {
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
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap,1);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//
        currAccountID.setAccountID("mnbvc");
        //Signin Action
        NewGameAction newGameAction = new NewGameAction(5);

        mockClient.sendObject(newGameAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPNewGameSuccess().getClass());
        assertEquals(currGameID.getCurrGameID(), 1);


        Map currentMap = gameHashMap.get(new GameID(1)).getMap();
        String hostTerritoryName =currentMap.getGroups().get(0).get(0);
        //assert Map's territory has been set
        assertEquals(currentMap.getTerritoryList().get(hostTerritoryName).getOwnerId(),new AccountID("mnbvc"));
        //assert Player know his territory
        assertEquals(gameHashMap.get(currGameID).getPlayerHashMap().get(currAccountID).getMyTerritories().size(),3);
        mockServer.closeSocket();
    }


    @Test
    void test_VisitJoinChooseGame() throws IOException, ClassNotFoundException, InterruptedException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //host player in Game
        AccountID hostAccountID = new AccountID("abcde");
        Player player = new Player(hostAccountID,new GameID(1),gameHashMap.get(new GameID(1)).getMap());
        gameHashMap.get(new GameID(1)).getPlayerHashMap().put(hostAccountID,player);
        gameHashMap.get(new GameID(1)).setOwnership(hostAccountID);
        //new SocketConnection
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = null;
        AccountID currAccountID = new AccountID("cdefg");
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap,2);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//

        //Add currAccount to AccountHashMap
        Account testAccount1 = new Account();
        testAccount1.setPassword("12345");
        accountHashMap.put(currAccountID, testAccount1);

        //JoinGame Action
        JoinAction joinAction = new JoinAction();
        mockClient.sendObject(joinAction);

        //(2)Recv OpenGameList
        Response trueResponse = (Response) mockClient.recvObject();

        ArrayList<GameID> responseArrayList = new ArrayList<>();
        responseArrayList.add(new GameID(1));
        assertEquals(trueResponse.getClass(), new RSPOpenGameList(responseArrayList).getClass());
        assertEquals(trueResponse, new RSPOpenGameList(responseArrayList));

        //(3)Send ChooseGame Action
        //ChooseGame Action

        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
        mockClient.sendObject(chooseJoinGameAction);
        Response responseChooseGame = (Response) mockClient.recvObject();

        t.join();
        assertEquals(responseChooseGame.getClass(), new RSPChooseJoinGameSuccess().getClass());
        //assert Game has 2 player include me and host
        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().size(), 2);
        //assert Committed is all false
        assertEquals(true,gameHashMap.get(new GameID(1)).getCommittedHashMap().containsKey(new AccountID("cdefg")));
        //assert all is not committed
        assertFalse(gameHashMap.get(new GameID(1)).getCommittedHashMap().containsValue(true));
        //assert have set territories
        Map currentMap = gameHashMap.get(new GameID(1)).getMap();
        String hostTerritoryName =currentMap.getGroups().get(0).get(0);
        assertEquals(currentMap.getTerritoryList().get(hostTerritoryName).getOwnerId(),new AccountID("abcde"));
        String joinedTerritoryName =currentMap.getGroups().get(1).get(0);
        assertEquals(currentMap.getTerritoryList().get(joinedTerritoryName).getOwnerId(),new AccountID("cdefg"));
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
        Player host1 = new Player(currAccountID,currGameID,gameHashMap.get(currGameID).getMap());
        gameHashMap.get(currGameID).getPlayerHashMap().put(currAccountID,host1);

        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap,2);
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
    void test_VisitDeploy() throws IOException, ClassNotFoundException {
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
        Player host = new Player(currAccountID,currGameID,currGame.getMap());
        currGame.getPlayerHashMap().put(currAccountID,host);
        currGame.setOwnership(currAccountID);
        host.assignMyTerritories();
        currGame.getCommittedHashMap().put(currAccountID,false);
        GameRunnable gameRunnable = new GameRunnable(gameHashMap,accountHashMap,currGameID);
        Thread gameThread = new Thread(gameRunnable);
        gameThread.start();

        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap,3);
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
        assertEquals(currGame.getMap().getTerritoryList().get("a1").getOwnerId(),currAccountID);

        //Deploy Fail on Not my territory
        deployAction.setDeployUnits(1).setTo("b1");
        mockClient.sendObject(deployAction);
        Response response1 = (Response) mockClient.recvObject();
        assertEquals(new RSPDeployFail().getClass(),response1.getClass());

        //Deploy Fail on Not enough Deployment
        deployAction.setDeployUnits(40).setTo("a2");
        mockClient.sendObject(deployAction);
        Response response2 = (Response) mockClient.recvObject();
        assertEquals(new RSPDeployFail().getClass(),response2.getClass());
    }

    @Test
    void testVisit8() {
    }

    @Test
    void testVisit9() {
    }

    @Test
    void testVisit10() {
    }

    @Test
    void testVisit11() {
    }

    @Test
    void testVisit12() {
    }
}