package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.IO.ClientActions.BombardmentAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Spy;
import edu.duke.ece651.shared.map.Territory;
import edu.duke.ece651.shared.map.Unit;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ClientPlayerPacketTest {

    @Test
    void testALL() {


        HashMap<String, Territory> myTerr = new HashMap<>();
        Territory territory1 = new Territory("b1");
        Territory territory2 = new Territory("b2");
        Territory territory3 = new Territory("b3");
        myTerr.put("b1", territory1);
        myTerr.put("b2", territory2);
        myTerr.put("b3", territory3);

        HashMap<AccountID, HashMap<String, ArrayList<Integer>>> enemyTerritoriesV2 = new HashMap<>();

        HashMap<String, ArrayList<Integer>> terr = new HashMap<>();

        ArrayList<Integer> enemyTerrUnits1 = new ArrayList<>();
        enemyTerrUnits1.add(7);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);

        ArrayList<Integer> enemyTerrUnits2 = new ArrayList<>();
        enemyTerrUnits2.add(1);
        enemyTerrUnits2.add(4);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);

        terr.put("a1", enemyTerrUnits1);
        terr.put("a2", enemyTerrUnits2);
        enemyTerritoriesV2.put(new AccountID("p1"), terr);

        HashMap<String, ArrayList<Spy>> spyInfo = new HashMap<>();
        ArrayList<Spy> spyArrayList = new ArrayList<>();
        spyArrayList.add(new Spy(new AccountID("p2")));
        spyInfo.put("a1", spyArrayList);

        ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(new GameID(1), new AccountID("abc"), 2, 100, 100, 2, 9, myTerr, new HashMap<String, ArrayList<String>>(), false, false, enemyTerritoriesV2, spyInfo);
        clientPlayerPacket.getEnemyTerritoriesV2();
        clientPlayerPacket.getSpyInfo();
        clientPlayerPacket.getMyTerritories();
        clientPlayerPacket.getNumOfPlayers();
        clientPlayerPacket.getMyTerritories();
        clientPlayerPacket.getCurrentGameID();
        clientPlayerPacket.getAccountID();
        clientPlayerPacket.getFoodResource();
        clientPlayerPacket.getTotalDeployment();
        clientPlayerPacket.getTechResource();
        clientPlayerPacket.getTechLevel();
        clientPlayerPacket.getEnemyTerritories();
        clientPlayerPacket.getLose();
        clientPlayerPacket.getWin();

        RSPCloakTerritorySuccess rspCloakTerritorySuccess = new RSPCloakTerritorySuccess();
        RSPDeploySuccess rspDeploySuccess = new RSPDeploySuccess();

        RSPGodBeWithUSuccess rspGodBeWithUSuccess = new RSPGodBeWithUSuccess();
        RSPLoginSuccess rspLoginSuccess = new RSPLoginSuccess();
        RSPLogoutSuccess rspLogoutSuccess = new RSPLogoutSuccess();
        RSPDeployFail rspDeployFail = new RSPDeployFail();

        RSPDeployFail rspDeployFail1= new RSPDeployFail("a");

        RSPAttackFail rspAttackFail = new RSPAttackFail();
        RSPAttackFail rspAttackFail1 = new RSPAttackFail("111");
        ArrayList<ArrayList<Integer>> units = new ArrayList<>();
        RSPAttackSuccess rspAttackSuccess = new RSPAttackSuccess("a1","b1",units,1);
        rspAttackSuccess.getFrom();
        rspAttackSuccess.getTo();
        rspAttackSuccess.getUnits();
        rspAttackSuccess.getTotalCost();
        rspAttackSuccess.setFrom("a1");
        rspAttackSuccess.setTo("b1");
        rspAttackSuccess.setTotalCost(1);
        rspAttackSuccess.setUnits(units);

        RSPBombardmentFail rspBombardmentFail = new RSPBombardmentFail();
        RSPBombardmentFail rspBombardmentFail1 = new RSPBombardmentFail("1");


        RSPBombardmentSuccess rspBombardmentSuccess = new RSPBombardmentSuccess("a1",null);
        rspBombardmentSuccess.getEnemyTerritory();
        rspBombardmentSuccess.getUnits();


        RSPCardBuyFail rspCardBuyFail = new RSPCardBuyFail();
        RSPCardBuyFail rspCardBuyFail1 = new RSPCardBuyFail("1");

        RSPCardBuySuccess rspCardBuySuccess = new RSPCardBuySuccess(1,1);
        rspCardBuySuccess.getCardCost();
        rspCardBuySuccess.getCardType();

        RSPChooseJoinGameSuccess rspChooseJoinGameSuccess = new RSPChooseJoinGameSuccess();
        rspChooseJoinGameSuccess.getClientPlayerPacket();

        RSPChooseJoinGameSuccess rspChooseJoinGameSuccess1 = new RSPChooseJoinGameSuccess(clientPlayerPacket);
        rspChooseJoinGameSuccess1.getClientPlayerPacket();

        RSPChooseSwitchGameFail rspChooseSwitchGameFail = new RSPChooseSwitchGameFail();
        RSPChooseSwitchGameFail rspChooseSwitchGameFail1 = new RSPChooseSwitchGameFail("1");

        RSPChooseJoinGameFail rspChooseJoinGameFail = new RSPChooseJoinGameFail();
        RSPChooseJoinGameFail rspChooseJoinGameFail1 = new RSPChooseJoinGameFail("1");

        RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess = new RSPChooseSwitchGameSuccess();
        RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess1 = new RSPChooseSwitchGameSuccess(clientPlayerPacket);
        rspChooseSwitchGameSuccess1.getClientPlayerPacket();

        RSPCloakTerritoryActionFail rspCloakTerritoryActionFail = new RSPCloakTerritoryActionFail("1");
        RSPCloakTerritoryActionFail rspCloakTerritoryActionFail1 = new RSPCloakTerritoryActionFail();


        RSPCommitFail rspCommitFail = new RSPCommitFail("1");
        RSPCommitFail rspCommitFail1 = new RSPCommitFail();

        RSPCommitSuccess rspCommitSuccess = new RSPCommitSuccess(clientPlayerPacket);
        RSPCommitSuccess rspCommitSuccess1 = new RSPCommitSuccess();
        rspCommitSuccess.getClientPlayerPacket();



        RSPDeployFail rspDeployFail2 = new RSPDeployFail();

        RSPGodBeWithUFail rspGodBeWithUFail = new RSPGodBeWithUFail("1");
        RSPGodBeWithUFail rspGodBeWithUFail1 = new RSPGodBeWithUFail();

        RSPGreatLeapForwardFail rspGreatLeapForwardFail = new RSPGreatLeapForwardFail("1");
        RSPGreatLeapForwardFail rspGreatLeapForwardFail1 = new RSPGreatLeapForwardFail();


        ArrayList<Unit> units1 = new ArrayList<>();

        RSPGreatLeapForwardSuccess rspGreatLeapForwardSuccess = new RSPGreatLeapForwardSuccess("a1",units1);
        rspGreatLeapForwardSuccess.getUnits();
        rspGreatLeapForwardSuccess.getTerrName();


        RSPLoginFail rspLoginFail = new RSPLoginFail("1");
        RSPLoginFail rspLoginFail1 = new RSPLoginFail();


        RSPMoveFail rspMoveFail = new RSPMoveFail("1");
        RSPMoveFail rspMoveFail1 = new RSPMoveFail();

        RSPMoveSuccess rspMoveSuccess = new RSPMoveSuccess("a1","b1",null,10);
        rspMoveSuccess.getFrom();
        rspMoveSuccess.getTo();
        rspMoveSuccess.getTotalCost();
        rspMoveSuccess.getUnits();
        rspMoveSuccess.setFrom("a1");
        rspMoveSuccess.setTo("b1");
        rspMoveSuccess.setUnits(null);
        rspMoveSuccess.setTotalCost(1);


        RSPNewGameSuccess rspNewGameSuccess = new RSPNewGameSuccess();
        RSPNewGameSuccess rspNewGameSuccess1 = new RSPNewGameSuccess(clientPlayerPacket);
        rspNewGameSuccess1.getClientPlayerPacket();







        RSPUpgradeUnitsSuccess rspUpgradeUnitsSuccess = new RSPUpgradeUnitsSuccess();
        rspUpgradeUnitsSuccess.setWhere("");
        rspUpgradeUnitsSuccess.setOldLevel(0);
        rspUpgradeUnitsSuccess.setNewLevel(0);
        rspUpgradeUnitsSuccess.setTechCost(0);
        rspUpgradeUnitsSuccess.getNewLevel();
        rspUpgradeUnitsSuccess.getOldLevel();
        rspUpgradeUnitsSuccess.getTechCost();
        rspUpgradeUnitsSuccess.getWhere();

        RSPUpgradeUnitsFail rspUpgradeUnitsFail = new RSPUpgradeUnitsFail();
        rspUpgradeUnitsFail.getErrMessage();
        RSPUpgradeUnitsFail rspUpgradeUnitsFail1 = new RSPUpgradeUnitsFail("123");

        RSPUpgradeTechSuccess rspUpgradeTechSuccess = new RSPUpgradeTechSuccess();
        rspUpgradeTechSuccess.setTechCost(0);
        rspUpgradeTechSuccess.getTechCost();

        RSPUpgradeTechFail rspUpgradeTechFail = new RSPUpgradeTechFail("123");
        rspUpgradeTechFail.getErrMessage();
        RSPUpgradeTechFail rspUpgradeTechFail1 = new RSPUpgradeTechFail();

        RSPUnitDeploySuccess rspUnitDeploySuccess = new RSPUnitDeploySuccess();
        RSPUnitDeployFail rspUnitDeployFail = new RSPUnitDeployFail("123");
        rspUnitDeployFail.getErrMessage();

        ArrayList<GameID> arr = new ArrayList<>();
        RSPSwitchGameList rspSwitchGameList = new RSPSwitchGameList(arr);
        RSPSwitchGameList rspSwitchGameList1 = new RSPSwitchGameList(arr);

        rspSwitchGameList1.equals(rspSwitchGameList);

        rspSwitchGameList.getGameIDArrayList();

        RSPSpyUpgradeSuccess rspSpyUpgradeSuccess = new RSPSpyUpgradeSuccess();
        RSPSpyUpgradeFail rspSpyUpgradeFail = new RSPSpyUpgradeFail("123");
        rspSpyUpgradeFail.getErrMessage();

        RSPSpyMoveSuccess rspSpyMoveSuccess = new RSPSpyMoveSuccess();
        RSPSpyMoveFail rspSpyMoveFail = new RSPSpyMoveFail("123");
        RSPSpyMoveFail rspSpyMoveFail1 = new RSPSpyMoveFail();
        rspSpyMoveFail.getErrMessage();

        RSPSpyDeploySuccess rspSpyDeploySuccess = new RSPSpyDeploySuccess(new Spy(new AccountID("123")));
        rspSpyDeploySuccess.getSpy();
        RSPSpyDeployFail rspSpyDeployFail = new RSPSpyDeployFail("123");
        rspSpyDeployFail.getErrMessage();

        RSPSignupSuccess rspSignupSuccess = new RSPSignupSuccess();
        RSPSignupFail rspSignupFail = new RSPSignupFail("123");
        RSPSignupFail rspSignupFail1 = new RSPSignupFail();
        rspSignupFail.getErrMessage();

        RSPSanctionFail rspSanctionFail = new RSPSanctionFail("123");
        rspSanctionFail.getErrMessage();
        RSPSanctionFail rspSanctionFail1 = new RSPSanctionFail();

        RSPOpenGameList rspOpenGameList = new RSPOpenGameList(new ArrayList<GameID>(1));
        RSPSpyDeployFail rspSpyDeployFail1 = new RSPSpyDeployFail();

        rspOpenGameList.getGameIDArrayList();
        assertEquals(rspSwitchGameList,rspSwitchGameList);
        assertNotEquals(rspSwitchGameList, null);
        assertEquals(rspSwitchGameList.getClass(),rspSwitchGameList.getClass());

        RSPOpenGameList rspOpenGameList1 = new RSPOpenGameList(new ArrayList<GameID>(1));
        boolean a = rspOpenGameList1.equals(rspOpenGameList);


        RSPSpyUpgradeFail rspSpyUpgradeFail1 = new RSPSpyUpgradeFail();

        rspSpyUpgradeFail.getErrMessage();








        ArrayList<ArrayList<Integer>> moveUnits = new ArrayList<>();
        ArrayList<Integer> unit = new ArrayList<>();
        unit.add(0);
        unit.add(1);
        moveUnits.add(unit);

        clientPlayerPacket.doMove("b1","b2",moveUnits, 1);
        clientPlayerPacket.doUpgradeTech(1,1);
        clientPlayerPacket.doUpgradeUnit("b1",1,2,1);
        clientPlayerPacket.doAttack("b1",null,moveUnits,1);
        clientPlayerPacket.doDeploy("b1",1);


        RSPSanctionSuccess rspSanctionSuccess = new RSPSanctionSuccess();

    }
}