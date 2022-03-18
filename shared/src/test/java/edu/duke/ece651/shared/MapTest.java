package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MapTest {
    @Test
    void testShowNeighbours_TEST() {
        for (int i = 2; i <= 5; i++) {
            System.out.println("show map structure for " + Integer.toString(i) + "players.");
            int numOfPlayers = i;
            Map m = new Map(numOfPlayers);
            assertEquals(m.territoryList.size(), 3 * numOfPlayers);
            assertEquals(m.numOfPlayers, m.groups.size());
            for (String name : m.territoryList.keySet()) {
                System.out.print("territory[" + name + "]: ");
                for (Territory t : m.territoryList.get(name).neighbours) {
                    System.out.print(t.getName() + " ");
                }
                System.out.print("\n");
            }
            System.out.println("show groups:");
            for (ArrayList<String> group : m.getGroups()) {
                for (String name : group) {
                    System.out.print(name + " ");
                }
                System.out.print("\n");
            }
        }

    }

    @Test
    void testIsAdjacent() {
        Map m = new Map(3);
        assertThrows(IllegalArgumentException.class, () -> m.isAdjacent(1, "a1", "a3"));

        m.territoryList.get("b1").setOwner(0);
        m.territoryList.get("b3").setOwner(0);
        assertTrue(m.isAdjacent(1, "a1", "b1"));
        assertFalse(m.isAdjacent(1, "a1", "b3"));
    }

    @Test
    void testIsPathExist() {
        Map m = new Map(3);
        assertTrue(m.isPathExist(1, "a3", "a1"));
        assertTrue(m.isPathExist(1, "a3", "a2"));
        assertTrue(m.isPathExist(1, "a1", "a2"));
        assertThrows(IllegalArgumentException.class, ()->m.isPathExist(1, "a1", "c1"));
        assertThrows(IllegalArgumentException.class, ()->m.isPathExist(1, "a1", "c3"));

    }

    @Test
    void testDisplayMap() {
        Map m = new Map(3);
        m.territoryList.get("a1").setOwner(1);
        m.territoryList.get("a2").setOwner(1);
        m.territoryList.get("a3").setOwner(1);
        m.territoryList.get("b1").setOwner(2);
        m.territoryList.get("b2").setOwner(2);
        m.territoryList.get("b3").setOwner(2);
        m.territoryList.get("c1").setOwner(3);
        m.territoryList.get("c2").setOwner(3);
        m.territoryList.get("c3").setOwner(3);

        m.territoryList.get("a1").addUnit(new Unit());
        MapView displayer = new MapTextView(3, System.out);
        m.displayMap(displayer);

    }

    /**
     * test map with multiple players(2-5)
     */
    @Test
    void createMap() {
        //players #: 2
        Map m = new Map(2);
        assertEquals(m.getTerritoryList().size(), 6);
        assertEquals(m.getGroups().size(), 2);
        //test isPathExist
        assertThrows(IllegalArgumentException.class, ()->m.isPathExist(2, "a1", "b3"));
        assertEquals(m.isPathExist(1, "a1", "a2"), true);
        m.getTerritoryList().get("b3").setOwner(1);
        assertEquals(m.isPathExist(1, "a1", "b3"), false);
        //test isAdjacent
        assertEquals(m.isAdjacent(1, "a2", "b2"), true);
        assertThrows(IllegalArgumentException.class, ()->m.isAdjacent(1, "a1", "a2"));



        //players #: 4
        Map m2 = new Map(4);
        assertEquals(m2.getTerritoryList().size(), 12);
        assertEquals(m2.getGroups().size(), 4);
        assertThrows(IllegalArgumentException.class, ()->m2.isPathExist(2, "a1", "b3"));
        assertEquals(m2.isPathExist(1, "a1", "a2"), true);
        m2.getTerritoryList().get("b3").setOwner(1);
        assertEquals(m2.isPathExist(1, "a1", "b3"), false);
        //players #: 5
        Map m3= new Map(5);
        assertEquals(m3.getTerritoryList().size(), 15);
        assertEquals(m3.getGroups().size(), 5);
        assertThrows(IllegalArgumentException.class, ()->m3.isPathExist(2, "a1", "b3"));
        assertEquals(m3.isPathExist(1, "a1", "a2"), true);
        m3.getTerritoryList().get("b3").setOwner(1);
        assertEquals(m3.isPathExist(1, "a1", "b3"), false);
    }

}
