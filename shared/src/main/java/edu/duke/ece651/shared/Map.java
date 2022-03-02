package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Map {
    HashMap<String, Territory> territoryList;

    /**
     * Create a fixed map for 3 players. Each player has three territories in the
     * beginning.
     * 
     * @return HaspMap of all the territory
     */
    private static HashMap<String, Territory> createMapForThreePlayer() {
        HashMap<String, Territory> m = new HashMap<String, Territory>();
        m.put("a1", new Territory("a1", new ArrayList<String>(Arrays.asList("a3", "a2", "b1"))));
        m.put("a2", new Territory("a2", new ArrayList<String>(Arrays.asList("a3", "a1", "c1"))));
        m.put("a3", new Territory("a3", new ArrayList<String>(Arrays.asList("a1", "a2"))));

        m.put("b1", new Territory("b1", new ArrayList<String>(Arrays.asList("b3", "b2", "a1"))));
        m.put("b2", new Territory("b2", new ArrayList<String>(Arrays.asList("b3", "b1", "c2"))));
        m.put("b3", new Territory("b3", new ArrayList<String>(Arrays.asList("b1", "b2"))));

        m.put("c1", new Territory("c1", new ArrayList<String>(Arrays.asList("c3", "c2", "a2"))));
        m.put("c2", new Territory("c2", new ArrayList<String>(Arrays.asList("c3", "c1", "b2"))));
        m.put("c3", new Territory("c3", new ArrayList<String>(Arrays.asList("c1", "c2"))));

        return m;

    }

    Map() {
        this.territoryList = createMapForThreePlayer();
    }

    /**
     * Use to test whether each territory has correct neighbour
     */
    void showNeighbours_TEST() {
        for (String name : territoryList.keySet()) {
            System.out.print("territory[" + name + "]: ");
            for (String neighbourName : territoryList.get(name).neighbours) {
                System.out.print(neighbourName+" ");
            }
            System.out.print("\n");
        }
    }
}

/*
TODO:
    1、怎么assign领地给每个player？ group怎么传递？
*/