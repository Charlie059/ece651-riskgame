package edu.duke.ece651.shared.IO;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Visitor.RSPVisitor;
import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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

    }
}
