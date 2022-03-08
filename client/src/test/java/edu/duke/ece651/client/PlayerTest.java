package edu.duke.ece651.client;

import edu.duke.ece651.shared.PlayerCounter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void constructorTest() {
        Player p1 = new Player(1);
        assertEquals(p1.id, 1);
        assertEquals(p1.wholeMap.getTerritoryList().size(), 9);
        assertEquals(p1.myTerritories.size(), 3);
    }

    @Test
    void multiPlayersTest(){
        PlayerCounter player_counter = null;//count player id
        Player p1 = new Player(player_counter.getInstance().getCurrent_id());//id: 1
        Player p2 = new Player(player_counter.getInstance().getCurrent_id());//id: 2
        Player p3 = new Player(player_counter.getInstance().getCurrent_id());//id: 3
        //check ids
        assertEquals(p1.id, 1);
        assertEquals(p2.id, 2);
        assertEquals(p3.id, 3);
        //check initial territories
        for (String i : p1.myTerritories.keySet()) {
            System.out.println("key: " + i + " value: " + p1.myTerritories.get(i));
            assertEquals(p1.wholeMap.getTerritoryList().get(i), p1.myTerritories.get(i));
        }

        for (String i : p2.myTerritories.keySet()) {
            System.out.println("key: " + i + " value: " + p2.myTerritories.get(i));
            assertEquals(p2.wholeMap.getTerritoryList().get(i), p2.myTerritories.get(i));
        }

        for (String i : p3.myTerritories.keySet()) {
            System.out.println("key: " + i + " value: " + p3.myTerritories.get(i));
            assertEquals(p3.wholeMap.getTerritoryList().get(i), p3.myTerritories.get(i));
        }
    }
}