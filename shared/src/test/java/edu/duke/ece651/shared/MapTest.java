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
            for (ArrayList<String> group : m.groups) {
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
        assertThrows(IllegalArgumentException.class, () -> m.isAdjacent(-1, "a1", "a3"));

        m.territoryList.get("b1").setOwner(0);
        m.territoryList.get("b3").setOwner(0);
        assertTrue(m.isAdjacent(-1, "a1", "b1"));
        assertFalse(m.isAdjacent(-1, "a1", "b3"));
    }

    @Test
    void testIsPathExist() {
        Map m = new Map(3);
        m.territoryList.get("b1").setOwner(0);
        m.territoryList.get("c1").setOwner(1);
        assertTrue(m.isPathExist(-1, "a3", "a1"));
        assertTrue(m.isPathExist(-1, "a3", "a2"));
        assertTrue(m.isPathExist(-1, "a1", "a2"));
        assertThrows(IllegalArgumentException.class, () -> m.isPathExist(-1, "a1", "c1"));
        assertFalse(m.isPathExist(-1, "a1", "c3"));

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
}
