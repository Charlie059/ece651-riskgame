package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.*;
import edu.duke.ece651.shared.IO.ClientActions.Action;
import edu.duke.ece651.shared.IO.ClientActions.SignUpAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSignupFail;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.CurrGameID;
import edu.duke.ece651.shared.Wrapper.PlayerID;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class ActionCheckDoFeedbackVistorTest {


    public class CommunicationRunnable implements Runnable {
        private PlayerID playerID;
        private CurrGameID currGameID;
        private Socket clientSocket;
        private ObjectStream objectStream;
        private HashMap<Integer, Game> gameHashMap;
        private HashMap<String, Player> playerHashMap;


        public CommunicationRunnable(PlayerID playerID, CurrGameID currGameID, Socket clientSocket, HashMap<String, Player> playerHashMap, HashMap<Integer, Game> gameHashMap, ObjectStream objectStream) throws IOException {
            this.playerID = playerID;
            this.currGameID = currGameID;
            this.clientSocket = clientSocket;
            this.gameHashMap = gameHashMap;
            this.playerHashMap = playerHashMap;
            this.objectStream = objectStream;
        }

        /**
         * Receive Client Action Object
         *
         * @return Action Object or Null(to indicate error)
         */
        private Action recvAction() {
            try {
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
            action.accept(new ActionCheckDoFeedbackVisitor(this.playerID, this.currGameID, this.clientSocket, this.objectStream, this.playerHashMap, this.gameHashMap));
        }
    }

    private Socket helperMockConstructor(PlayerID playerID, CurrGameID currGameID, HashMap<String, Player> playerHashMap, HashMap<Integer, Game> gameHashMap) throws IOException {
        //Server
        MockServer mockServer = new MockServer(12345);
        MockClient mockClient = new MockClient(12345, "127.0.0.1");
        Socket clientSocket = mockServer.acceptClient();

//        ObjectStream serverObjectStream = new ObjectStream(clientSocket);
//        ObjectStream clientObjectStream = new ObjectStream(clientSocket);
//        // Server creates a new thread
//        CommunicationRunnable task =  new CommunicationRunnable (playerID, currGameID, clientSocket, playerHashMap, gameHashMap, serverObjectStream);
//        new Thread(task).start();
        return clientSocket;
    }


    @Test
    void test_visitSignup() {

        //Communicator Thread:
        PlayerID playerID = new PlayerID();
        CurrGameID currGameID = new CurrGameID();

        Player testPlayer = new Player();
        testPlayer.setPassword("12345");
        HashMap<String, Player> playerHashMap = new HashMap<>();
        playerHashMap.put("abcde", testPlayer);

        HashMap<Integer, Game> gameHashMap = new HashMap<>();
        MockClient mockClient = null;

        //Client Action
        SignUpAction signUpAction = new SignUpAction();
        signUpAction.setAccount("cdefg");
        signUpAction.setPassword("12345");

        Socket clientSocket = null;
        try {
            clientSocket = helperMockConstructor(playerID,currGameID,playerHashMap,gameHashMap);
            signUpAction.accept(new ActionCheckDoFeedbackVisitor(playerID,currGameID,clientSocket,));
        } catch (IOException e) {
            e.printStackTrace();
        }




        //Mock Construct
        try {
            ObjectStream clientObjectStream = helperMockConstructor(playerID, currGameID, playerHashMap, gameHashMap);
            Response response = (Response) clientObjectStream.recvObject();
            RSPSignupFail rspSignupFail = new RSPSignupFail();
            assertEquals(rspSignupFail.getClass(),response.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


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