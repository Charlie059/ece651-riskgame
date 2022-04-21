package edu.duke.ece651.server;

import edu.duke.ece651.server.IO.MockClient;
import edu.duke.ece651.server.IO.MockServer;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.server.Wrapper.GameRunnableHashMap;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;
import edu.duke.ece651.shared.Wrapper.GameID;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionCheckDoFeedBackVisitorTestGameProcess {

    @Test
    void test_VisitSignup_VisitLogin_VisitCommit_VisitDeploy_VisitTechUpgrade_VisitUpgradeUnit_VisitMove_VisitAttack() throws IOException, ClassNotFoundException, InterruptedException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = new GameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = new AccountHashMap();
        //new GameRunnableHashmap
        GameRunnableHashMap gameRunnableHashMap = new GameRunnableHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12336);
        MockClient mockClient1 = new MockClient(12336, "127.0.0.1");
        MockClient mockClient2 = new MockClient(12336, "127.0.0.1");
        Socket clientSocket1 = mockServer.acceptClient();
        Socket clientSocket2 = mockServer.acceptClient();


        //new Game numOfPlayer = 2 for testing,
        AccountID hostAccountID = new AccountID("1");
        AccountID joinerAccountID = new AccountID("2");

        //Host
        CommunicatorRunnable task1 = new CommunicatorRunnable(clientSocket1, gameHashMap, accountHashMap, gameRunnableHashMap, 53);
        Thread CommunicatorThread1 = new Thread(task1);
        CommunicatorThread1.start();
        //Joiner
        CommunicatorRunnable task2 = new CommunicatorRunnable(clientSocket2, gameHashMap, accountHashMap, gameRunnableHashMap, 27);
        Thread CommunicatorThread2 = new Thread(task2);
        CommunicatorThread2.start();
        ////==============================TESTBENCH==================================//

//H1 J1        //---------------------------Signup-------------------------------/ H1 J1
        SignUpAction signUpAction = new SignUpAction(hostAccountID, "1");
        mockClient1.sendObject(signUpAction);
        Response responseSignup = (Response) mockClient1.recvObject();

        SignUpAction signUpAction1 = new SignUpAction(joinerAccountID, "2");
        mockClient2.sendObject(signUpAction1);
        Response responseSignup1 = (Response) mockClient2.recvObject();

//H2 J2        //---------------------------Signin--------------------------------------/ H2 J2 +1
        LoginAction loginAction = new LoginAction(hostAccountID, "1");
        mockClient1.sendObject(loginAction);
        Response responseSignin = (Response) mockClient1.recvObject();

        //Login Fail
        LoginAction loginActionFail = new LoginAction(new AccountID("abdhj"), "12345");
        mockClient2.sendObject(loginActionFail);
        mockClient2.recvObject();

        //Login Success
        LoginAction loginAction1 = new LoginAction(joinerAccountID, "2");
        mockClient2.sendObject(loginAction1);
        Response responseSignin1 = (Response) mockClient2.recvObject();

//H3 J3 J4        //---------------------------New Game Join Game------------------------/ H3 J3 J4 +2
        NewGameAction newGameAction = new NewGameAction(2);
        mockClient1.sendObject(newGameAction);
        //Note that New Game does not require Response
        //mockClient1.recvObject();
        Thread.sleep(1000);

        JoinAction joinAction = new JoinAction();
        mockClient2.sendObject(joinAction);
        mockClient2.recvObject();

        //Choose Join Game Fail
        ChooseJoinGameAction chooseJoinGameActionFail = new ChooseJoinGameAction(new GameID(5));
        mockClient2.sendObject(chooseJoinGameActionFail);
        mockClient2.recvObject();

        //Choose Join Game Success
        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
        mockClient2.sendObject(chooseJoinGameAction);
        Response responseJoinGame = (Response) mockClient2.recvObject();


        mockClient2.sendObject(chooseJoinGameAction);
        Response responseJoinGame1 = (Response) mockClient2.recvObject();

        //Host receive New Game Success while all start
        Response responseNewGameSuccess = (Response) mockClient1.recvObject();
