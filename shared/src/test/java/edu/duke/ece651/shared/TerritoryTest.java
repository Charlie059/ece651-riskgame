package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TerritoryTest {
    @Test
    void testGetName() {
        Territory t = new Territory("123");
        assertTrue(t.getName().equals("123"));
        ArrayList<Territory> neigh = new ArrayList<>();
        Territory t2 = new Territory("a1", neigh);
        assertEquals(t2.neighbours.size(), 0);
    }

    @Test
    void test() {
        ArrayList<Territory> list = new ArrayList<Territory>();
        Territory t1 = new Territory("1");
        Territory t2 = new Territory("2");
        Territory t3 = new Territory("3");
        list.add(t1);
        list.add(t2);
        list.add(t3);
        System.out.println(t1.getName() + " " + t1.getOwner());
        System.out.println(t2.getName() + " " + t2.getOwner());
        System.out.println(t3.getName() + " " + t3.getOwner());

        for (int i = 0; i < 3; i++) {
            list.get(i).setOwner(i+1);
        }
        System.out.println(t1.getName() + " " + t1.getOwner());
        System.out.println(t2.getName() + " " + t2.getOwner());
        System.out.println(t3.getName() + " " + t3.getOwner());

    }


    @Test
    void addNeighbour() {
        Territory t = new Territory("a1");
        Territory t2 = new Territory("a2");
        t.addNeighbour(t2);
        assertEquals(t.neighbours.get(0), t2);
    }


    @Test
    void setOwner() {
        Territory t = new Territory("a1");
        t.setOwner(1);
        assertEquals(t.getOwner(), 1);
    }

    @Test
    void getUnits() {
        Territory t = new Territory("a1");
        HashMap<Integer, Integer> numOfUnits = new HashMap<>();
        for(int i = 0; i < 3; i++) {
            Unit u = new Unit(1);
            t.addUnit(u);
        }
        assertEquals(t.getUnits().get(1).size(), 3);
        numOfUnits.put(1, 3);
        t.addNumUnit(numOfUnits);
        assertEquals(t.getUnits().get(1).size(), 6);

    }

    @Test
    void removeUnit() {
        Territory t = new Territory("a1");
        //t.getUnits().put(1, new ArrayList<>());
        HashMap<Integer, Integer> numOfUnits = new HashMap<>();
        numOfUnits.put(1, 3);
        t.removeNumUnit(numOfUnits);
        for(int i = 0; i < 5; i++) {
            Unit u = new Unit(1);
            t.addUnit(u);
        }
        t.removeNumUnit(numOfUnits);
        assertEquals(t.getUnits().get(1).size(), 2);
    }


    @Test
    void changeOwner() {
        Territory t = new Territory("a1");
        t.setOwner(1);
        t.changeOwner(2);
        assertEquals(t.getOwner(), 2);
    }
}
