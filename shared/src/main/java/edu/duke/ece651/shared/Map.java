package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    public int numOfPlayers; // number of player = 3
    MapFactory myMapFactory;
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
    public boolean isPathExist(int playerID, String from, String to) {
        return true;
    }

    /**
     * Used in Attack action, check if the two territories (with names "Terr1" and
     * "Terr2") are neighbors and belong to different players. ? 
     * 
     * @param playerID
     * @param Terr1
     * @param Terr2
     * @return
     */
    public boolean isAdjacent(int playerID, String Terr1, String Terr2) {
        return true;
    }
}
