package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.*;
import edu.duke.ece651.shared.IO.ClientActions.Action;
import edu.duke.ece651.shared.IO.ClientActions.SignUpAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSignupFail;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSignupSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.CurrGameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class ActionCheckDoFeedbackVistorTest {


    public class CommunicationRunnable implements Runnable {
        private AccountID accountID;
        private CurrGameID currGameID;
        private Socket clientSocket;
        private HashMap<Integer, Game> gameHashMap;
        private HashMap<String, Account> playerHashMap;


        public CommunicationRunnable(AccountID accountID, CurrGameID currGameID, Socket clientSocket, HashMap<String, Account> playerHashMap, HashMap<Integer, Game> gameHashMap) throws IOException {
            this.accountID = accountID;
            this.currGameID = currGameID;
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
            action.accept(new ActionCheckDoFeedbackVisitor(this.accountID, this.currGameID, this.clientSocket, this.playerHashMap, this.gameHashMap));
        }
    }






    @Test
    void test_visitSignupSuccess() throws IOException, ClassNotFoundException {

        //Communicator Thread:
        AccountID accountID = new AccountID();
        CurrGameID currGameID = new CurrGameID();

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<String, Account> playerHashMap = new HashMap<>();
        playerHashMap.put("abcde", testAccount);

        HashMap<Integer, Game> gameHashMap = new HashMap<>();

        //Client Action
        SignUpAction SignUpAction = new SignUpAction();
        SignUpAction.setAccount("cdefg");
        SignUpAction.setPassword("23456");


        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, currGameID, clientSocket, playerHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(SignUpAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPSignupSuccess().getClass());
        assertTrue(playerHashMap.containsKey("cdefg"));
        assertEquals(playerHashMap.get("cdefg").getPassword(),"23456");
        mockServer.closeSocket();
    }

    @Test
    void test_visitSignupFailed() throws IOException, ClassNotFoundException {

        //Communicator Thread:
        AccountID accountID = new AccountID();
        CurrGameID currGameID = new CurrGameID();

        Account testAccount = new Account();
        testAccount.setPassword("12345");
        HashMap<String, Account> playerHashMap = new HashMap<>();
        playerHashMap.put("abcde", testAccount);

        HashMap<Integer, Game> gameHashMap = new HashMap<>();

        //Client Action
        SignUpAction SignUpAction = new SignUpAction();
        SignUpAction.setAccount("abcde");
        SignUpAction.setPassword("12345");

        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();
        // Server creates a new thread
        CommunicationRunnable task =  new CommunicationRunnable (accountID, currGameID, clientSocket, playerHashMap, gameHashMap);
        Thread t = new Thread(task);
        t.start();

        mockClient.sendObject(SignUpAction);
        Response trueResponse = (Response) mockClient.recvObject();
        assertEquals(trueResponse.getClass(), new RSPSignupFail().getClass());
        mockServer.closeSocket();
    }


    @Test
    void testVisit() {
    }

    @Test
    void testVisit1() {
    }

    @Test
    void testVisit2() {
    }

    @Test
    void testVisit3() {
    }

    @Test
    void testVisit4() {
    }

    @Test
    void testVisit5() {
    }

    @Test
    void testVisit6() {
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