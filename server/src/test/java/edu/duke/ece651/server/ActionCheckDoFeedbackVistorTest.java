package edu.duke.ece651.server;

import edu.duke.ece651.server.IO.MockClient;
import edu.duke.ece651.server.IO.MockServer;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.*;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class ActionCheckDoFeedbackVistorTest {


    public class CommunicationRunnable implements Runnable {
        private AccountID accountID;
        private GameID gameID;
        private Socket clientSocket;
        private HashMap<GameID, Game> gameHashMap;
        private HashMap<AccountID, Account> playerHashMap;


        public CommunicationRunnable(AccountID accountID, GameID gameID, Socket clientSocket, HashMap<AccountID, Account> playerHashMap, HashMap<GameID, Game> gameHashMap) throws IOException {
            this.accountID = accountID;
            this.gameID = gameID;
            this.clientSocket = clientSocket;
            this.gameHashMap = gameHashMap;
            this.playerHashMap = playerHashMap;
        }

        /**
         * Receive Client Action Object
         *
         * @return Action Object or Null(to indicate error)
         */
        private Action recvAction() {
            try {
                ObjectStream objectStream = new ObjectStream(this.clientSocket);
                return (Action) objectStream.recvObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void run() {
            //Receive Action
            Action action = this.recvAction();
            //Check Do Feedback action
            action.accept(new ActionCheckDoFeedbackVisitor(this.accountID, this.gameID, this.clientSocket, this.playerHashMap, this.gameHashMap));
        }
    }






    @Test
    void test_visitSignupSuccess() throws IOException, ClassNotFoundException {

        //Communicator Thread:
        AccountID accountID = new AccountID("abcde");
        GameID gameID = new GameID(null);

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<AccountID, Account> playerHashMap = new HashMap<>();
        playerHashMap.put(accountID, testAccount);

        HashMap<GameID, Game> gameHashMap = new HashMap<>();

        //Client Action
        SignUpAction SignUpAction = new SignUpAction();
        SignUpAction.setAccount(new AccountID("cdefg"));
        SignUpAction.setPassword("23456");


        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, gameID, clientSocket, playerHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(SignUpAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPSignupSuccess().getClass());
        assertTrue(playerHashMap.containsKey(new AccountID("cdefg")));
        assertEquals(playerHashMap.get(new AccountID("cdefg")).getPassword(),"23456");
        mockServer.closeSocket();
    }

    @Test
    void test_visitSignupFailed() throws IOException, ClassNotFoundException {

        //Communicator Thread:
        AccountID accountID = new AccountID("abcde");
        GameID gameID = new GameID(null);

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();
        accountHashMap.put(accountID, testAccount);

        HashMap<GameID, Game> gameHashMap = new HashMap<>();

        //Client Action
        SignUpAction SignUpAction = new SignUpAction();
        SignUpAction.setAccount(new AccountID("abcde"));
        SignUpAction.setPassword("12345");

        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, gameID, clientSocket, accountHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(SignUpAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPSignupFail().getClass());
        mockServer.closeSocket();
    }


    @Test
    void test_VisitLoginSuccess() throws IOException, ClassNotFoundException {
        //Communicator Thread:
        AccountID accountID = new AccountID("abcde");
        GameID gameID = new GameID(null);

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();
        accountHashMap.put(accountID, testAccount);

        HashMap<GameID, Game> gameHashMap = new HashMap<>();

        //Signin Action
        LoginAction loginAction = new LoginAction();
        loginAction.setEnterAccount(new AccountID("abcde"));
        loginAction.setEnterPassword("12345");

        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, gameID, clientSocket, accountHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(loginAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPLoginSuccess().getClass());
        assertEquals(accountID,new AccountID("abcde"));
        mockServer.closeSocket();
    }

    @Test
    void test_VisitLoginFail() throws IOException, ClassNotFoundException {
        //Communicator Thread:
        AccountID accountID = new AccountID("abcde");
        GameID gameID = new GameID(null);

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();
        accountHashMap.put(accountID, testAccount);

        HashMap<GameID, Game> gameHashMap = new HashMap<>();

        //Signin Action
        LoginAction loginAction = new LoginAction();
        loginAction.setEnterAccount(new AccountID("abcde"));
        loginAction.setEnterPassword("12346");

        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, gameID, clientSocket, accountHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(loginAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPLoginFail().getClass());
        mockServer.closeSocket();
    }

    @Test
    void test_VisitLogout() throws IOException, ClassNotFoundException {
        //Communicator Thread:
        AccountID accountID = new AccountID("abcde");
        GameID gameID = new GameID(null);

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();
        accountHashMap.put(accountID, testAccount);

        HashMap<GameID, Game> gameHashMap = new HashMap<>();

        accountID.setAccountID("mnbvc");
        //Signin Action
        LogoutAction logoutAction = new LogoutAction();


        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, gameID, clientSocket, accountHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(logoutAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPLogoutSuccess().getClass());
        mockServer.closeSocket();
    }

    @Test
    void test_VisitNewGameSuccess() throws IOException, ClassNotFoundException {
        //Communicator Thread:
        AccountID accountID = new AccountID("abcde");
        GameID gameID = new GameID(1);

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();
        accountID.setAccountID("abcde");
        accountHashMap.put(accountID, testAccount);

        HashMap<GameID, Game> gameHashMap = new HashMap<>();

        accountID.setAccountID("mnbvc");
        //Signin Action
        NewGameAction newGameAction = new NewGameAction();
        newGameAction.setNumOfPlayer(5);


        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, gameID, clientSocket, accountHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(newGameAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPNewGameSuccess().getClass());
        assertEquals(gameID.getCurrGameID(),1);
        mockServer.closeSocket();
    }



    @Test
    void test_VisitJoinSuccess() throws IOException, ClassNotFoundException {
        //Communicator Thread:
        AccountID accountID = new AccountID("abcde");
        GameID gameID = new GameID(null);

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<AccountID, Account> accountHashMap = new HashMap<>();
        accountID.setAccountID("abcde");
        accountHashMap.put(accountID, testAccount);

        HashMap<GameID, Game> gameHashMap = new HashMap<>();
        Game game = new Game(5);
        gameHashMap.put(new GameID(1),game);

        accountID.setAccountID("mnbvc");
        //NewGame Action
        NewGameAction newGameAction = new NewGameAction();
        newGameAction.setNumOfPlayer(5);


        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, gameID, clientSocket, accountHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(newGameAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPNewGameSuccess().getClass());
        mockServer.closeSocket();
    }

    @Test
    void test_VisitJoinFail() {
    }

    @Test
    void testVisit7() {
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