//H5        //---------------------------Deploy--------------------------/ H5
        //DeploySuccess
        DeployAction deployAction = new DeployAction();
        deployAction.setDeployUnits(1).setTo("a1");
        mockClient1.sendObject(deployAction);
        Response responseDeploySuccess = (Response) mockClient1.recvObject();
        //ASSERT
        assertEquals(responseDeploySuccess.getClass(), new RSPDeploySuccess().getClass());
//H6        //------------------------------Deploy Fail on Not my territory---------------------/ H6
        deployAction.setDeployUnits(1).setTo("b1");
        mockClient1.sendObject(deployAction);
        Response response1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeployFail().getClass(), response1.getClass());
//H7        //------------------------------Deploy Fail on Not enough Deployment---------------/ H7
        deployAction.setDeployUnits(40).setTo("a2");
        mockClient1.sendObject(deployAction);
        Response response2 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeployFail().getClass(), response2.getClass());
        mockServer.closeSocket();
//H8        //-------------------------------Commit Fail on Not finished Deployment-----------/ H8
        CommitAction commitAction = new CommitAction();
        mockClient1.sendObject(commitAction);
        Response response3 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitFail().getClass(), response3.getClass());

//H9 J5        //------------------------------Deploy Finish----------------/ H9 J5
        deployAction.setDeployUnits(5).setTo("a1");
        mockClient1.sendObject(deployAction);
        Response response4 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response4.getClass());

        deployAction.setDeployUnits(6).setTo("b1");
        mockClient2.sendObject(deployAction);
        Response response5 = (Response) mockClient2.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response5.getClass());
//H10 J6        //--------------Commit Success on fininshed Deployment-------------/ H10 J6
        mockClient1.sendObject(commitAction);
        mockClient2.sendObject(commitAction);
        Response response6 = (Response) mockClient1.recvObject();
        Response response7 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response6.getClass());
        assertEquals(new RSPCommitSuccess().getClass(), response7.getClass());
//H11 H12 J7        //------------------------------------------TechUpgrade------------/ H11 H12 J7
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
        Player host = gameHashMap.get(new GameID(1)).getPlayerHashMap().get(new AccountID("1"));
        Player joiner = gameHashMap.get(new GameID(1)).getPlayerHashMap().get(new AccountID("2"));
        assertEquals(1, host.getCurrTechLevel());
        assertEquals(1, joiner.getCurrTechLevel());
        assertEquals(50, host.getTechResource());
        assertEquals(100, joiner.getTechResource());
//H13 J8        //----------Commit, CurrentLevel Changed------/ H13 J8
        //host commit
        mockClient1.sendObject(commitAction);
        mockClient2.sendObject(commitAction);
        Response response4_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response4_1.getClass());
        //joiner commit
        Response response4_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response4_2.getClass());
        //Current Tech level of host changed
        assertEquals(2, host.getCurrTechLevel());

//H14 J9        //-----------------------------------------------------Unit Upgrade--------------/H14 J9
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
//H15 J10        //------------------Unit Upgrade do not have enough unit--------------/H15 J10
        host.setCurrTechLevel(6);
        joiner.setCurrTechLevel(6);
        hostUpgradeUnitsAction.setOldLevel(2).setNewLevel(3).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(2).setNewLevel(3).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse2 = (Response) mockClient1.recvObject();
        Response joinerResponse2 = (Response) mockClient2.recvObject();
        //Assert upgrade fail
        assertEquals(new RSPUpgradeUnitsFail().getClass(), hostResponse2.getClass());
        assertEquals(new RSPUpgradeUnitsFail().getClass(), joinerResponse2.getClass());

