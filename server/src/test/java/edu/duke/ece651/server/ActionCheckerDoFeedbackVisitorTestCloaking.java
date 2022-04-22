package edu.duke.ece651.server;

import edu.duke.ece651.server.IO.MockClient;
import edu.duke.ece651.server.IO.MockServer;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.server.Wrapper.GameRunnableHashMap;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCheckerDoFeedbackVisitorTestCloaking {
//    @Test
//    void test_VisitCloaking() throws IOException, ClassNotFoundException, InterruptedException {
//        //==============================COMMUNICATOR==================================//
//        //new GameHashMap
//        GameHashMap gameHashMap = new GameHashMap();
//        //new AccountHashMap
//        AccountHashMap accountHashMap = new AccountHashMap();
//        //new GameRunnableHashmap
//        GameRunnableHashMap gameRunnableHashMap = new GameRunnableHashMap();
//        //new SocketConnection
//        MockServer mockServer = new MockServer(12337);
//        MockClient mockClient1 = new MockClient(12337, "127.0.0.1");
//        MockClient mockClient2 = new MockClient(12337, "127.0.0.1");
//        Socket clientSocket1 = mockServer.acceptClient();
//        Socket clientSocket2 = mockServer.acceptClient();
//
//
//        //new Game numOfPlayer = 2 for testing,
//        AccountID hostAccountID = new AccountID("1");
//        AccountID joinerAccountID = new AccountID("2");
//
//        //Host
//        CommunicatorRunnable task1 = new CommunicatorRunnable(clientSocket1, gameHashMap, accountHashMap, gameRunnableHashMap, 16);
//        Thread CommunicatorThread1 = new Thread(task1);
//        CommunicatorThread1.start();
//        //Joiner
//        CommunicatorRunnable task2 = new CommunicatorRunnable(clientSocket2, gameHashMap, accountHashMap, gameRunnableHashMap, 11);
//        Thread CommunicatorThread2 = new Thread(task2);
//        CommunicatorThread2.start();
//        ////==============================TESTBENCH==================================//
//
////H1 J1        //---------------------------Signup-------------------------------/ H1 J1
//        SignUpAction signUpAction = new SignUpAction(hostAccountID, "1");
//        mockClient1.sendObject(signUpAction);
//        Response responseSignup = (Response) mockClient1.recvObject();
//
//        SignUpAction signUpAction1 = new SignUpAction(joinerAccountID, "2");
//        mockClient2.sendObject(signUpAction1);
//        Response responseSignup1 = (Response) mockClient2.recvObject();
//
////H2 J2        //---------------------------Signin--------------------------------------/ H2 J2 +1
//        LoginAction loginAction = new LoginAction(hostAccountID, "1");
//        mockClient1.sendObject(loginAction);
//        Response responseSignin = (Response) mockClient1.recvObject();
//
//        //Login Fail
//        LoginAction loginActionFail = new LoginAction(new AccountID("abdhj"), "12345");
//        mockClient2.sendObject(loginActionFail);
//        mockClient2.recvObject();
//
//        //Login Success
//        LoginAction loginAction1 = new LoginAction(joinerAccountID, "2");
//        mockClient2.sendObject(loginAction1);
//        Response responseSignin1 = (Response) mockClient2.recvObject();
//
////H3 J3 J4        //---------------------------New Game Join Game------------------------/ H3 J3 J4 +2
//        NewGameAction newGameAction = new NewGameAction(2);
//        mockClient1.sendObject(newGameAction);
//        //Note that New Game does not require Response
//        //mockClient1.recvObject();
//        Thread.sleep(1000);
//
//        JoinAction joinAction = new JoinAction();
//        mockClient2.sendObject(joinAction);
//        mockClient2.recvObject();
//
//        //Choose Join Game Fail
//        ChooseJoinGameAction chooseJoinGameActionFail = new ChooseJoinGameAction(new GameID(5));
//        mockClient2.sendObject(chooseJoinGameActionFail);
//        mockClient2.recvObject();
//
//        //Choose Join Game Success
//        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
//        mockClient2.sendObject(chooseJoinGameAction);
//        Response responseJoinGame = (Response) mockClient2.recvObject();
//
//
//        mockClient2.sendObject(chooseJoinGameAction);
//        Response responseJoinGame1 = (Response) mockClient2.recvObject();
//
//        //Host receive New Game Success while all start
//        Response responseNewGameSuccess = (Response) mockClient1.recvObject();
////H5        //---------------------------Deploy--------------------------/ H5
//        //DeploySuccess
//        DeployAction deployAction = new DeployAction();
//        deployAction.setDeployUnits(1).setTo("a1");
//        mockClient1.sendObject(deployAction);
//        Response responseDeploySuccess = (Response) mockClient1.recvObject();
//        //ASSERT
//        assertEquals(responseDeploySuccess.getClass(), new RSPDeploySuccess().getClass());
////H6        //------------------------------Deploy Fail on Not my territory---------------------/ H6
//        deployAction.setDeployUnits(1).setTo("b1");
//        mockClient1.sendObject(deployAction);
//        Response response1 = (Response) mockClient1.recvObject();
//        assertEquals(new RSPDeployFail().getClass(), response1.getClass());
////H7        //------------------------------Deploy Fail on Not enough Deployment---------------/ H7
//        deployAction.setDeployUnits(40).setTo("a2");
//        mockClient1.sendObject(deployAction);
//        Response response2 = (Response) mockClient1.recvObject();
//        assertEquals(new RSPDeployFail().getClass(), response2.getClass());
//        mockServer.closeSocket();
////H8        //-------------------------------Commit Fail on Not finished Deployment-----------/ H8
//        CommitAction commitAction = new CommitAction();
//        mockClient1.sendObject(commitAction);
//        Response response3 = (Response) mockClient1.recvObject();
//        assertEquals(new RSPCommitFail().getClass(), response3.getClass());
//
////H9 J5        //------------------------------Deploy Finish----------------/ H9 J5
//        deployAction.setDeployUnits(5).setTo("a1");
//        mockClient1.sendObject(deployAction);
//        Response response4 = (Response) mockClient1.recvObject();
//        assertEquals(new RSPDeploySuccess().getClass(), response4.getClass());
//
//        deployAction.setDeployUnits(6).setTo("b1");
//        mockClient2.sendObject(deployAction);
//        Response response5 = (Response) mockClient2.recvObject();
//        assertEquals(new RSPDeploySuccess().getClass(), response5.getClass());
////H10 J6        //--------------Commit Success on fininshed Deployment-------------/ H10 J6
//        mockClient1.sendObject(commitAction);
//        mockClient2.sendObject(commitAction);
//        Response response6 = (Response) mockClient1.recvObject();
//        Response response7 = (Response) mockClient2.recvObject();
//        assertEquals(new RSPCommitSuccess().getClass(), response6.getClass());
//        assertEquals(new RSPCommitSuccess().getClass(), response7.getClass());
////H11 H12 J7        //------------------------------------------TechUpgrade------------/ H11 H12 J7
//        //Start Resource 100
//        //level 1 -> level 2 cost = 50
//        //Upgrade to level 1 success
//        UpgradeTechAction upgradeTechAction = new UpgradeTechAction();
//        mockClient1.sendObject(upgradeTechAction);
//        Response response3_1 = (Response) mockClient1.recvObject();
//        assertEquals(new RSPUpgradeTechSuccess().getClass(), response3_1.getClass());
//
//        //Update Fail because has done one
//        mockClient1.sendObject(upgradeTechAction);
//        Response response3_2 = (Response) mockClient1.recvObject();
//        assertEquals(new RSPUpgradeTechFail().getClass(), response3_2.getClass());
//
//        //----------CurrLevel unchanged, Tech Resources decrease------/
//        //Host Upgrade 1 level
//        //Host Tech Resource decrease
//        //Host currLevel remain unchanged
//        //Joiner didn't upgrade
//        Player host = gameHashMap.get(new GameID(1)).getPlayerHashMap().get(new AccountID("1"));
//        Player joiner = gameHashMap.get(new GameID(1)).getPlayerHashMap().get(new AccountID("2"));
//        assertEquals(1, host.getCurrTechLevel());
//        assertEquals(1, joiner.getCurrTechLevel());
//        assertEquals(50, host.getTechResource());
//        assertEquals(100, joiner.getTechResource());
////H13 J8        //----------Commit, CurrentLevel Changed------/ H13 J8
//        //host commit
//        mockClient1.sendObject(commitAction);
//        mockClient2.sendObject(commitAction);
//        Response response4_1 = (Response) mockClient1.recvObject();
//        assertEquals(new RSPCommitSuccess().getClass(), response4_1.getClass());
//        //joiner commit
//        Response response4_2 = (Response) mockClient2.recvObject();
//        assertEquals(new RSPCommitSuccess().getClass(), response4_2.getClass());
//        //Current Tech level of host changed
//        assertEquals(2, host.getCurrTechLevel());
//
//        //-------------------------------------------------Cloaking-------------------------------------/
//        //Cloaking fail because tech level is 2
//        CloakTerritoryAction cloakTerritoryAction = new CloakTerritoryAction("a1");
//        mockClient1.sendObject(cloakTerritoryAction);
//        Response rspCloakingAction = (Response) mockClient1.recvObject();
//        assertEquals(rspCloakingAction.getClass(), RSPCloakTerritoryActionFail.class);
//
//        mockClient1.sendObject(upgradeTechAction);
//        Response responseTech3 = (Response) mockClient1.recvObject();
//        assertEquals(new RSPUpgradeTechSuccess().getClass(), responseTech3.getClass());
//
//        mockClient1.sendObject(commitAction);
//        mockClient2.sendObject(commitAction);
//        mockClient1.recvObject();
//        mockClient2.recvObject();
//
//        //Cloaking Success
//        CloakTerritoryAction cloakTerritoryAction1 = new CloakTerritoryAction("a1");
//        mockClient1.sendObject(cloakTerritoryAction1);
//        Response rspCloakingAction1 = (Response) mockClient1.recvObject();
//        assertEquals(rspCloakingAction1.getClass(), RSPCloakTerritorySuccess.class);
//        mockServer.closeSocket();
//    }
}
