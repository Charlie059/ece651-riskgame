package edu.duke.ece651.shared.IO;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.GameIDCounter;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Visitor.RSPVisitor;
import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    @Test
    public void StructureTest() throws IOException {

        //Action
        AttackAction attackAction = new AttackAction("a1","b1",new ArrayList<>());
        ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(1));
        chooseJoinGameAction.setGameID(new GameID(2));
        ChooseSwitchGameAction chooseSwitchGameAction = new ChooseSwitchGameAction(new GameID(1));
        chooseJoinGameAction.setGameID(new GameID(2));
        DeployAction deployAction = new DeployAction("a1",2,"12345");
        LoginAction loginAction = new LoginAction(new AccountID("abcde"),"12345");
        loginAction.setEnterPassword("12356");
        loginAction.setEnterAccount(new AccountID("cdefg"));
        MoveAction moveAction = new MoveAction("a1","c1",new ArrayList<>());
        NewGameAction newGameAction = new NewGameAction(2);
        newGameAction.setNumOfPlayer(5);
        SignUpAction signUpAction = new SignUpAction(new AccountID("abcde"),"12345");
        signUpAction.setAccount(new AccountID("abcde"));
        UpgradeUnitsAction upgradeUnitsAction =  new UpgradeUnitsAction("a1",2,3);
        ClientPlayerPacket clientPlayerPacket  = new ClientPlayerPacket(null,null,3,100,100,2,100,null,null,null,null);
        clientPlayerPacket.getCurrentGameID();
        clientPlayerPacket.getAccountID();
        clientPlayerPacket.getNumOfPlayers();
        clientPlayerPacket.getFoodResource();
        clientPlayerPacket.getTotalDeployment();
        clientPlayerPacket.getTechResource();
        clientPlayerPacket.getTechLevel();
        clientPlayerPacket.getMyTerritories();
        clientPlayerPacket.getEnemyTerritories();
        clientPlayerPacket.getLose();
        clientPlayerPacket.getWin();
        //clientPlayerPacket.doDeploy("a1",1);
        //clientPlayerPacket.doMove("a1","b1",new ArrayList<>(),3);
        //RSP
        RSPAttackSuccess rspAttackSuccess = new RSPAttackSuccess("a1","a2",new ArrayList<>(),3);
        rspAttackSuccess.getFrom();
        rspAttackSuccess.setFrom("a1");
        rspAttackSuccess.getTo();
        rspAttackSuccess.setTo("a1");
        rspAttackSuccess.getUnits();
        rspAttackSuccess.setUnits(new ArrayList<>());
        rspAttackSuccess.getTotalCost();
        rspAttackSuccess.setTotalCost(3);
        RSPChooseJoinGameSuccess rspChooseJoinGameSuccess = new RSPChooseJoinGameSuccess();
        rspChooseJoinGameSuccess.getClientPlayerPacket();
        RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess = new RSPChooseSwitchGameSuccess();
        rspChooseSwitchGameSuccess.getClientPlayerPacket();
        RSPCommitSuccess rspCommitSuccess = new RSPCommitSuccess();
        rspCommitSuccess.getClientPlayerPacket();
        RSPLoginSuccess rspLoginSuccess = new RSPLoginSuccess();

        RSPMoveSuccess rspMoveSuccess = new RSPMoveSuccess("a1","b1",new ArrayList<>(),1);
        rspMoveSuccess.getFrom();
        rspMoveSuccess.setFrom("a1");
        rspMoveSuccess.getTo();
        rspMoveSuccess.setTo("a1");
        rspMoveSuccess.getUnits();
        rspMoveSuccess.setUnits(new ArrayList<>());
        rspMoveSuccess.getTotalCost();
        rspMoveSuccess.setTotalCost(3);
        RSPNewGameSuccess rspNewGameSuccess = new RSPNewGameSuccess();
        rspNewGameSuccess.getClientPlayerPacket();
        RSPOpenGameList rspOpenGameList = new RSPOpenGameList(new ArrayList<>());
        RSPUpgradeTechSuccess rspUpgradeTechSuccess = new RSPUpgradeTechSuccess();
        rspUpgradeTechSuccess.getTechCost();
        RSPUpgradeUnitsSuccess rspUpgradeUnitsSuccess = new RSPUpgradeUnitsSuccess();
        rspUpgradeUnitsSuccess.getWhere();
        rspUpgradeUnitsSuccess.getOldLevel();
        rspUpgradeUnitsSuccess.getNewLevel();
        rspUpgradeUnitsSuccess.getTechCost();
        RSPVisitor rspVisitor = new RSPVisitor();
        chooseSwitchGameAction.setGameID(new GameID(1));
        signUpAction.setPassword("12345");
        assertEquals(new RSPSwitchGameList(new ArrayList<>()).getClass(), new RSPSwitchGameList(new ArrayList<>()).getClass());
        assertEquals(new RSPOpenGameList(new ArrayList<>()).getClass(), new RSPOpenGameList(new ArrayList<>()).getClass());
        Account account = new Account();
        account.getPassword();
        account.setPassword("12345");
        GameIDCounter gameIDCounter = new GameIDCounter();
        gameIDCounter.getInstance();
        gameIDCounter.getCurrent_id();
        AccountID accountID = new AccountID("12345");
        accountID.getAccountID();
        accountID.setAccountID("123");
        accountID.hashCode();
        GameID gameID = new GameID(123);
        gameID.getCurrGameID();
        gameID.setCurrGameID(1234);
        assertEquals(new GameID(1), new GameID(1));
        gameID.hashCode();
        AttackAction attackAction1 = new AttackAction();
        attackAction1.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        attackAction1.getFrom();
        attackAction1.setFrom("a1");
        attackAction1.getTo();
        attackAction1.setTo("b1");
        attackAction1.getUnits();
        attackAction1.setUnits(new ArrayList<>());
        chooseJoinGameAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        chooseJoinGameAction.setGameID(new GameID(1));
        chooseSwitchGameAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        chooseSwitchGameAction.getGameID();
        CommitAction commitAction = new CommitAction();
        commitAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        DeployAction deployAction1 = new DeployAction();
        deployAction1.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        deployAction.setTo("a1");
        deployAction.getTo();
        deployAction.setDeployUnits(3);
        JoinAction joinAction = new JoinAction();
        joinAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        loginAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        loginAction.getEnterAccount();
        loginAction.getEnterPassword();

        LogoutAction logoutAction = new LogoutAction();
        logoutAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        MoveAction moveAction1 = new MoveAction();
        moveAction1.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        moveAction.getFrom();
        moveAction.setFrom("a1");
        moveAction.getTo();
        moveAction.setTo("a1");
        moveAction.getUnits();
        moveAction.setUnits(new ArrayList<>());
        newGameAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        newGameAction.getNumOfPlayer();
        signUpAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        signUpAction.getAccount();
        signUpAction.getPassword();
        SwitchGameAction switchGameAction = new SwitchGameAction();
        switchGameAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        UpgradeUnitsAction upgradeUnitsAction1 = new UpgradeUnitsAction();
        upgradeUnitsAction1.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        UpgradeUnitsAction upgradeUnitsAction2 = new UpgradeUnitsAction();
        upgradeUnitsAction2.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        upgradeUnitsAction.getOldLevel();
        upgradeUnitsAction.setOldLevel(1);
        upgradeUnitsAction.getNewLevel();
        upgradeUnitsAction.setNewLevel(1);
        upgradeUnitsAction.getWhere();
        upgradeUnitsAction.setWhere("a1");
        chooseJoinGameAction.getGameID();
        deployAction.getDeployUnits();
        UpgradeTechAction upgradeTechAction = new UpgradeTechAction();
        upgradeTechAction.accept(new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {

            }

            @Override
            public void visit(CommitAction commitAction) {

            }

            @Override
            public void visit(DeployAction deployAction) {

            }

            @Override
            public void visit(JoinAction joinAction) {

            }

            @Override
            public void visit(LoginAction loginAction) {

            }

            @Override
            public void visit(LogoutAction logoutAction) {

            }

            @Override
            public void visit(MoveAction moveAction) {

            }

            @Override
            public void visit(NewGameAction newGameAction) {

            }

            @Override
            public void visit(SignUpAction signUpAction) {

            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {

            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {

            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {

            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {

            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {

            }
        });
        RSPChooseJoinGameSuccess rspChooseJoinGameSuccess1 = new RSPChooseJoinGameSuccess(new ClientPlayerPacket(new GameID(1),new AccountID("123"),3,3,3,3,3,new HashMap<>(),new HashMap<>(),false,false));
        ClientPlayerPacket clientPlayerPacket1 = new ClientPlayerPacket(new GameID(1),new AccountID("123"),3,3,3,3,3,new HashMap<>(),new HashMap<>(),false,false);
        RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess1 = new RSPChooseSwitchGameSuccess(clientPlayerPacket1);
        RSPCommitSuccess rspCommitSuccess1 = new RSPCommitSuccess(clientPlayerPacket1);
        rspLoginSuccess.accept(new ResponseVisitor() {
            @Override
            public void visit(RSPMoveSuccess rspMoveSuccess) {

            }

            @Override
            public void visit(RSPLoginSuccess rspLoginSuccess) {

            }
        });
        RSPNewGameSuccess rspNewGameSuccess1 = new RSPNewGameSuccess(clientPlayerPacket);
        rspOpenGameList.getGameIDArrayList();
        assertEquals(rspOpenGameList,rspOpenGameList);
        RSPSwitchGameList rspSwitchGameList = new RSPSwitchGameList(new ArrayList<>());
        rspSwitchGameList.getGameIDArrayList();
        assertEquals(rspSwitchGameList,rspSwitchGameList);
        rspUpgradeTechSuccess.setTechCost(3);
        rspUpgradeUnitsSuccess.setWhere("a1");
        rspUpgradeUnitsSuccess.setOldLevel(1);
        rspUpgradeUnitsSuccess.setNewLevel(1);
        rspUpgradeUnitsSuccess.setTechCost(1);
    }
}