//H21 J16        //-----------Unit Upgrade do not have enough resource--------------/ H21 J16
        hostUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse3 = (Response) mockClient1.recvObject();
        Response joinerResponse3 = (Response) mockClient2.recvObject();
        hostUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse4 = (Response) mockClient1.recvObject();
        Response joinerResponse4 = (Response) mockClient2.recvObject();
        hostUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse5 = (Response) mockClient1.recvObject();
        Response joinerResponse5 = (Response) mockClient2.recvObject();
        hostUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse6 = (Response) mockClient1.recvObject();
        Response joinerResponse6 = (Response) mockClient2.recvObject();
        hostUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse7 = (Response) mockClient1.recvObject();
        Response joinerResponse7 = (Response) mockClient2.recvObject();
        hostUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("a1");
        joinerUpgradeUnitsAction.setOldLevel(0).setNewLevel(6).setWhere("b1");
        mockClient1.sendObject(hostUpgradeUnitsAction);
        mockClient2.sendObject(joinerUpgradeUnitsAction);
        Response hostResponse8 = (Response) mockClient1.recvObject();
        Response joinerResponse8 = (Response) mockClient2.recvObject();
        //Assert upgrade fail
        assertEquals(new RSPUpgradeUnitsFail().getClass(), hostResponse8.getClass());
        assertEquals(new RSPUpgradeUnitsFail().getClass(), joinerResponse8.getClass());


//H22 J17        //--------------------------------------------------------move-------------------/ H22 J17
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
        assertEquals(new RSPMoveSuccess("a1", "a2", units, 3).getClass(), hostResponse.getClass());
        assertEquals(new RSPMoveSuccess("b1", "b2", units, 3).getClass(), joinerResponse.getClass());
        assertEquals(1, host.getMyTerritories().get("a1").getUnits().get(0).getValue());
        assertEquals(3, host.getMyTerritories().get("a2").getUnits().get(0).getValue());
        //assert move fail
        moveAction.setFrom("a1").setTo("b3").setUnits(units);
        mockClient2.sendObject(moveAction);
        mockClient2.recvObject();
//H23 J18        //--------------------move Fail of Not Enough---/H23 J18
        mockClient1.sendObject(moveAction);
        mockClient2.sendObject(moveAction);
        Response hostResponseMove1 = (Response) mockClient1.recvObject();
        Response joinerResponseMove2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPMoveFail().getClass(), hostResponseMove1.getClass());
//H24 J18        //-------------------------------------------Attack-------------------------/H24 J18
        AttackAction attackAction = new AttackAction();
        ArrayList<Integer> level0attack = new ArrayList<>();
        level0attack.add(6);//level
        level0attack.add(1);//num
        ArrayList<ArrayList<Integer>> unitsHost = new ArrayList<>();
        unitsHost.add(level0attack);
        attackAction.setTo("b1").setFrom("a1").setUnits(unitsHost);
        mockClient1.sendObject(attackAction);
        Response responseAttack = (Response) mockClient1.recvObject();
        assertEquals(new RSPAttackSuccess("a1", "b1", units, 3).getClass(), responseAttack.getClass());
