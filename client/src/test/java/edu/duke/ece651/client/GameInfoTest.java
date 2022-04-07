package edu.duke.ece651.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameInfoTest {
    @Test
    public void test_GameInfo(){
        GameInfo gameInfo = new GameInfo(1,1,"1");
        assertEquals(1,gameInfo.getGameID());
        assertEquals(1,gameInfo.getNPlayer());
        assertEquals("1",gameInfo.getNote());
    }
}