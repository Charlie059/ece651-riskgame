package edu.duke.ece651.shared;

import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    public Map createMap(){
        Map m = new Map(3);
        //assign territory ownership
        for(String terrName: m.getGroups().get(0)){
            m.getTerritoryList().get(terrName).setOwner(new AccountID("acc1"));
        }
        for(String terrName: m.getGroups().get(1)){
            m.getTerritoryList().get(terrName).setOwner(new AccountID("acc2"));
        }
        for(String terrName: m.getGroups().get(2)){
            m.getTerritoryList().get(terrName).setOwner(new AccountID("acc3"));
        }
        return m;
    }

    public Player createPlayer(){
        Map m = createMap();
        Player p = new Player(new AccountID("acc1"),
                new GameID(123),
                m);
        p.assignMyTerritories();
        assertEquals(p.getTechResource(), 100);
        assertEquals(p.getCurrentGameID(), 123);
        assertEquals(p.getMyTerritories().size(), 3);
        assertEquals(p.getWholeMap().numOfPlayers, 3);
        return p;
    }

    @Test
    void doDeploy() {
        Player p = createPlayer();
        assertEquals(p.getTotalDeployment(), 9);
        p.doDeploy("a1", 3);
        assertEquals(p.getTotalDeployment(), 6);
        assertEquals(p.getMyTerritories().get("a1").getUnits().get(0).getValue(), 3);
    }

    @Test
    void doMove() {
        Player p = createPlayer();
        p.doDeploy("a1", 4);
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        ArrayList<Integer> a = new ArrayList<>();
        a.add(0); //level
        a.add(3); //value
        arr.add(a);
        p.doMove("a1", "a2", arr, 90);
        assertEquals(p.getMyTerritories().get("a2").getUnits().get(0).getValue(), 3);
        assertEquals(p.getMyTerritories().get("a1").getUnits().get(0).getValue(), 1);
        assertEquals(p.getFoodResource(), 10);
    }

    @Test
    void doUpgradeTech() {
        Player p = createPlayer();
        p.doDeploy("a1", 4);
        assertEquals(p.getTechResource(), 100);
        p.setUpgradeTech(50);
        assertEquals(p.getTechResource(), 50);

    }

    @Test
    void doUpgradeUnit() {
        Player p = createPlayer();
        p.doDeploy("a1", 4);
        p.setUpgradeTech(50);
        p.DoUpgradeUnit("a1", 0, 2, 11);
        assertEquals(p.getTechResource(), 39);
        assertEquals(p.getMyTerritories().get("a1").getUnits().get(0).getValue(), 3);
        assertEquals(p.getMyTerritories().get("a1").getUnits().get(2).getValue(), 1);
    }


}