//H25 J18        //----------------attack Fail--------------/ H25 J18
        ArrayList<Integer> level0attack1 = new ArrayList<>();
        level0attack1.add(0);//level
        level0attack1.add(10);//num
        ArrayList<ArrayList<Integer>> unitsJoiner = new ArrayList<>();
        unitsJoiner.add(level0attack1);
        attackAction.setTo("b1").setFrom("a1").setUnits(unitsJoiner);
        mockClient1.sendObject(attackAction);
        Response responseAttack1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPAttackFail("").getClass(), responseAttack1.getClass());
        //-------------------------------------------------SpyDeploy-------------------------------------/
        //Spy Deploy Success
        SpyDeployAction spyDeployAction = new SpyDeployAction("a1", "a1");
        mockClient1.sendObject(spyDeployAction);
        RSPSpyDeploySuccess responseSpyDeploy = (RSPSpyDeploySuccess) mockClient1.recvObject();
        assertEquals(responseSpyDeploy.getClass(), RSPSpyDeploySuccess.class);
        //Spy Deploy Unsuccess because it's not my territory
        SpyDeployAction spyDeployAction1 = new SpyDeployAction("b1", "b1");
        mockClient1.sendObject(spyDeployAction1);
        Response rspSpyDeploy1 = (Response) mockClient1.recvObject();
        assertEquals(rspSpyDeploy1.getClass(), RSPSpyDeployFail.class);
        //Spy Deploy Unsuccess because tech Source not enough or Unit not enough
        mockClient1.sendObject(spyDeployAction);
        RSPSpyDeploySuccess responseSpyDeploy2 = (RSPSpyDeploySuccess) mockClient1.recvObject();
        assertEquals(responseSpyDeploy2.getClass(), RSPSpyDeploySuccess.class);

        mockClient1.sendObject(spyDeployAction);
        RSPSpyDeploySuccess responseSpyDeploy3 = (RSPSpyDeploySuccess) mockClient1.recvObject();
        assertEquals(responseSpyDeploy3.getClass(), RSPSpyDeploySuccess.class);
        mockClient1.sendObject(spyDeployAction);
        RSPSpyDeploySuccess responseSpyDeploy4 = (RSPSpyDeploySuccess) mockClient1.recvObject();
        assertEquals(responseSpyDeploy4.getClass(), RSPSpyDeploySuccess.class);
        mockClient1.sendObject(spyDeployAction);
        RSPSpyDeploySuccess responseSpyDeploy5 = (RSPSpyDeploySuccess) mockClient1.recvObject();
        assertEquals(responseSpyDeploy5.getClass(), RSPSpyDeploySuccess.class);
        mockClient1.sendObject(spyDeployAction);
        Response responseSpyDeploy6 = (Response) mockClient1.recvObject();
        assertEquals(responseSpyDeploy6.getClass(), RSPSpyDeployFail.class);
        //-------------------------------------------------SpyMove-------------------------------------/
        //Spy Move Success and
        SpyMoveAction spyMoveAction = new SpyMoveAction(responseSpyDeploy.getSpyUUID(), "a1", "b3");
        SpyMoveAction spyMoveActionB = new SpyMoveAction(responseSpyDeploy.getSpyUUID(), "b3", "a1");

        mockClient1.sendObject(spyMoveAction);
        Response rspSpyMove = (Response) mockClient1.recvObject();
        assertEquals(rspSpyMove.getClass(), RSPSpyMoveSuccess.class);

        //Spy Move Fail on Not my Spy
        mockClient2.sendObject(spyMoveActionB);
        Response rspSpyMoveJoiner = (Response) mockClient2.recvObject();
        assertEquals(rspSpyMoveJoiner.getClass(), RSPSpyMoveFail.class);
        //Spy Move Fail on Lacking of Food
        Integer time = 6;
        for (int i = 0; i <= time; i++) {
            mockClient1.sendObject(spyMoveActionB);
            Response rspSpyMoveB = (Response) mockClient1.recvObject();

            mockClient1.sendObject(spyMoveAction);
            Response rspSpyMove1 = (Response) mockClient1.recvObject();

            if (i == time) {
                assertEquals(rspSpyMove1.getClass(), RSPSpyMoveFail.class);
            }
        }
        //-------------------------------------------------SpyUpgrade-------------------------------------/
        //Spy Upgrade Success
        SpyUpgradeAction spyUpgradeAction = new SpyUpgradeAction("a1", responseSpyDeploy2.getSpyUUID(), new CardType().getSpecialSpyUpgrade().get(0));
        mockClient1.sendObject(spyUpgradeAction);
        Response rspspyUpgradeAction = (Response) mockClient1.recvObject();
        assertEquals(rspspyUpgradeAction.getClass(), RSPSpyUpgradeSuccess.class);
        //Spy Upgrade Fail because they don't have any special Card
        mockClient1.sendObject(spyUpgradeAction);
        Response rspspyUpgradeAction1 = (Response) mockClient1.recvObject();
        assertEquals(rspspyUpgradeAction1.getClass(), RSPSpyUpgradeFail.class);
        //AT THIS TIME HOST 1:
        //FOOD RESOURCE = 10, TECH RESOURCE = 7, CURRTECHLEVEL = 6

        CloakTerritoryAction cloakTerritoryAction = new CloakTerritoryAction("a1");
        mockClient1.sendObject(cloakTerritoryAction);
        Response rspCloakingAction = (Response) mockClient1.recvObject();
        assertEquals(rspCloakingAction.getClass(), RSPCloakTerritoryActionFail.class);

        mockServer.closeSocket();
    }


    @Test
    void test_VisitCloaking() throws IOException, ClassNotFoundException, InterruptedException {
        //==============================COMMUNICATOR==================================//
        //new GameHashMap
        GameHashMap gameHashMap = new GameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = new AccountHashMap();
        //new GameRunnableHashmap
        GameRunnableHashMap gameRunnableHashMap = new GameRunnableHashMap();
        //new SocketConnection
        MockServer mockServer = new MockServer(12336);
        MockClient mockClient1 = new MockClient(12336, "127.0.0.1");
        MockClient mockClient2 = new MockClient(12336, "127.0.0.1");
        Socket clientSocket1 = mockServer.acceptClient();
        Socket clientSocket2 = mockServer.acceptClient();


        //new Game numOfPlayer = 2 for testing,
        AccountID hostAccountID = new AccountID("1");
        AccountID joinerAccountID = new AccountID("2");

        //Host
        CommunicatorRunnable task1 = new CommunicatorRunnable(clientSocket1, gameHashMap, accountHashMap, gameRunnableHashMap, 20);
        Thread CommunicatorThread1 = new Thread(task1);
        CommunicatorThread1.start();
        //Joiner
        CommunicatorRunnable task2 = new CommunicatorRunnable(clientSocket2, gameHashMap, accountHashMap, gameRunnableHashMap, 15);
        Thread CommunicatorThread2 = new Thread(task2);
        CommunicatorThread2.start();
        ////==============================TESTBENCH==================================//

//H1 J1        //---------------------------Signup-------------------------------/ H1 J1
        SignUpAction signUpAction = new SignUpAction(hostAccountID, "1");
        mockClient1.sendObject(signUpAction);
        Response responseSignup = (Response) mockClient1.recvObject();

        SignUpAction signUpAction1 = new SignUpAction(joinerAccountID, "2");
        mockClient2.sendObject(signUpAction1);
        Response responseSignup1 = (Response) mockClient2.recvObject();

//H2 J2        //---------------------------Signin--------------------------------------/ H2 J2 +1
        LoginAction loginAction = new LoginAction(hostAccountID, "1");
        mockClient1.sendObject(loginAction);
        Response responseSignin = (Response) mockClient1.recvObject();

        //Login Fail
        LoginAction loginActionFail = new LoginAction(new AccountID("abdhj"), "12345");
        mockClient2.sendObject(loginActionFail);
        mockClient2.recvObject();

        //Login Success
        LoginAction loginAction1 = new LoginAction(joinerAccountID, "2");
        mockClient2.sendObject(loginAction1);
        Response responseSignin1 = (Response) mockClient2.recvObject();

//H3 J3 J4        //---------------------------New Game Join Game------------------------/ H3 J3 J4 +2
        NewGameAction newGameAction = new NewGameAction(2);
        mockClient1.sendObject(newGameAction);
        //Note that New Game does not require Response
        //mockClient1.recvObject();
        Thread.sleep(1000);

        JoinAction joinAction = new JoinAction();
        mockClient2.sendObject(joinAction);
        mockClient2.recvObject();

        //Choose Join Game Fail
        ChooseJoinGameAction chooseJoinGameActionFail = new ChooseJoinGameAction(new GameID(5));
        mockClient2.sendObject(chooseJoinGameActionFail);
        mockClient2.recvObject();

        //Choose Join Game Success
        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
        mockClient2.sendObject(chooseJoinGameAction);
        Response responseJoinGame = (Response) mockClient2.recvObject();


        mockClient2.sendObject(chooseJoinGameAction);
        Response responseJoinGame1 = (Response) mockClient2.recvObject();

        //Host receive New Game Success while all start
        Response responseNewGameSuccess = (Response) mockClient1.recvObject();
//H5        //---------------------------Deploy--------------------------/ H5
        //DeploySuccess
        DeployAction deployAction = new DeployAction();
        deployAction.setDeployUnits(1).setTo("a1");
        mockClient1.sendObject(deployAction);
        Response responseDeploySuccess = (Response) mockClient1.recvObject();
        //ASSERT
        assertEquals(responseDeploySuccess.getClass(), new RSPDeploySuccess().getClass());
//H6        //------------------------------Deploy Fail on Not my territory---------------------/ H6
        deployAction.setDeployUnits(1).setTo("b1");
        mockClient1.sendObject(deployAction);
        Response response1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeployFail().getClass(), response1.getClass());
