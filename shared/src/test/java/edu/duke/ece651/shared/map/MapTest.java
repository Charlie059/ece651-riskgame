package edu.duke.ece651.shared.map;

import edu.duke.ece651.shared.Wrapper.AccountID;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
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
                for (String t : m.territoryList.keySet()) {
                    System.out.print(t + " ");
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
    void testIsPathExist(){
        Map m = new Map(5);
        //get the shortest paths from terr to other territories
        AccountID acc1 = new AccountID("1");
        AccountID acc2 = new AccountID("2");
        AccountID acc3 = new AccountID("3");
        AccountID acc4 = new AccountID("4");
        AccountID acc5 = new AccountID("5");
        //assign ownership
        for(String terr: m.getGroups().get(0)){
            m.getTerritoryList().get(terr).setOwner(acc1);
        }
        for(String terr: m.getGroups().get(1)){
            m.getTerritoryList().get(terr).setOwner(acc2);
        }
        //assign ownership
        for(String terr: m.getGroups().get(2)){
            m.getTerritoryList().get(terr).setOwner(acc3);
        }
        for(String terr: m.getGroups().get(3)){
            m.getTerritoryList().get(terr).setOwner(acc4);
        }
        for(String terr: m.getGroups().get(4)){
            m.getTerritoryList().get(terr).setOwner(acc5);
        }
        m.getTerritoryList().get("c1").setOwner(acc1);
        assertTrue(m.isPathExist(acc1, "a1", "a2"));
        assertFalse(m.isPathExist(acc1, "a1", "c1"));
        assertThrows(IllegalArgumentException.class, ()->m.isPathExist(acc1, "a1", "b1"));

    }

    @Test
    void testIsAdjacent() {
        Map m = new Map(3);
        AccountID acc0 = new AccountID("0");
        AccountID acc1 = new AccountID("1");
        m.territoryList.get("b1").setOwner(acc1);
        m.territoryList.get("b3").setOwner(acc1);
        m.territoryList.get("a1").setOwner(acc0);
        m.territoryList.get("a3").setOwner(acc0);
        assertThrows(IllegalArgumentException.class, () -> m.isAdjacent(acc0, "a1", "a3"));
        assertTrue(m.isAdjacent(acc0, "a1", "b1"));
        assertFalse(m.isAdjacent(acc0, "a1", "b3"));
        assertFalse(m.isAdjacent(acc0, "ssss1", "b3"));
        assertFalse(m.isAdjacent(acc0, "a1", "dsfsffg3"));
    }


    @Test
    void testDisplayMap() {

        Map m = new Map(3);
        AccountID a1 = new AccountID("1");
        AccountID a2 = new AccountID("2");
        AccountID a3 = new AccountID("3");
        m.territoryList.get("a1").setOwner(a1);
        m.territoryList.get("a2").setOwner(a1);
        m.territoryList.get("a3").setOwner(a1);
        m.territoryList.get("b1").setOwner(a2);
        m.territoryList.get("b2").setOwner(a2);
        m.territoryList.get("b3").setOwner(a2);
        m.territoryList.get("c1").setOwner(a3);
        m.territoryList.get("c2").setOwner(a3);
        m.territoryList.get("c3").setOwner(a3);

        ArrayList<ArrayList<Integer>> units_arr = new ArrayList<>();
        ArrayList<Integer> curr = new ArrayList<>();
        curr.add(0);
        curr.add(3);

        ArrayList<Integer> curr2 = new ArrayList<>();
        curr2.add(1);
        curr2.add(2);

        units_arr.add(curr);
        units_arr.add(curr2);

        m.territoryList.get("a1").addUnitMultiLevels(units_arr);
        MapView displayer = new MapTextView(3, System.out);
        m.displayMap(displayer);

    }

    @Test
    void testDisplayMapErr(){
        Map m = new Map(3);
        AccountID a1 = new AccountID("1");
        AccountID a2 = new AccountID("2");
        AccountID a3 = new AccountID("3");
        m.territoryList.get("a1");
        m.territoryList.get("a2");
        m.territoryList.get("a3");
        m.territoryList.get("b1");
        m.territoryList.get("b2");
        m.territoryList.get("b3");
        m.territoryList.get("c1");
        m.territoryList.get("c2");
        m.territoryList.get("c3");
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
        AccountID acc1 = new AccountID("1");
        AccountID acc2 = new AccountID("2");
        //assign ownership
        for(String terr: m.getGroups().get(0)){
            m.getTerritoryList().get(terr).setOwner(acc1);
        }
        for(String terr: m.getGroups().get(1)){
            m.getTerritoryList().get(terr).setOwner(acc2);
        }
        assertEquals(m.getTerritoryList().get("a1").getOwnerId(), acc1);
        assertEquals(m.getTerritoryList().get("b1").getOwnerId(), acc2);
        assertEquals(m.getTerritoryList().size(), 6);
        assertEquals(m.getGroups().size(), 2);

        //test isAdjacent
        assertEquals(m.isAdjacent(acc1, "a2", "b2"), true);
        assertThrows(IllegalArgumentException.class, ()->m.isAdjacent(acc1, "a1", "a2"));

        //players #: 4
        Map m2 = new Map(4);
        assertEquals(m2.getTerritoryList().size(), 12);
        assertEquals(m2.getGroups().size(), 4);

        //players #: 5
        Map m3= new Map(5);
        assertEquals(m3.getTerritoryList().size(), 15);
        assertEquals(m3.getGroups().size(), 5);
    }

    @Test
    void testShortestPath(){
        Map m = new Map(5);
        //get the shortest paths from terr to other territories
        AccountID acc1 = new AccountID("1");
        AccountID acc2 = new AccountID("2");
        AccountID acc3 = new AccountID("3");
        AccountID acc4 = new AccountID("4");
        AccountID acc5 = new AccountID("5");
        //assign ownership
        for(String terr: m.getGroups().get(0)){
            m.getTerritoryList().get(terr).setOwner(acc1);
        }
        for(String terr: m.getGroups().get(1)){
            m.getTerritoryList().get(terr).setOwner(acc2);
        }
        //assign ownership
        for(String terr: m.getGroups().get(2)){
            m.getTerritoryList().get(terr).setOwner(acc3);
        }
        for(String terr: m.getGroups().get(3)){
            m.getTerritoryList().get(terr).setOwner(acc4);
        }
        for(String terr: m.getGroups().get(4)){
            m.getTerritoryList().get(terr).setOwner(acc5);
        }
        //from -> to
        assertEquals(m.shortestPathFrom(acc1, "a1", "a1"), 0);
        assertEquals(m.shortestPathFrom(acc1, "a1", "a2"), 20);
        //wrong owner
        assertEquals(-1, m.shortestPathFrom(acc1, "a1", "e1"));
        //cannot arrive
        m.getTerritoryList().get("c1").setOwner(acc1);
        assertEquals(Integer.MAX_VALUE, m.shortestPathFrom(acc1, "a1", "c1"));
    }

}