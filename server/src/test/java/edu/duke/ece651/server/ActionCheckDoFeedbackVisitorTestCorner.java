package edu.duke.ece651.server;

import edu.duke.ece651.server.IO.MockClient;
import edu.duke.ece651.server.IO.MockServer;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.IO.ClientActions.ChooseJoinGameAction;
import edu.duke.ece651.shared.IO.ClientActions.JoinAction;
import edu.duke.ece651.shared.IO.ClientActions.NewGameAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPChooseJoinGameFail;
import edu.duke.ece651.shared.IO.ServerResponse.RSPChooseJoinGameSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.RSPOpenGameList;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionCheckDoFeedbackVisitorTestCorner {

    private AccountHashMap createAccountHashMap() {
        AccountHashMap accountHashMap = new AccountHashMap();
        Account account = new Account();
        account.setPassword("12345");
        accountHashMap.put(new AccountID("abcde"), account);
        return accountHashMap;
    }

//    @Test
//    void test_VisitJoinChooseFail() throws IOException, ClassNotFoundException, InterruptedException {
//        //==============================COMMUNICATOR==================================//
//        //new GameHashMap
////        GameHashMap gameHashMap = this.createGameHashMap();
//        GameHashMap gameHashMap = new GameHashMap();
//        //new AccountHashMap
//        AccountHashMap accountHashMap = this.createAccountHashMap();
//        //new SocketConnection
//        MockServer mockServer = new MockServer(12345);
//        MockClient mockClientHost = new MockClient(12345, "127.0.0.1");
//        MockClient mockClientJoiner = new MockClient(12345, "127.0.0.1");
//        MockClient mockClientFalseJoiner=  new MockClient(12345,"127.0.0.1");
//        Socket clientSocket1 = mockServer.acceptClient();
//        Socket clientSocket2 = mockServer.acceptClient();
//        Socket clientSocket3 = mockServer.acceptClient();
//        //Host
//        AccountID hostAccountID = new AccountID("abcde");
//        CommunicatorRunnable hostTask = new CommunicatorRunnable(hostAccountID, null, clientSocket1, accountHashMap, gameHashMap, 1);
//        Thread CommunicatorThreadHost = new Thread(hostTask);
//        CommunicatorThreadHost.start();
//        //Joiner
//        AccountID joinerAccountID = new AccountID("cdefg");
//        CommunicatorRunnable joinerTask1 = new CommunicatorRunnable(joinerAccountID, null, clientSocket2, accountHashMap, gameHashMap, 2);
//        Thread CommunicatorThreadJoiner = new Thread(joinerTask1);
//        CommunicatorThreadJoiner.start();
//        //False Joiner
//        AccountID falseJoinerAccountID = new AccountID("ooooo");
//        CommunicatorRunnable falseJoinerTask1 = new CommunicatorRunnable(falseJoinerAccountID, null, clientSocket3, accountHashMap, gameHashMap, 1);
//        Thread CommunicatorThreadFalseJoiner = new Thread(falseJoinerTask1);
//        CommunicatorThreadFalseJoiner.start();
//        ////==============================TESTBENCH==================================//
//        //New Game Action
//        NewGameAction newGameAction = new NewGameAction(2);
//        //Host New Game
//        mockClientHost.sendObject(newGameAction);
//
//        //Joiner join Game
//        JoinAction joinAction = new JoinAction();
//        mockClientJoiner.sendObject(joinAction);
//        Response responsejoiner1 = (Response) mockClientJoiner.recvObject();
//        ArrayList<GameID> gameIDArrayList = new ArrayList<>();
//        gameIDArrayList.add(new GameID(1));
//        gameIDArrayList.add(new GameID(2));
//        assertEquals(new RSPOpenGameList(gameIDArrayList).getClass(), responsejoiner1.getClass());
//        //Join the Choose Game
//        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
//        mockClientJoiner.sendObject(chooseJoinGameAction);
//        Response responsejoiner2 = (Response) mockClientJoiner.recvObject();
//        assertEquals(new RSPChooseJoinGameSuccess().getClass(), responsejoiner2.getClass());
//        //Host Receive Object
//        Response responseHost2= (Response) mockClientHost.recvObject();
//
//        //False Joiner join the Choose Game
//        ChooseJoinGameAction chooseJoinGameAction1 = new ChooseJoinGameAction(new GameID(1));
//        mockClientFalseJoiner.sendObject(chooseJoinGameAction1);
//        Response responseFalseJoiner = (Response) mockClientFalseJoiner.recvObject();
//        assertEquals(new RSPChooseJoinGameFail().getClass(),responseFalseJoiner.getClass());
//        mockServer.closeSocket();
//    }
}
