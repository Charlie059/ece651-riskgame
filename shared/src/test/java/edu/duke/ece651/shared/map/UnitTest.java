package edu.duke.ece651.shared.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Test
    void testEquals() {
        Unit u1 = new Unit().setLevel(1).setValue(1);
        Unit u2 = new Unit().setLevel(1).setValue(1);
        Unit u3 = new Unit().setLevel(2).setValue(1);
        u1.getLevel();
        assertEquals(u1, u2);
        assertEquals(u1, u1);
        assertNotEquals(u1, u3);
    }
}