package edu.duke.ece651.shared;

import java.util.ArrayList;

public class Territory {
    private String name;
    private int ownerId;
    final ArrayList<Territory> neighbours; 

    public Territory(String name, ArrayList<Territory> neighbourList) {
        this.name = name;
        this.ownerId = -1;
        this.neighbours = neighbourList;
    }

    // create a ioslated territory
    public Territory(String name) {
        this.name = name;
        this.ownerId = -1;
        this.neighbours = new ArrayList<Territory>();
    }

    void addNeighbour(Territory t){
        neighbours.add(t);
    }

    public String getName() {
        return name;
    }

    public void setOwner(int playerId) {
        ownerId = playerId;
    }

    public int getOwner() {
        return ownerId;
    }

}