//H7        //------------------------------Deploy Fail on Not enough Deployment---------------/ H7
        deployAction.setDeployUnits(40).setTo("a2");
        mockClient1.sendObject(deployAction);
        Response response2 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeployFail().getClass(), response2.getClass());
        mockServer.closeSocket();
//H8        //-------------------------------Commit Fail on Not finished Deployment-----------/ H8
        CommitAction commitAction = new CommitAction();
        mockClient1.sendObject(commitAction);
        Response response3 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitFail().getClass(), response3.getClass());

//H9 J5        //------------------------------Deploy Finish----------------/ H9 J5
        deployAction.setDeployUnits(5).setTo("a1");
        mockClient1.sendObject(deployAction);
        Response response4 = (Response) mockClient1.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response4.getClass());

        deployAction.setDeployUnits(6).setTo("b1");
        mockClient2.sendObject(deployAction);
        Response response5 = (Response) mockClient2.recvObject();
        assertEquals(new RSPDeploySuccess().getClass(), response5.getClass());
//H10 J6        //--------------Commit Success on fininshed Deployment-------------/ H10 J6
        mockClient1.sendObject(commitAction);
        mockClient2.sendObject(commitAction);
        Response response6 = (Response) mockClient1.recvObject();
        Response response7 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response6.getClass());
        assertEquals(new RSPCommitSuccess().getClass(), response7.getClass());
