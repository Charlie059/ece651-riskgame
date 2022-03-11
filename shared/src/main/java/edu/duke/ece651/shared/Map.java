package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

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
        Territory territorySrc = territoryList.get(from);
        Territory territoryDst = territoryList.get(to);

        if(territorySrc.getOwner() != playerID ||  territoryDst.getOwner() != playerID){
            throw new IllegalArgumentException("territory from and territory to belong to different(wrong) player.");
        }
            
        //todo: search a path from A to B using adjacentlist (DFS)

        return true;
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

        if(t1.getOwner() != playerID || t2.getOwner() == playerID){
            throw new IllegalArgumentException("Terr1 and Terr2 belong to the same(wrong) player.");
        }
        
        // check Terr1's neighbourList whether contains Terr2
        for (Territory it : t1.neighbours) {
            if(it.getName().equals(Terr2))
                return true;   
        }
        return false;
    }
}
