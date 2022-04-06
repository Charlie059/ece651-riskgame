package edu.duke.ece651.shared.map;

import edu.duke.ece651.shared.Wrapper.AccountID;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TerritoryTest {
    @Test
    void createTerr(){
        Territory t1 = new Territory("a1");
        //default settings
        assertEquals(t1.getOwnerId(), null);
        assertEquals(t1.getUnits().size(), 7);
        assertEquals(t1.getCost(), 10);
        assertEquals(t1.getDist(), Integer.MAX_VALUE);
        assertNull(t1.getPrev());
        //add settings
        //set account ID
        AccountID acc = new AccountID("1");
        t1.setOwner(acc);
        assertEquals(t1.getOwnerId(), acc);
        //set cost
        t1.setCost(30);
        assertEquals(t1.getCost(), 30);
        t1.setDist(0);
        //set units
        //add 3 level-0 units
        //add 3 level-1 units
        t1.addUnitLevel( 0, 3, t1.getUnits());
        t1.addUnitLevel(1, 3, t1.getUnits());
        assertEquals(t1.getUnits().get(0).getValue(), 3);
        assertEquals(t1.getUnits().get(1).getValue(), 3);
        //add more 3 level-1 units
        t1.addUnitLevel(1, 3, t1.getUnits());
        assertEquals(t1.getUnits().get(1).getValue(), 6);
        //remove 2 level-0 units
        t1.removeUnitLevel(0, 2, t1.getUnits());
        assertEquals(t1.getUnits().get(0).getValue(), 1);

        //test constructor with neighbor list
        Territory t2 = createTerritory();
        ArrayList<Territory> terrs = new ArrayList<>();
        terrs.add(t2);
        terrs.add(t1);
        Territory t3 = new Territory("a3", terrs);
        assertEquals(2, t3.getNeighbour().size());
        t1.setPrev("a3");
        t1.addNeighbour(t2);
        assertEquals(1, t1.getNeighbour().size());
        //change name
        assertEquals("a1", t1.getName());
        t2.setDist(10);
        assertEquals(-10, t1.compareTo(t2));
    }


     public Territory createTerritory(){
        Territory t1 = new Territory("a1", 30);
        //add settings
        //set account ID
        AccountID acc = new AccountID("1");
        t1.setOwner(acc);
        //set units
        //add 3 level-0 units
        //add 3 level-1 units
        t1.addUnitLevel( 0, 3, t1.getUnits());
        t1.addUnitLevel(1, 3, t1.getUnits());
        return t1;
    }

    @Test
    void testAddUnitsHashMap(){
        Territory t1 = createTerritory();
        //add 3 level-0 units
        //add 3 level-1 units
        //add 2 level-0, 2 level-1, 2 level-2
        ArrayList<ArrayList<Integer>> arr= new ArrayList<>();
        ArrayList<Integer> a0 = new ArrayList<>();
        a0.add(0);
        a0.add(2);
        ArrayList<Integer> a1 = new ArrayList<>();
        a1.add(1);
        a1.add(2);
        ArrayList<Integer> a2 = new ArrayList<>();
        a2.add(2);
        a2.add(2);
        arr.add(a0);
        arr.add(a1);
        arr.add(a2);
        t1.addUnitMultiLevels(arr);
        assertEquals(t1.getUnits().get(0).getValue(), 5);
        assertEquals(t1.getUnits().get(1).getValue(), 5);
        assertEquals(t1.getUnits().get(2).getValue(), 2);

        //add hashmap
        HashMap<Integer, Integer> h = new HashMap<>();
        h.put(0, 4);
        h.put(1, 4);
        h.put(4, 4);
        t1.addUnitMultiLevelsHashMap(h);
        assertEquals(t1.getUnits().get(0).getValue(), 4);
        assertEquals(t1.getUnits().get(1).getValue(), 4);
        assertEquals(t1.getUnits().get(4).getValue(), 4);
        assertEquals(t1.getUnits().get(2).getValue(), 0);

        assertFalse(t1.isEmpty());
        HashMap<Integer, Integer> h2 = new HashMap<>();
        h2.put(0, 0);
        h2.put(1, 0);
        h2.put(2, 0);
        h2.put(3, 0);
        h2.put(4, 0);
        h2.put(5, 0);
        h2.put(6, 0);
        t1.addUnitMultiLevelsHashMap(h2);
        assertTrue(t1.isEmpty());

    }

    @Test
    void testRemoveMultiLevel(){
        Territory t1 = createTerritory();
        //add 3 level-0 units
        //add 3 level-1 units
        //remove 2 level-0, 2 level-1
        ArrayList<ArrayList<Integer>> arr= new ArrayList<>();
        ArrayList<Integer> a0 = new ArrayList<>();
        a0.add(0);
        a0.add(2);
        ArrayList<Integer> a1 = new ArrayList<>();
        a1.add(1);
        a1.add(2);
        arr.add(a0);
        arr.add(a1);
        assertTrue(t1.removeUnitMultiLevels(arr));
        assertEquals(t1.getUnits().get(0).getValue(), 1);
        assertEquals(t1.getUnits().get(1).getValue(), 1);
        assertFalse(t1.removeUnitMultiLevels(arr));

    }

    @Test
    void testInvalidRMUnits(){
        Territory t1 = createTerritory();
        //add 3 level-0 units
        //add 3 level-1 units
        assertFalse(t1.removeUnitLevel(-1, 4, t1.getUnits()));
        assertFalse(t1.removeUnitLevel(0, 4, t1.getUnits()));
        assertEquals(0, t1.getUnits().get(0).getValue());
    }

    @Test
    void testInvalidAddUnits(){
        Territory t1 = createTerritory();
        //add 3 level-0 units
        //add 3 level-1 units
        assertFalse(t1.addUnitLevel(-1, 4, t1.getUnits()));
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        ArrayList<Integer> a0 = new ArrayList<>();
        a0.add(-1);
        a0.add(2);
        arr.add(a0);
        assertFalse(t1.addUnitMultiLevels(arr));
    }

    @Test
    void changeOwner(){
        Territory t1 = createTerritory();
        t1.changeOwner(new AccountID("2"));
        assertEquals(t1.getOwnerId(),new AccountID("2"));
    }


}