package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class TextMapFactory implements MapFactory{
    private final int numOfPlayers;
    public TextMapFactory(int num_p){
        numOfPlayers = num_p;
    }

    @Override
    public HashMap<String, Territory> createMap() {
        HashMap<String, Territory> m = new HashMap<>();
        if (numOfPlayers == 3) {
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

            m.put("a1", a1);
            m.put("a2", a2);
            m.put("a3", a3);
            m.put("b1", b1);
            m.put("b2", b2);
            m.put("b3", b3);
            m.put("c1", c1);
            m.put("c2", c2);
            m.put("c3", c3);

        }
        return m;
    }

    @Override
    public ArrayList<ArrayList<String>> createGroupsForPlayer() {
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        if (numOfPlayers == 3) {
            ArrayList<String> group_1 = new ArrayList<>();
            group_1.add("a1");
            group_1.add("a2");
            group_1.add("a3");
            groups.add(group_1);

            ArrayList<String> group_2 = new ArrayList<>();
            group_2.add("b1");
            group_2.add("b2");
            group_2.add("b3");
            groups.add(group_2);

            ArrayList<String> group_3 = new ArrayList<>();
            group_3.add("c1");
            group_3.add("c2");
            group_3.add("c3");
            groups.add(group_3);
        }
        return groups;
    }


}
