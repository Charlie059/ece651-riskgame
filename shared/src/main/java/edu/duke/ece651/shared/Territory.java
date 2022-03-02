package edu.duke.ece651.shared;

import java.util.ArrayList;

public class Territory {
    private String name;
    private int ownerId;
    final ArrayList<String> neighbours; // store neighbour territory's name. 怎么设置成不变的常量，使得后续操作不能再修改？

    public Territory(String name, ArrayList<String> neighbourList) {
        this.name = name;
        this.ownerId = -1;
        this.neighbours = neighbourList;
    }

    // create a ioslated territory for testing
    public Territory(String name) {
        this.name = name;
        this.ownerId = -1;
        this.neighbours = new ArrayList<String>();
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