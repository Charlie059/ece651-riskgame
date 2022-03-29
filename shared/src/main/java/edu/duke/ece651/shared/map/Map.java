package edu.duke.ece651.shared.map;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Map {
    public int numOfPlayers; // number of player = 3
    private MapFactory myMapFactory;
    HashMap<String, Territory> territoryList;
    ArrayList<ArrayList<String>> groups; // initial territory groups


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
        assignInitialID();

    }

    public HashMap<String, Territory> getTerritoryList() {
        return territoryList;
    }

    public ArrayList<ArrayList<String>> getGroups() {
        return groups;
    }

    /**
     * assign initial player IDs to each Territory
     */
    public void assignInitialID(){
        for(int i = 0; i < groups.size(); i++){
            ArrayList<String> name_arr = groups.get(i);
            for(int j = 0; j < name_arr.size(); j++){
                territoryList.get(name_arr.get(j)).changeOwner(i+1);
            }
        }
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

        if (terrSrc.getOwnerId() != playerID || terrDst.getOwnerId() != playerID) {
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

            for (Territory t : cur_node.getNeighbour()) {
                if (visited.contains(t) == false && t.getOwnerId() == playerID) {
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

        if (t1.getOwnerId() != playerID || t2.getOwnerId() == playerID) {
            throw new IllegalArgumentException("Terr1 and Terr2 belong to the same(wrong) player.");
        }

        // check Terr1's neighbourList whether contains Terr2
        for (Territory it : t1.getNeighbour()) {
            if (it.getName().equals(Terr2))
                return true;
        }

        return false;
    }

    /**
     * display map information of the map for client.
     * @param displayer
     * @return null
     */
    public void displayMap(MapView displayer){
        displayer.generateViewInfo(territoryList);
        displayer.display();
    }
}

// visitor design pattern for display

