package edu.duke.ece651.shared.IO;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.GameIDCounter;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Visitor.RSPVisitor;
import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;
import edu.duke.ece651.shared.Wrapper.GameID;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

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
        ClientPlayerPacket clientPlayerPacket  = new ClientPlayerPacket(null,null,3,100,100,2,100,null,null,null,null, null, null);
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
        ActionVisitor actionVisitor = new ActionVisitor() {
            @Override
            public void visit(AttackAction attackAction) {
                return;
            }

            @Override
            public void visit(CommitAction commitAction) {
                return;

            }

            @Override
            public void visit(DeployAction deployAction) {
                return;
            }

            @Override
            public void visit(JoinAction joinAction) {
                return;
            }

            @Override
            public void visit(LoginAction loginAction) {
                return;
            }

            @Override
            public void visit(LogoutAction logoutAction) {
                return;
            }

            @Override
            public void visit(MoveAction moveAction) {
                return;
            }

            @Override
            public void visit(NewGameAction newGameAction) {
                return;
            }

            @Override
            public void visit(SignUpAction signUpAction) {
                return;
            }

            @Override
            public void visit(SwitchGameAction switchGameAction) {
                return;
            }

            @Override
            public void visit(UpgradeTechAction upgradeTechAction) {
                return;
            }

            @Override
            public void visit(UpgradeUnitsAction updateUnitsAction) {
                return;
            }

            @Override
            public void visit(ChooseJoinGameAction chooseJoinGameAction) {
                return;
            }

            @Override
            public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {
                return;
            }

            @Override
            public void visit(SpyDeployAction spyDeployAction) {
                return;
            }

            @Override
            public void visit(SpyMoveAction spyMoveAction) {
                return;
            }

            @Override
            public void visit(SpyUpgradeAction spyUpgradeAction) {
                return;
            }

            @Override
            public void visit(CloakTerritoryAction cloakTerritoryAction) {
                return;
            }

            @Override
            public void visit(CardBuyAction cardBuyAction) {
                return;
            }

            @Override
            public void visit(BombardmentAction bombardmentAction) {
                return;
            }

            @Override
            public void visit(SanctionAction sanctionAction) {
                return;
            }

            @Override
            public void visit(TheGreatLeapForwardAction theGreatLeapForwardAction) {
                return;
            }

            @Override
            public void visit(GodBeWithUAction godBeWithUAction) {
                return;
            }

            @Override
            public void visit(UnitDeployAction unitDeployAction) {
                return;
            }

            @Override
            public void visit(TestAction testAction) {
                return;
            }
        };

        attackAction1.accept(actionVisitor);
        attackAction1.getFrom();
        attackAction1.setFrom("a1");
        attackAction1.getTo();
        attackAction1.setTo("b1");
        attackAction1.getUnits();
        attackAction1.setUnits(new ArrayList<>());
        chooseJoinGameAction.accept(actionVisitor);
        chooseJoinGameAction.setGameID(new GameID(1));
        chooseSwitchGameAction.accept(actionVisitor);
        chooseSwitchGameAction.getGameID();
        CommitAction commitAction = new CommitAction();
        commitAction.accept(actionVisitor);
        DeployAction deployAction1 = new DeployAction();
        deployAction1.accept(actionVisitor);
        deployAction.setTo("a1");
        deployAction.getTo();
        deployAction.setDeployUnits(3);
        JoinAction joinAction = new JoinAction();
        joinAction.accept(actionVisitor);
        loginAction.accept(actionVisitor);
        loginAction.getEnterAccount();
        loginAction.getEnterPassword();

        LogoutAction logoutAction = new LogoutAction();
        logoutAction.accept(actionVisitor);
        MoveAction moveAction1 = new MoveAction();
        moveAction1.accept(actionVisitor);
        moveAction.getFrom();
        moveAction.setFrom("a1");
        moveAction.getTo();
        moveAction.setTo("a1");
        moveAction.getUnits();
        moveAction.setUnits(new ArrayList<>());
        newGameAction.accept(actionVisitor);
        newGameAction.getNumOfPlayer();
        signUpAction.accept(actionVisitor);
        signUpAction.getAccount();
        signUpAction.getPassword();
        SwitchGameAction switchGameAction = new SwitchGameAction();
        switchGameAction.accept(actionVisitor);
        UpgradeUnitsAction upgradeUnitsAction1 = new UpgradeUnitsAction();
        upgradeUnitsAction1.accept(actionVisitor);
        UpgradeUnitsAction upgradeUnitsAction2 = new UpgradeUnitsAction();
        upgradeUnitsAction2.accept(actionVisitor);
        upgradeUnitsAction.getOldLevel();
        upgradeUnitsAction.setOldLevel(1);
        upgradeUnitsAction.getNewLevel();
        upgradeUnitsAction.setNewLevel(1);
        upgradeUnitsAction.getWhere();
        upgradeUnitsAction.setWhere("a1");
        chooseJoinGameAction.getGameID();
        deployAction.getDeployUnits();
        UpgradeTechAction upgradeTechAction = new UpgradeTechAction();
        upgradeTechAction.accept(actionVisitor);
        RSPChooseJoinGameSuccess rspChooseJoinGameSuccess1 = new RSPChooseJoinGameSuccess(new ClientPlayerPacket(new GameID(1),new AccountID("123"),3,3,3,3,3,new HashMap<>(),new HashMap<>(),false,false, null, null));
        ClientPlayerPacket clientPlayerPacket1 = new ClientPlayerPacket(new GameID(1),new AccountID("123"),3,3,3,3,3,new HashMap<>(),new HashMap<>(),false,false, null, null);
        RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess1 = new RSPChooseSwitchGameSuccess(clientPlayerPacket1);
        RSPCommitSuccess rspCommitSuccess1 = new RSPCommitSuccess(clientPlayerPacket1);
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


        BombardmentAction bombardmentAction = new BombardmentAction("a1");
        bombardmentAction.getEnemyTerritory();
        bombardmentAction.accept(actionVisitor);

        List<Integer> cardType = new ArrayList<>();
        cardType.add(1);
        CardBuyAction cardBuyAction = new CardBuyAction(cardType);

        cardBuyAction.accept(actionVisitor);

        cardBuyAction.getCardType();

        CloakTerritoryAction cloakTerritoryAction = new CloakTerritoryAction("a1");
        cloakTerritoryAction.accept(actionVisitor);
        cloakTerritoryAction.getFrom();

        GodBeWithUAction godBeWithUAction = new GodBeWithUAction();
        godBeWithUAction.accept(actionVisitor);

        SanctionAction sanctionAction = new SanctionAction(new AccountID("a"));
        sanctionAction.getEnemyID();
        sanctionAction.accept(actionVisitor);

        SpyDeployAction spyDeployAction = new SpyDeployAction("a1","b1");
        spyDeployAction.getFrom();
        spyDeployAction.getTo();
        spyDeployAction.accept(actionVisitor);

        SpyMoveAction spyMoveAction = new SpyMoveAction(new UUID(1L,2L),"a1","b1");
        spyMoveAction.getFrom();
        spyMoveAction.getTo();
        spyMoveAction.getSpyUUID();
        spyMoveAction.accept(actionVisitor);


        SpyUpgradeAction spyUpgradeAction = new SpyUpgradeAction("a1", new UUID(1L, 1L),1);
        spyUpgradeAction.getFrom();
        spyUpgradeAction.getSpyUUID();
        spyUpgradeAction.getType();


        spyUpgradeAction.accept(actionVisitor);
        spyDeployAction.getTo();

        spyDeployAction.accept(actionVisitor);


        TestAction testAction = new TestAction(true);
        testAction.getMode();
        testAction.accept(actionVisitor);

        TheGreatLeapForwardAction theGreatLeapForwardAction = new TheGreatLeapForwardAction("a1");
        theGreatLeapForwardAction.getTerritoryName();
        theGreatLeapForwardAction.accept(actionVisitor);

        UnitDeployAction unitDeployAction = new UnitDeployAction("a1");
        unitDeployAction.getTo();
        unitDeployAction.accept(actionVisitor);
    }
}
