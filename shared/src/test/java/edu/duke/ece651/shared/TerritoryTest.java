package edu.duke.ece651.shared;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TerritoryTest {
    @Test
    void testGetName() {
        Territory t = new Territory("123");
        assertTrue(t.getName().equals("123"));
    }
}
