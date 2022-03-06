package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    HashMap<String, Territory> territoryList;
    ArrayList<ArrayList<String>> groups; 
    /**
     * Create a fixed map for 3 players. Each player has three territories in the
     * beginning.
     * 
     * @return HaspMap of all the territory
     */
    private static HashMap<String, Territory> createMapForThreePlayer() {
        HashMap<String, Territory> m = new HashMap<String, Territory>();

        Territory a1 = new Territory("a1");
        Territory a2 = new Territory("a2");
        Territory a3 = new Territory("a3");

        Territory b1 = new Territory("b1");
        Territory b2 = new Territory("b2");
        Territory b3 = new Territory("b3");

        Territory c1 = new Territory("c1");
        Territory c2 = new Territory("c2");
        Territory c3 = new Territory("c3");

        a1.addNeighbour(a3);
        a1.addNeighbour(a2);
        a1.addNeighbour(b1);
        a2.addNeighbour(a3);
        a2.addNeighbour(a1);
        a2.addNeighbour(c1);
        a3.addNeighbour(a1);
        a3.addNeighbour(a2);

        b1.addNeighbour(b3);
        b1.addNeighbour(b2);
        b1.addNeighbour(a1);
        b2.addNeighbour(b3);
        b2.addNeighbour(b1);
        b2.addNeighbour(c2);
        b3.addNeighbour(b1);
        b3.addNeighbour(b2);

        c1.addNeighbour(c3);
        c1.addNeighbour(c2);
        c1.addNeighbour(a2);
        c2.addNeighbour(c3);
        c2.addNeighbour(c1);
        c2.addNeighbour(b2);
        c3.addNeighbour(c1);
        c3.addNeighbour(c2);

        m.put("a1",a1);
        m.put("a2",a2);
        m.put("a3",a3);
        m.put("b1",b1);
        m.put("b2",b2);
        m.put("b3",b3);
        m.put("c1",c1);
        m.put("c2",c2);
        m.put("c3",c3);
        
        return m;

    }

    private static ArrayList<ArrayList<String>> createGroupsforThreePlayer(){
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();

        ArrayList<String> group_1 = new ArrayList<String>();
        group_1.add("a1");
        group_1.add("a2");
        group_1.add("a3");
        groups.add(group_1);

        ArrayList<String> group_2 = new ArrayList<String>();
        group_2.add("b1");
        group_2.add("b2");
        group_2.add("b3");
        groups.add(group_2);

        ArrayList<String> group_3 = new ArrayList<String>();
        group_3.add("c1");
        group_3.add("c2");
        group_3.add("c3");
        groups.add(group_3);
            
        return groups;
    }
    
    Map() {
        this.territoryList = createMapForThreePlayer();
        this.groups = createGroupsforThreePlayer();
    }

    /**
     * Use to test whether each territory has correct neighbour and whether groups is correct
     */
    void showNeighbours_TEST() {
        for (String name : territoryList.keySet()) {
            System.out.print("territory[" + name + "]: ");
            for (Territory t:territoryList.get(name).neighbours) {
                System.out.print(t.getName()+" ");
            }
            System.out.print("\n");
        }
        for(ArrayList<String> group:groups){
            for (String name : group) {
                System.out.print(name + " ");
            }
            System.out.print("\n");
        }
    }
}

