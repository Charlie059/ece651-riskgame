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
}
