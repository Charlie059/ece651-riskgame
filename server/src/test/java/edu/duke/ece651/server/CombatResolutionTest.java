package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.IO.ClientActions.AttackAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Territory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CombatResolutionTest {
    /**
     * Create GameController AccountHashMap
     *
     * @return AccountHashMap defalut Account:abcde Password:12345
     */
    private AccountHashMap createAccountHashMap() {
        AccountHashMap accountHashMap = new AccountHashMap();
        Account account = new Account();
        account.setPassword("12345");
        accountHashMap.put(new AccountID("abcde"), account);
        return accountHashMap;
    }

    /**
     * Create GameController GameHashMap
     *
     * @return GameHashMap default GameID:1 numOfPlayer: 5
     */
    private GameHashMap createGameHashMap() {
        Game game = new Game(5);
        GameHashMap gameHashMap = new GameHashMap();
        gameHashMap.put(new GameID(1), game);
        return gameHashMap;
    }

    @Test
    public void test_PadAndSort_3continue(){
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();

        //New AttackAction
        AttackAction attackAction = new AttackAction();
        ArrayList<Integer> level0attack = new ArrayList<>();
        level0attack.add(0);//level
        level0attack.add(3);//num
        ArrayList<Integer> level1attack = new ArrayList<>();
        level1attack.add(1);
        level1attack.add(2);
        ArrayList<Integer> level4attack1 = new ArrayList<>();
        level4attack1.add(4);
        level4attack1.add(1);
        ArrayList<Integer> level4attack2 = new ArrayList<>();
        level4attack2.add(4);
        level4attack2.add(2);
        ArrayList<Integer> level4attack3 = new ArrayList<>();
        level4attack3.add(4);
        level4attack3.add(3);


        ArrayList<ArrayList<Integer>> units = new ArrayList<>();
        units.add(level0attack);
        units.add(level1attack);
        units.add(level4attack1);
        units.add(level4attack2);
        units.add(level4attack3);

        CombatResolution combatResolution = new CombatResolution(gameHashMap,new GameID(1));
        combatResolution.singlePadMergeAndSort(units);
        //Expected Pad and Sort Result
        //[[6,0],[5,0],[4,6],[3,0],[2,0],[1,2],[0,3]]
        assertEquals(6,combatResolution.getTemp_currUnitList().get(0).get(0));
        assertEquals(5,combatResolution.getTemp_currUnitList().get(1).get(0));
        assertEquals(4,combatResolution.getTemp_currUnitList().get(2).get(0));
        assertEquals(6,combatResolution.getTemp_currUnitList().get(2).get(1));
        assertEquals(3,combatResolution.getTemp_currUnitList().get(3).get(0));
        assertEquals(2,combatResolution.getTemp_currUnitList().get(4).get(0));
        assertEquals(1,combatResolution.getTemp_currUnitList().get(5).get(0));
        assertEquals(2,combatResolution.getTemp_currUnitList().get(5).get(1));
        assertEquals(0,combatResolution.getTemp_currUnitList().get(6).get(0));
        assertEquals(3,combatResolution.getTemp_currUnitList().get(6).get(1));
    }

    @Test
    public void test_PadAndSort_2Continue(){
        //new GameHashMap
        GameHashMap gameHashMap = this.createGameHashMap();
        //new AccountHashMap
        AccountHashMap accountHashMap = this.createAccountHashMap();

        //New AttackAction
        AttackAction attackAction = new AttackAction();
        ArrayList<Integer> level0attack = new ArrayList<>();
        level0attack.add(0);//level
        level0attack.add(3);//num
        ArrayList<Integer> level1attack = new ArrayList<>();
        level1attack.add(1);
        level1attack.add(2);
        ArrayList<Integer> level4attack1 = new ArrayList<>();
        level4attack1.add(4);
        level4attack1.add(1);
        ArrayList<Integer> level4attack2 = new ArrayList<>();
        level4attack2.add(4);
        level4attack2.add(2);


        ArrayList<ArrayList<Integer>> units = new ArrayList<>();
        units.add(level0attack);
        units.add(level1attack);
        units.add(level4attack1);
        units.add(level4attack2);

        CombatResolution combatResolution = new CombatResolution(gameHashMap,new GameID(1));
        combatResolution.singlePadMergeAndSort(units);
        //Expected Pad and Sort Result
        //[[6,0],[5,0],[4,3],[3,0],[2,0],[1,2],[0,3]]
        assertEquals(6,combatResolution.getTemp_currUnitList().get(0).get(0));
        assertEquals(5,combatResolution.getTemp_currUnitList().get(1).get(0));
        assertEquals(4,combatResolution.getTemp_currUnitList().get(2).get(0));
        assertEquals(3,combatResolution.getTemp_currUnitList().get(2).get(1));
        assertEquals(3,combatResolution.getTemp_currUnitList().get(3).get(0));
        assertEquals(2,combatResolution.getTemp_currUnitList().get(4).get(0));
        assertEquals(1,combatResolution.getTemp_currUnitList().get(5).get(0));
        assertEquals(2,combatResolution.getTemp_currUnitList().get(5).get(1));
        assertEquals(0,combatResolution.getTemp_currUnitList().get(6).get(0));
        assertEquals(3,combatResolution.getTemp_currUnitList().get(6).get(1));
    }

    @Test
    public void test_combatResolution(){
        HashMap<AccountID,ArrayList<AttackAction>> attackActionHashMap = new HashMap<>();
        AttackAction attackAction = new AttackAction();
        ArrayList<Integer> level0attack = new ArrayList<>();
        level0attack.add(0);//level
        level0attack.add(3);//num
        ArrayList<Integer> level1attack = new ArrayList<>();
        level1attack.add(1);
        level1attack.add(2);
        ArrayList<Integer> level4attack1 = new ArrayList<>();
        level4attack1.add(4);
        level4attack1.add(1);
        ArrayList<Integer> level4attack2 = new ArrayList<>();
        level4attack2.add(4);
        level4attack2.add(2);
        ArrayList<ArrayList<Integer>> units = new ArrayList<>();
        units.add(level0attack);
        units.add(level1attack);
        units.add(level4attack1);
        units.add(level4attack2);
        attackAction.setUnits(units).setFrom("a1").setTo("b1");
        ArrayList<AttackAction> attackActionArrayList = new ArrayList<>();
        attackActionArrayList.add(attackAction);
        attackAction.setUnits(units).setFrom("a3").setTo("b1");
        attackActionArrayList.add(attackAction);


        GameHashMap gameHashMap = new GameHashMap();
        Game game = new Game(3);
        Map m = game.getMap();
        //manualy assign accountID to territory
        Territory t_b1 = m.getTerritoryList().get("b1");
        t_b1.setOwner(new AccountID("Drew"));
        //add units to territory b1
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        ArrayList<Integer> a = new ArrayList<>();
        //add 1 lv2 units
        a.add(2);
        a.add(1);
        arr.add(a);
        //add 2 lv4 units
        a = new ArrayList<>();
        a.add(4);
        a.add(2);
        arr.add(a);
        t_b1.addUnitMultiLevels(arr);

        game.getAttackHashMap().put(new AccountID("Eric"),attackActionArrayList);
        gameHashMap.put(new GameID(1),game);

        CombatResolution combatResolution = new CombatResolution(gameHashMap,new GameID(1));
        combatResolution.combatResolution(-1); //defender always wins

        assertEquals(new AccountID("Drew"), t_b1.getOwnerId());
        assertEquals(1, t_b1.getUnits().get(2).getValue());
        assertEquals(2, t_b1.getUnits().get(4).getValue());

        HashMap<String, ArrayList<ArrayList<Integer>>> result = combatResolution.getAttackUnitListHashMap();
        assertEquals(result.get("b1").get(0).get(0),6);
        assertEquals(result.get("b1").get(2).get(0),4);
        assertEquals(result.get("b1").get(2).get(1),0);
        assertEquals(result.get("b1").get(5).get(1),0);
    }

//    @Test
//    public void test_combatResolution_AtkWin(){
//        HashMap<AccountID,ArrayList<AttackAction>> attackActionHashMap = new HashMap<>();
//        AttackAction attackAction = new AttackAction();
//        ArrayList<Integer> level0attack = new ArrayList<>();
//        level0attack.add(0);//level
//        level0attack.add(3);//num
//        ArrayList<Integer> level1attack = new ArrayList<>();
//        level1attack.add(1);
//        level1attack.add(2);
//        ArrayList<Integer> level4attack1 = new ArrayList<>();
//        level4attack1.add(4);
//        level4attack1.add(1);
//        ArrayList<Integer> level4attack2 = new ArrayList<>();
//        level4attack2.add(4);
//        level4attack2.add(2);
//        ArrayList<ArrayList<Integer>> units = new ArrayList<>();
//        units.add(level0attack);
//        units.add(level1attack);
//        units.add(level4attack1);
//        units.add(level4attack2);
//        attackAction.setUnits(units).setFrom("a1").setTo("b1");
//        ArrayList<AttackAction> attackActionArrayList = new ArrayList<>();
//        attackActionArrayList.add(attackAction);
//        attackAction.setUnits(units).setFrom("a3").setTo("b1");
//        attackActionArrayList.add(attackAction);
//
//        GameHashMap gameHashMap = new GameHashMap();
//        Game game = new Game(3);
//        Map m = game.getMap();
//        //manualy assign accountID to territory
//        Territory t_b1 = m.getTerritoryList().get("b1");
//        t_b1.setOwner(new AccountID("Tenshi Drew"));
//        //add units to territory b1
//        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
//        ArrayList<Integer> a = new ArrayList<>();
//        //add 1 lv2 units
//        a.add(2);
//        a.add(1);
//        arr.add(a);
//        //add 2 lv4 units
//        a = new ArrayList<>();
//        a.add(4);
//        a.add(2);
//        arr.add(a);
//        t_b1.addUnitMultiLevels(arr);
//
//        game.getAttackHashMap().put(new AccountID("Akuma Drew"),attackActionArrayList);
//        gameHashMap.put(new GameID(1),game);
//
//        CombatResolution combatResolution = new CombatResolution(gameHashMap,new GameID(1));
//        combatResolution.combatResolution(1); //Attacker always wins
//        //expected attackerUnits after attack: ((0, 6) (1, 4) (2, 0) (3, 0) (4, 6) (5, 0) (6, 0))
//        assertEquals(new AccountID("Akuma Drew"), t_b1.getOwnerId());
//        for(int i = 0; i< t_b1.getUnits().size(); i++){
//            System.out.println("Level: "+ t_b1.getUnits().get(i).getLevel() + ", Value: "+ t_b1.getUnits().get(i).getValue());
//        }
//        assertEquals(6, t_b1.getUnits().get(0).getValue());
//        assertEquals(4, t_b1.getUnits().get(1).getValue());
//        assertEquals(6, t_b1.getUnits().get(4).getValue());
//        assertEquals(0, t_b1.getUnits().get(6).getValue());
//
//        HashMap<String, ArrayList<ArrayList<Integer>>> result = combatResolution.getAttackUnitListHashMap();
//        assertEquals(result.get("b1").get(0).get(0),6);
//        assertEquals(result.get("b1").get(2).get(0),4);
//        assertEquals(result.get("b1").get(2).get(1),6);
//        assertEquals(result.get("b1").get(5).get(1),4);
//    }

//    @Test
//    public void test_combatResolution_random(){
//        HashMap<AccountID,ArrayList<AttackAction>> attackActionHashMap = new HashMap<>();
//        AttackAction attackAction = new AttackAction();
//        ArrayList<Integer> level0attack = new ArrayList<>();
//        level0attack.add(0);//level
//        level0attack.add(3);//num
//        ArrayList<Integer> level1attack = new ArrayList<>();
//        level1attack.add(1);
//        level1attack.add(2);
//        ArrayList<Integer> level4attack1 = new ArrayList<>();
//        level4attack1.add(4);
//        level4attack1.add(1);
//        ArrayList<Integer> level4attack2 = new ArrayList<>();
//        level4attack2.add(4);
//        level4attack2.add(2);
//
//        ArrayList<ArrayList<Integer>> units = new ArrayList<>();
//        units.add(level0attack);
//        units.add(level1attack);
//        units.add(level4attack1);
//        units.add(level4attack2);
//
//        attackAction.setUnits(units).setFrom("a1").setTo("b1");
//        ArrayList<AttackAction> attackActionArrayList = new ArrayList<>();
//        attackActionArrayList.add(attackAction);
//        attackAction.setUnits(units).setFrom("a3").setTo("b1");
//        attackActionArrayList.add(attackAction);
//
//        GameHashMap gameHashMap = new GameHashMap();
//        Game game = new Game(3);
//        Map m = game.getMap();
//        //manualy assign accountID to territory
//        Territory t_b1 = m.getTerritoryList().get("b1");
//        t_b1.setOwner(new AccountID("Tenshi Drew"));
//        //add units to territory b1
//        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
//        ArrayList<Integer> a = new ArrayList<>();
//        //add 1 lv2 units
//        a.add(2);
//        a.add(1);
//        arr.add(a);
//        //add 2 lv4 units
//        a = new ArrayList<>();
//        a.add(4);
//        a.add(2);
//        arr.add(a);
//        //add 3 lv5 units
//        a = new ArrayList<>();
//        a.add(5);
//        a.add(3);
//        arr.add(a);
//        //add 1 lv6 units
//        a = new ArrayList<>();
//        a.add(6);
//        a.add(1);
//        arr.add(a);
//        t_b1.addUnitMultiLevels(arr);
//
//        game.getAttackHashMap().put(new AccountID("Akuma Drew"),attackActionArrayList);
//        gameHashMap.put(new GameID(1),game);
//
//        CombatResolution combatResolution = new CombatResolution(gameHashMap,new GameID(1));
//        combatResolution.combatResolution(0); //random
//        //attackerUnits before attack: ((0, 6) (1, 4) (2, 0) (3, 0) (4, 6) (5, 0) (6, 0))
//        //defenderUnits before attack: ((0, 0) (1, 0) (2, 1) (3, 0) (4, 2) (5, 3) (6, 1))
//
//        System.out.println("Owner: " + t_b1.getOwnerId().getAccountID());
//        for(int i = 0; i< t_b1.getUnits().size(); i++){
//            System.out.println("Level: "+ t_b1.getUnits().get(i).getLevel() + ", Value: "+ t_b1.getUnits().get(i).getValue());
//        }
//
//    }

}