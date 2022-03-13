package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class TextMapFactory implements MapFactory {
    private final int numOfPlayers;

    public TextMapFactory(int num_p) {
        numOfPlayers = num_p;
    }

    private void createTerritoryList_2P(HashMap<String, Territory> m) {
        Territory a1 = new Territory("a1");
        Territory a2 = new Territory("a2");
        Territory a3 = new Territory("a3");

        Territory b1 = new Territory("b1");
        Territory b2 = new Territory("b2");
        Territory b3 = new Territory("b3");

        a1.addNeighbour(a3);
        a1.addNeighbour(b1);
        a2.addNeighbour(a3);
        a2.addNeighbour(b2);
        a3.addNeighbour(a1);
        a3.addNeighbour(a2);

        b1.addNeighbour(b3);
        b1.addNeighbour(a1);
        b2.addNeighbour(b3);
        b2.addNeighbour(a2);
        b3.addNeighbour(b1);
        b3.addNeighbour(b2);

        m.put("a1", a1);
        m.put("a2", a2);
        m.put("a3", a3);
        m.put("b1", b1);
        m.put("b2", b2);
        m.put("b3", b3);

    }

    private void createGroup_2P(ArrayList<ArrayList<String>> groups) {
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

    }

    private void createTerritoryList_3P(HashMap<String, Territory> m) {
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

    private void createGroup_3P(ArrayList<ArrayList<String>> groups) {
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

    private void createTerritoryList_4P(HashMap<String, Territory> m) {
        Territory a1 = new Territory("a1");
        Territory a2 = new Territory("a2");
        Territory a3 = new Territory("a3");

        Territory b1 = new Territory("b1");
        Territory b2 = new Territory("b2");
        Territory b3 = new Territory("b3");

        Territory c1 = new Territory("c1");
        Territory c2 = new Territory("c2");
        Territory c3 = new Territory("c3");

        Territory d1 = new Territory("d1");
        Territory d2 = new Territory("d2");
        Territory d3 = new Territory("d3");

        a1.addNeighbour(c1);
        a1.addNeighbour(a3);
        a2.addNeighbour(a3);
        a2.addNeighbour(b2);
        a3.addNeighbour(a1);
        a3.addNeighbour(a2);

        b1.addNeighbour(b3);
        b1.addNeighbour(d1);
        b2.addNeighbour(b3);
        b2.addNeighbour(a2);
        b3.addNeighbour(b1);
        b3.addNeighbour(b2);

        c1.addNeighbour(a1);
        c1.addNeighbour(c3);
        c2.addNeighbour(d2);
        c2.addNeighbour(c3);
        c3.addNeighbour(c1);
        c3.addNeighbour(c2);

        d1.addNeighbour(b1);
        d1.addNeighbour(d3);
        d2.addNeighbour(c2);
        d2.addNeighbour(d3);
        d3.addNeighbour(d1);
        d3.addNeighbour(d2);

        m.put("a1", a1);
        m.put("a2", a2);
        m.put("a3", a3);
        m.put("b1", b1);
        m.put("b2", b2);
        m.put("b3", b3);
        m.put("c1", c1);
        m.put("c2", c2);
        m.put("c3", c3);
        m.put("d1", d1);
        m.put("d2", d2);
        m.put("d3", d3);

    }

    private void createGroup_4P(ArrayList<ArrayList<String>> groups) {
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

        ArrayList<String> group_4 = new ArrayList<>();
        group_4.add("d1");
        group_4.add("d2");
        group_4.add("d3");
        groups.add(group_4);
    }

    private void createTerritoryList_5P(HashMap<String, Territory> m) {
        Territory a1 = new Territory("a1");
        Territory a2 = new Territory("a2");
        Territory a3 = new Territory("a3");

        Territory b1 = new Territory("b1");
        Territory b2 = new Territory("b2");
        Territory b3 = new Territory("b3");

        Territory c1 = new Territory("c1");
        Territory c2 = new Territory("c2");
        Territory c3 = new Territory("c3");

        Territory d1 = new Territory("d1");
        Territory d2 = new Territory("d2");
        Territory d3 = new Territory("d3");

        Territory e1 = new Territory("e1");
        Territory e2 = new Territory("e2");
        Territory e3 = new Territory("e3");

        a1.addNeighbour(b1);
        a1.addNeighbour(a3);
        a2.addNeighbour(e1);
        a2.addNeighbour(a3);
        a3.addNeighbour(a1);
        a3.addNeighbour(a2);

        b1.addNeighbour(a1);
        b1.addNeighbour(b3);
        b2.addNeighbour(c2);
        b2.addNeighbour(b3);
        b3.addNeighbour(b1);
        b3.addNeighbour(b2);

        c1.addNeighbour(d1);
        c1.addNeighbour(c3);
        c2.addNeighbour(b2);
        c2.addNeighbour(c3);
        c3.addNeighbour(c1);
        c3.addNeighbour(c2);

        d1.addNeighbour(c1);
        d1.addNeighbour(d3);
        d2.addNeighbour(e2);
        d2.addNeighbour(d3);
        d3.addNeighbour(d1);
        d3.addNeighbour(d2);

        e1.addNeighbour(a2);
        e1.addNeighbour(e3);
        e2.addNeighbour(d2);
        e2.addNeighbour(e3);
        e3.addNeighbour(e1);
        e3.addNeighbour(e2);

        m.put("a1", a1);
        m.put("a2", a2);
        m.put("a3", a3);
        m.put("b1", b1);
        m.put("b2", b2);
        m.put("b3", b3);
        m.put("c1", c1);
        m.put("c2", c2);
        m.put("c3", c3);
        m.put("d1", d1);
        m.put("d2", d2);
        m.put("d3", d3);
        m.put("e1", e1);
        m.put("e2", e2);
        m.put("e3", e3);

    }

    private void createGroup_5P(ArrayList<ArrayList<String>> groups) {
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

        ArrayList<String> group_4 = new ArrayList<>();
        group_4.add("d1");
        group_4.add("d2");
        group_4.add("d3");
        groups.add(group_4);

        ArrayList<String> group_5 = new ArrayList<>();
        group_5.add("e1");
        group_5.add("e2");
        group_5.add("e3");
        groups.add(group_5);
    }

    @Override
    public HashMap<String, Territory> createMap() {
        HashMap<String, Territory> m = new HashMap<>();
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

    @Override
    public ArrayList<ArrayList<String>> createGroupsForPlayer() {
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        switch (numOfPlayers) {
            case 2:
                createGroup_2P(groups);
                break;
            case 3:
                createGroup_3P(groups);
                break;
            case 4:
                createGroup_4P(groups);
                break;
            case 5:
                createGroup_5P(groups);
                break;
            default:
                break;
        }
        return groups;
    }

}

// 为什么要创建TextMapFactory? GUI界面下 map存储形式不变。