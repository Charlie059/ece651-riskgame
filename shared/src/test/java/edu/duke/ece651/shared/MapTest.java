package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    @Test
    void testShowNeighbours_TEST() {
        Map m = new Map(3);
        assertEquals(m.territoryList.size(), 9);
        assertEquals(m.numOfPlayers, m.groups.size());
        m.showNeighbours_TEST();
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
}
