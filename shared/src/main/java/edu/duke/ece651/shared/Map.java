package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Map {
    public int numOfPlayers; // number of player = 3
    private MapFactory myMapFactory;
    HashMap<String, Territory> territoryList;
    ArrayList<ArrayList<String>> groups;

    /**
     * constructor
     * 
     * @param _numOfPlayers
     */
    public Map(int _numOfPlayers) {
        this.numOfPlayers = _numOfPlayers;
        myMapFactory = new TextMapFactory(numOfPlayers);
        this.territoryList = myMapFactory.createMap();
        this.groups = myMapFactory.createGroupsForPlayer();
    }

    /**
     * Use to test whether each territory has correct neighbour and whether groups
     * is correct
     */
    void showNeighbours_TEST() {
        for (String name : territoryList.keySet()) {
            System.out.print("territory[" + name + "]: ");
            for (Territory t : territoryList.get(name).neighbours) {
                System.out.print(t.getName() + " ");
            }
            System.out.print("\n");
        }
        for (ArrayList<String> group : groups) {
            for (String name : group) {
                System.out.print(name + " ");
            }
            System.out.print("\n");
        }
    }

    public HashMap<String, Territory> getTerritoryList() {
        return territoryList;
    }

    public ArrayList<ArrayList<String>> getGroups() {
        return groups;
    }

    /**
     * Used in Move action, check if there is a path between the two
     * territories(with names "from" and "to") of the player (playerID).
     * 
     * @param playerID
     * @param from
     * @param to
     * @return return true if path existed, else return false.
     */
    public boolean isPathExist(int playerID, String from, String to) throws IllegalArgumentException {
        Territory terrSrc = territoryList.get(from);
        Territory terrDst = territoryList.get(to);

        if (terrSrc.getOwner() != playerID || terrDst.getOwner() != playerID) {
            throw new IllegalArgumentException(
                    "territory (from) and territory (to) belong to different(wrong) player.");
        }

        // DFS search an existed path
        HashSet<Territory> visited = new HashSet<Territory>();
        Stack<Territory> s = new Stack<Territory>();

        s.push(terrSrc);
        visited.add(terrSrc);
        while (!s.empty()) {
            Territory cur_node = s.pop();
            if (cur_node.equals(terrDst))
                return true;

            for (Territory t : cur_node.neighbours) {
                if (visited.contains(t) == false && t.getOwner() == playerID) {
                    visited.add(t);
                    s.push(t);
                }
            }
        }

        return false;
    }

    /**
     * Used in Attack action, territory(Terr1) belongs to playerID, check whether
     * territory(Terr2) belongs to another player.
     * If yes, check whether these two territories are adjacent.
     * 
     * @param playerID
     * @param Terr1
     * @param Terr2
     * @return check result
     */
    public boolean isAdjacent(int playerID, String Terr1, String Terr2) throws IllegalArgumentException {
        Territory t1 = territoryList.get(Terr1);
        Territory t2 = territoryList.get(Terr2);

        if (t1.getOwner() != playerID || t2.getOwner() == playerID) {
            throw new IllegalArgumentException("Terr1 and Terr2 belong to the same(wrong) player.");
        }

        // check Terr1's neighbourList whether contains Terr2
        for (Territory it : t1.neighbours) {
            if (it.getName().equals(Terr2))
                return true;
        }
        return false;
    }
}