//H11 H12 J7        //------------------------------------------TechUpgrade------------/ H11 H12 J7
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
        Player host = gameHashMap.get(new GameID(1)).getPlayerHashMap().get(new AccountID("1"));
        Player joiner = gameHashMap.get(new GameID(1)).getPlayerHashMap().get(new AccountID("2"));
        assertEquals(1, host.getCurrTechLevel());
        assertEquals(1, joiner.getCurrTechLevel());
        assertEquals(50, host.getTechResource());
        assertEquals(100, joiner.getTechResource());
//H13 J8        //----------Commit, CurrentLevel Changed------/ H13 J8
        //host commit
        mockClient1.sendObject(commitAction);
        mockClient2.sendObject(commitAction);
        Response response4_1 = (Response) mockClient1.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response4_1.getClass());
        //joiner commit
        Response response4_2 = (Response) mockClient2.recvObject();
        assertEquals(new RSPCommitSuccess().getClass(), response4_2.getClass());
        //Current Tech level of host changed
        assertEquals(2, host.getCurrTechLevel());

        //-------------------------------------------------Cloaking-------------------------------------/
        //Cloaking fail because tech level is 2
        CloakTerritoryAction cloakTerritoryAction = new CloakTerritoryAction("a1");
        mockClient1.sendObject(cloakTerritoryAction);
        Response rspCloakingAction = (Response) mockClient1.recvObject();
        assertEquals(rspCloakingAction.getClass(), RSPCloakTerritoryActionFail.class);

        mockClient1.sendObject(upgradeTechAction);
        Response responseTech3 = (Response) mockClient1.recvObject();
        assertEquals(new RSPUpgradeTechSuccess().getClass(), responseTech3.getClass());

        mockClient1.sendObject(commitAction);
        mockClient2.sendObject(commitAction);
        mockClient1.recvObject();
        mockClient2.recvObject();

        //Cloaking Success
        CloakTerritoryAction cloakTerritoryAction1 = new CloakTerritoryAction("a1");
        mockClient1.sendObject(cloakTerritoryAction1);
        Response rspCloakingAction1 = (Response) mockClient1.recvObject();
        assertEquals(rspCloakingAction1.getClass(), RSPCloakTerritorySuccess.class);


        mockServer.closeSocket();
    }
}
