package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    @Test
    void getAtk() {
        Unit u = new Unit();
        assertEquals(u.getAtk(), 1);
    }

    @Test
    void getHp() {
        Unit u = new Unit();
        assertEquals(u.getHp(), 1);
    }

    @Test
    void getLevel() {
        Unit u = new Unit();
        assertEquals(u.getLevel(), 1);
    }
}