package edu.duke.ece651.server;

import edu.duke.ece651.server.IO.MockClient;
import edu.duke.ece651.server.IO.MockServer;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.server.Wrapper.GameRunnableHashMap;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.map.Map;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
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
        MockServer mockServer = new MockServer(12310);
        MockClient mockClient = new MockClient(12310, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        GameRunnableHashMap gameRunnableHashMap = new GameRunnableHashMap();
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(clientSocket, gameHashMap, accountHashMap, gameRunnableHashMap,1);
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
        MockServer mockServer = new MockServer(12308);
        MockClient mockClient = new MockClient(12308, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");
        GameRunnableHashMap gameRunnableHashMap = new GameRunnableHashMap();
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(clientSocket, gameHashMap, accountHashMap, gameRunnableHashMap,1);
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
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap,new GameRunnableHashMap(), 2);
        Thread t = new Thread(task);
        t.start();
        ////==============================TESTBENCH==================================//

        //Signup
        //ACTION
        AccountID newAccountID = new AccountID("abcde");
        SignUpAction SignUpAction = new SignUpAction(newAccountID, "12345");
        mockClient.sendObject(SignUpAction);
        Response Response = (Response) mockClient.recvObject();

        //ACTION
        LoginAction loginAction = new LoginAction(new AccountID("abcde"), "12345");
        mockClient.sendObject(loginAction);

        //ASSERT
        Response trueResponse = (Response) mockClient.recvObject();
        t.join();
        assertEquals(trueResponse.getClass(), new RSPLoginSuccess().getClass());
        assertEquals(task.getAccountID().getAccountID(), new AccountID("abcde").getAccountID());
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
        GameRunnableHashMap gameRunnableHashMap = new GameRunnableHashMap();
        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(clientSocket, gameHashMap, accountHashMap, gameRunnableHashMap,1);
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
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap, new GameRunnableHashMap(), 1);
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

//    @Test
//    void test_VisitNewGameJoinChooseSuccess() throws IOException, ClassNotFoundException, InterruptedException {
//        //==============================COMMUNICATOR==================================//
//        //new GameHashMap
////        GameHashMap gameHashMap = this.createGameHashMap();
//        GameHashMap gameHashMap = new GameHashMap();
//        //new AccountHashMap
//        AccountHashMap accountHashMap = this.createAccountHashMap();
//        //new SocketConnection
//        MockServer mockServer = new MockServer(12309);
//        MockClient mockClient1 = new MockClient(12309, "127.0.0.1");
//        MockClient mockClient2 = new MockClient(12309, "127.0.0.1");
//        Socket clientSocket1 = mockServer.acceptClient();
//        Socket clientSocket2 = mockServer.acceptClient();
//        //Host
//        AccountID hostAccountID = new AccountID("abcde");
//        CommunicatorRunnable hostTask = new CommunicatorRunnable(hostAccountID, new GameID(1), clientSocket1, accountHashMap, gameHashMap, new GameRunnableHashMap(), 1);
//        Thread CommunicatorThread1 = new Thread(hostTask);
//        CommunicatorThread1.start();
//        //Joiner
//        AccountID joinerAccountID = new AccountID("cdefg");
//        CommunicatorRunnable joinerTask = new CommunicatorRunnable(joinerAccountID, new GameID(1), clientSocket2, accountHashMap, gameHashMap, new GameRunnableHashMap(), 2);
//        Thread CommunicatorThread2 = new Thread(joinerTask);
//        CommunicatorThread2.start();
//
//        ////==============================TESTBENCH==================================//
//        //New Game Action
//        NewGameAction newGameAction = new NewGameAction(2);
////H1        //Host New Game
//        mockClient1.sendObject(newGameAction);
//
////J1       //Joiner join Game
//        JoinAction joinAction = new JoinAction();
//        mockClient2.sendObject(joinAction);
//        Response responsejoiner1 = (Response) mockClient2.recvObject();
//        ArrayList<GameID> gameIDArrayList = new ArrayList<>();
//        gameIDArrayList.add(new GameID(1));
//        gameIDArrayList.add(new GameID(2));
//        assertSame(new RSPOpenGameList(gameIDArrayList).getClass(), responsejoiner1.getClass());
//        //Join the Choose Game
//        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
////J2
//        mockClient2.sendObject(chooseJoinGameAction);
//        Response responsejoiner2 = (Response) mockClient2.recvObject();
//        //assertEquals(new RSPChooseJoinGameSuccess().getClass(), responsejoiner2.getClass());
//        //========================Once all player joined==========================//
//        //once all player joined
////H1
//        RSPNewGameSuccess Responsehost1 = (RSPNewGameSuccess) mockClient1.recvObject();
//        assertEquals(Responsehost1.getClass(), new RSPNewGameSuccess().getClass());
//        assertEquals(new GameID(1),Responsehost1.getClientPlayerPacket().getCurrentGameID());
//        assertEquals(2,Responsehost1.getClientPlayerPacket().getNumOfPlayers());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).getMyTerritories().getClass(),Responsehost1.getClientPlayerPacket().getMyTerritories().getClass());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).getFoodResource(),Responsehost1.getClientPlayerPacket().getFoodResource());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).getTechResource(),Responsehost1.getClientPlayerPacket().getTechResource());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).getCurrTechLevel(),Responsehost1.getClientPlayerPacket().getTechLevel());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).isLose(),Responsehost1.getClientPlayerPacket().getLose());
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(hostAccountID).isWon(),Responsehost1.getClientPlayerPacket().getWin());
//
//        Map currentMap = gameHashMap.get(new GameID(1)).getMap();
//        String hostTerritoryName = currentMap.getGroups().get(0).get(0);
//        //assert Map's territory has been set
//        assertEquals(currentMap.getTerritoryList().get(hostTerritoryName).getOwnerId(), hostAccountID);
//        //assert Player know his territory
//        CommunicatorThread1.join();
//        CommunicatorThread2.join();
//        assertEquals(gameHashMap.get(new GameID(1)).getPlayerHashMap().get(joinerAccountID).getMyTerritories().size(),3);
//        mockServer.closeSocket();
//    }




    @Test
    void test_VisitSwitchChooseGame() throws IOException, ClassNotFoundException, InterruptedException {

        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12307);
        MockClient mockClient = new MockClient(12307, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        GameID currGameID = new GameID(1);
        AccountID currAccountID = new AccountID("abcde");

        //new Player in currGame
        Player host1 = new Player(currAccountID, currGameID, gameHashMap.get(currGameID).getMap());
        gameHashMap.get(currGameID).getPlayerHashMap().put(currAccountID, host1);

        //new Communicator thread
        CommunicatorRunnable task = new CommunicatorRunnable(currAccountID, currGameID, clientSocket, accountHashMap, gameHashMap, new GameRunnableHashMap(), 2);
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
        //assertEquals(response.getClass(), new RSPSwitchGameList(gameIDArrayList));
        //Before Switch, currGameID is still gameID 1
        assertEquals(task.getGameID().getCurrGameID(), new GameID(1).getCurrGameID());


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

}