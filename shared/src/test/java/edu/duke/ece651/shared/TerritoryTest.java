package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TerritoryTest {
    @Test
    void testGetName() {
        Territory t = new Territory("123");
        assertTrue(t.getName().equals("123"));
        ArrayList<Territory> neigh = new ArrayList<>();
        Territory t1 = new Territory("123");
        neigh.add(t1);
        Territory t2 = new Territory("a1", neigh);
        assertEquals(t2.getNeighbour().size(), 1);
    }

    @Test
    void testGetOwnerId() {
        ArrayList<Territory> list = new ArrayList<>();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        Territory t3 = new Territory("3");
        list.add(t1);
        list.add(t2);
        list.add(t3);
        System.out.println(t1.getName() + " " + t1.getOwnerId());
        System.out.println(t2.getName() + " " + t2.getOwnerId());
        System.out.println(t3.getName() + " " + t3.getOwnerId());

        for (int i = 0; i < 3; i++) {
            list.get(i).setOwner(i+1);
        }
        System.out.println(t1.getName() + " " + t1.getOwnerId());
        System.out.println(t2.getName() + " " + t2.getOwnerId());
        System.out.println(t3.getName() + " " + t3.getOwnerId());
    }


    @Test
    void addNeighbour() {
        Territory t = new Territory("a1");
        Territory t2 = new Territory("a2");
        t.addNeighbour(t2);
        assertEquals(t.getNeighbour().get(0), t2);
    }


    @Test
    void setOwner() {
        Territory t = new Territory("a1");
        t.setOwner(1);
        assertEquals(t.getOwnerId(), 1);
    }

    @Test
    void getUnits() {
        Territory t = new Territory("a1");
        assertTrue(t.addUnitLevel(0,1, t.getUnits()));
        assertEquals(t.getUnits().size(), 7);
    }

    private Territory createTerritoryWUnits(){
        Territory t = new Territory("a1");
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        arr.add(new ArrayList<>());//0
        arr.add(new ArrayList<>()); // 1
        arr.get(0).add(1);//level
        arr.get(0).add(2);//value
        arr.get(1).add(0);//level
        arr.get(1).add(3);//value
        t.addUnitMultiLevels(arr);
        return t;
    }

    @Test
    void removeUnit() {
        Territory t = createTerritoryWUnits();
        ArrayList<ArrayList<Integer>> arr2 = new ArrayList<>();
        arr2.add(new ArrayList<>());//0
        arr2.add(new ArrayList<>()); // 1
        arr2.get(0).add(1);//level
        arr2.get(0).add(2);//value
        arr2.get(1).add(0);//level
        arr2.get(1).add(3);//value
        assertTrue(t.removeUnitMultiLevels(arr2));
        assertEquals(t.getUnits().get(0).getValue(), 0);
    }

    @Test
    void removeErr(){
        Territory t = createTerritoryWUnits();
        assertFalse(t.removeUnitLevel(6, 5, t.getUnits()));
        ArrayList<ArrayList<Integer>> arr2 = new ArrayList<>();
        arr2.add(new ArrayList<>());//0
        arr2.add(new ArrayList<>()); // 1
        arr2.get(0).add(8);//level
        arr2.get(0).add(2);//value
        arr2.get(1).add(0);//level
        arr2.get(1).add(3);//value
        assertFalse(t.removeUnitMultiLevels(arr2));

    }

    @Test
    void addErr(){
        Territory t = createTerritoryWUnits();
        assertFalse(t.addUnitLevel(-1, 5, t.getUnits()));
        ArrayList<ArrayList<Integer>> arr2 = new ArrayList<>();
        arr2.add(new ArrayList<>());//0
        arr2.add(new ArrayList<>()); // 1
        arr2.get(0).add(8);//level
        arr2.get(0).add(2);//value
        arr2.get(1).add(0);//level
        arr2.get(1).add(3);//value
        assertFalse(t.addUnitMultiLevels(arr2));

    }

    @Test
    void changeOwner() {
        Territory t = new Territory("a1");
        t.setOwner(1);
        t.changeOwner(2);
        assertEquals(t.getOwnerId(), 2);
    }
}
