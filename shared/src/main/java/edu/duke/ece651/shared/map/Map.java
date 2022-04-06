package edu.duke.ece651.shared.map;

import edu.duke.ece651.shared.Wrapper.AccountID;

import java.io.PrintStream;
import java.util.*;

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

    }

    public synchronized HashMap<String, Territory> getTerritoryList() {
        return territoryList;
    }
    public synchronized ArrayList<ArrayList<String>> getGroups() {
        return groups;
    }


    /**
     * Used in Move action, check if there is a path between the two
     * territories(with names "from" and "to") of the player (playerID).
     *
     * @param accountID
     * @param from
     * @param to
     * @return return true if path existed, else return false.
     */
    public boolean isPathExist(AccountID accountID, String from, String to) throws IllegalArgumentException {

        Territory terrSrc = territoryList.get(from);
        Territory terrDst = territoryList.get(to);

        if ((!terrSrc.getOwnerId().equals(accountID)) || (!terrDst.getOwnerId().equals(accountID))) {
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
                if (visited.contains(t) == false && t.getOwnerId().equals(accountID)) {
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
     * @param accountID
     * @param Terr1
     * @param Terr2
     * @return check result
     */
    public boolean isAdjacent(AccountID accountID, String Terr1, String Terr2) throws IllegalArgumentException {
        Territory t1;
        Territory t2;
         t1 = territoryList.get(Terr1);
         t2 = territoryList.get(Terr2);
        if (t1 == null || t2 == null){
            return false;
        }
        if (!(t1.getOwnerId().equals(accountID)) || t2.getOwnerId().equals(accountID)) {
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
     * Dijkstra's Algorithm
     * @param start
     * @return
     */
    public int shortestPathFrom(AccountID accountID, String start, String to){
        Territory start_terr = this.territoryList.get(start);
        //HashMap<String, Territory.Node> res = new HashMap<>();
        int res = -1;
        PriorityQueue<Territory> pQueue = new PriorityQueue<>();//min-heap
        //add all territory of the graph to the priority queue
        for(Territory terr: this.territoryList.values()){
            //same reference to the start territory
            if (terr.getOwnerId().equals(accountID)) {
                if (terr.equals(start_terr)) {
                    terr.setDist(0);
                } else {
                    terr.setDist(Integer.MAX_VALUE);
                }
                terr.setPrev(null);
                pQueue.add(terr);
            }
        }
        //loop while there are vertices left to visit
        while(!pQueue.isEmpty()){
            //find the next vertex to visit
            Territory u = pQueue.poll();
            //Territory.Node n = new Territory.Node(u.getName(), u.getDist(), u.getPrev());
            if (u.getName().equals(to)){
                res = u.getDist();
                return res;
            }
            //check each neighbors of u
            //update predictions and previous vertex
            for(Territory neigh: u.getNeighbour()){
                if (neigh.getOwnerId().equals(accountID) && neigh.getDist() > u.getDist() + neigh.getCost()){
                    neigh.setDist(u.getDist() + neigh.getCost());
                    neigh.setPrev(u.getName());
                }
            }
        }
        return res;
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

