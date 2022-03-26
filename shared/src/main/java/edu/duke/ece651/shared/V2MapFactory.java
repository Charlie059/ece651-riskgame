package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class V2MapFactory {
    private final int numOfPlayers;

    public V2MapFactory(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    private void createTerritoryList_2P(HashMap<String, HashSet<String>> m) {
        HashSet<String> a1_neigh = new HashSet<>();
        a1_neigh.add("a3");
        a1_neigh.add("b1");
        a1_neigh.add("a2");
        HashSet<String> a2_neigh = new HashSet<>();
        a2_neigh.add("a1");
        a2_neigh.add("a3");
        a2_neigh.add("b2");
        HashSet<String> a3_neigh = new HashSet<>();
        a3_neigh.add("a1");
        a3_neigh.add("a2");
        HashSet<String> b1_neigh = new HashSet<>();
        b1_neigh.add("a1");
        b1_neigh.add("b2");
        b1_neigh.add("b3");
        HashSet<String> b2_neigh = new HashSet<>();
        b2_neigh.add("b1");
        b2_neigh.add("a2");
        b2_neigh.add("b3");
        HashSet<String> b3_neigh = new HashSet<>();
        b3_neigh.add("b1");
        b3_neigh.add("b2");

        m.put("a1", a1_neigh);
        m.put("a2", a2_neigh);
        m.put("a3", a3_neigh);
        m.put("b1", b1_neigh);
        m.put("b2", b2_neigh);
        m.put("b3", b3_neigh);

    }

    private void createTerritoryList_3P(HashMap<String, HashSet<String>> m) {

        HashSet<String> a1_neigh = new HashSet<>();
        a1_neigh.add("a3");
        a1_neigh.add("a2");
        a1_neigh.add("b1");
        HashSet<String> a2_neigh = new HashSet<>();
        a2_neigh.add("a3");
        a2_neigh.add("a1");
        a2_neigh.add("c1");
        HashSet<String> a3_neigh = new HashSet<>();
        a3_neigh.add("a1");
        a3_neigh.add("a2");
        HashSet<String> b1_neigh = new HashSet<>();
        b1_neigh.add("b3");
        b1_neigh.add("b2");
        b1_neigh.add("a1");
        HashSet<String> b2_neigh = new HashSet<>();
        b2_neigh.add("b3");
        b2_neigh.add("b1");
        b2_neigh.add("c2");
        HashSet<String> b3_neigh = new HashSet<>();
        b3_neigh.add("b1");
        b3_neigh.add("b2");
        HashSet<String> c1_neigh = new HashSet<>();
        c1_neigh.add("c3");
        c1_neigh.add("c2");
        c1_neigh.add("a2");
        HashSet<String> c2_neigh = new HashSet<>();
        c2_neigh.add("c3");
        c2_neigh.add("c1");
        c2_neigh.add("b2");
        HashSet<String> c3_neigh = new HashSet<>();
        c3_neigh.add("c1");
        c3_neigh.add("c2");

        m.put("a1", a1_neigh);
        m.put("a2", a2_neigh);
        m.put("a3", a3_neigh);
        m.put("b1", b1_neigh);
        m.put("b2", b2_neigh);
        m.put("b3", b3_neigh);
        m.put("c1", c1_neigh);
        m.put("c2", c2_neigh);
        m.put("c3", c3_neigh);
    }

    private void createTerritoryList_4P(HashMap<String, HashSet<String>> m) {
        HashSet<String> a1_neigh = new HashSet<>();
        a1_neigh.add("c1");
        a1_neigh.add("a3");
        HashSet<String> a2_neigh = new HashSet<>();
        a2_neigh.add("a3");
        a2_neigh.add("b2");
        HashSet<String> a3_neigh = new HashSet<>();
        a3_neigh.add("a1");
        a3_neigh.add("a2");
        HashSet<String> b1_neigh = new HashSet<>();
        b1_neigh.add("b3");
        b1_neigh.add("d1");
        HashSet<String> b2_neigh = new HashSet<>();
        b2_neigh.add("a2");
        b2_neigh.add("b3");
        HashSet<String> b3_neigh = new HashSet<>();
        b3_neigh.add("b1");
        b3_neigh.add("b2");
        HashSet<String> c1_neigh = new HashSet<>();
        c1_neigh.add("a1");
        c1_neigh.add("c3");
        HashSet<String> c2_neigh = new HashSet<>();
        c2_neigh.add("c3");
        c2_neigh.add("b2");
        HashSet<String> c3_neigh = new HashSet<>();
        c3_neigh.add("c1");
        c3_neigh.add("c2");
        HashSet<String> d1_neigh = new HashSet<>();
        d1_neigh.add("b1");
        d1_neigh.add("d3");
        HashSet<String> d2_neigh = new HashSet<>();
        d2_neigh.add("c2");
        d2_neigh.add("d3");
        HashSet<String> d3_neigh = new HashSet<>();
        d3_neigh.add("d1");
        d3_neigh.add("d2");

        m.put("a1", a1_neigh);
        m.put("a2", a2_neigh);
        m.put("a3", a3_neigh);
        m.put("b1", b1_neigh);
        m.put("b2", b2_neigh);
        m.put("b3", b3_neigh);
        m.put("c1", c1_neigh);
        m.put("c2", c2_neigh);
        m.put("c3", c3_neigh);
        m.put("d1", d1_neigh);
        m.put("d2", d2_neigh);
        m.put("d3", d3_neigh);

    }


    private void createTerritoryList_5P(HashMap<String, HashSet<String>> m) {
        HashSet<String> a1_neigh = new HashSet<>();
        a1_neigh.add("b1");
        a1_neigh.add("a3");
        a1_neigh.add("b3");
        a1_neigh.add("a2");
        HashSet<String> a2_neigh = new HashSet<>();
        a2_neigh.add("e1");
        a2_neigh.add("a3");
        a2_neigh.add("a1");
        a2_neigh.add("e3");
        HashSet<String> a3_neigh = new HashSet<>();
        a3_neigh.add("a2");
        a3_neigh.add("a1");
        a3_neigh.add("b1");
        a3_neigh.add("b3");
        a3_neigh.add("c3");
        a3_neigh.add("d3");
        a3_neigh.add("e3");
        a3_neigh.add("e1");
        HashSet<String> b1_neigh = new HashSet<>();
        b1_neigh.add("a1");
        b1_neigh.add("b3");
        b1_neigh.add("b2");
        b1_neigh.add("a3");
        HashSet<String> b2_neigh = new HashSet<>();
        b2_neigh.add("b1");
        b2_neigh.add("c2");
        b2_neigh.add("b3");
        b2_neigh.add("c3");
        HashSet<String> b3_neigh = new HashSet<>();
        b3_neigh.add("b1");
        b3_neigh.add("b2");
        b3_neigh.add("a1");
        b3_neigh.add("a3");
        b3_neigh.add("c2");
        b3_neigh.add("c3");
        b3_neigh.add("e3");
        b3_neigh.add("d3");
        HashSet<String> c1_neigh = new HashSet<>();
        c1_neigh.add("d1");
        c1_neigh.add("c3");
        c1_neigh.add("d3");
        c1_neigh.add("c2");
        HashSet<String> c2_neigh = new HashSet<>();
        c2_neigh.add("b2");
        c2_neigh.add("b3");
        c2_neigh.add("c1");
        c2_neigh.add("c3");
        HashSet<String> c3_neigh = new HashSet<>();
        c3_neigh.add("c1");
        c3_neigh.add("c2");
        c3_neigh.add("b2");
        c3_neigh.add("b3");
        c3_neigh.add("d1");
        c3_neigh.add("d3");
        c3_neigh.add("a3");
        c3_neigh.add("e3");
        HashSet<String> d1_neigh = new HashSet<>();
        d1_neigh.add("c1");
        d1_neigh.add("c3");
        d1_neigh.add("d2");
        d1_neigh.add("d3");
        HashSet<String> d2_neigh = new HashSet<>();
        d2_neigh.add("d1");
        d2_neigh.add("d3");
        d2_neigh.add("e2");
        d2_neigh.add("e3");
        HashSet<String> d3_neigh = new HashSet<>();
        d3_neigh.add("d1");
        d3_neigh.add("d2");
        d3_neigh.add("c1");
        d3_neigh.add("c3");
        d3_neigh.add("e2");
        d3_neigh.add("e3");
        d3_neigh.add("a3");
        d3_neigh.add("b3");
        HashSet<String> e1_neigh = new HashSet<>();
        e1_neigh.add("e2");
        e1_neigh.add("e3");
        e1_neigh.add("a2");
        e1_neigh.add("a3");
        HashSet<String> e2_neigh = new HashSet<>();
        e2_neigh.add("e1");
        e2_neigh.add("e3");
        e2_neigh.add("d2");
        e2_neigh.add("d3");
        HashSet<String> e3_neigh = new HashSet<>();
        e3_neigh.add("e1");
        e3_neigh.add("e2");
        e3_neigh.add("a2");
        e3_neigh.add("a3");
        e3_neigh.add("d2");
        e3_neigh.add("d3");
        e3_neigh.add("b3");
        e3_neigh.add("c3");

        m.put("a1", a1_neigh);
        m.put("a2", a2_neigh);
        m.put("a3", a3_neigh);
        m.put("b1", b1_neigh);
        m.put("b2", b2_neigh);
        m.put("b3", b3_neigh);
        m.put("c1", c1_neigh);
        m.put("c2", c2_neigh);
        m.put("c3", c3_neigh);
        m.put("d1", d1_neigh);
        m.put("d2", d2_neigh);
        m.put("d3", d3_neigh);
        m.put("e1", e1_neigh);
        m.put("e2", e2_neigh);
        m.put("e3", e3_neigh);
    }


    /**
     * create adjacent list for the graph
     * @return
     */
    public HashMap<String, HashSet<String>> createGraph() {
        HashMap<String, HashSet<String>> m = new HashMap<>();
        switch (numOfPlayers) {
            case 2:
                createTerritoryList_2P(m);
                break;
            case 3:
                createTerritoryList_3P(m);
                break;
            case 4:
                createTerritoryList_4P(m);
                break;
            case 5:
                createTerritoryList_5P(m);
                break;

            default:
                break;
        }
        return m;
    }

}
