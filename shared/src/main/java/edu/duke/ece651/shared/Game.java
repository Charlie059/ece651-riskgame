package edu.duke.ece651.shared;

import java.util.HashMap;

public class Game {
    private  HashMap<String, Player> playerHashMap;//PlayerID, Player
    private Integer numOfPlayer;
    public Game(Integer numOfPlayer) {
        this.playerHashMap = new HashMap<>();
        this.numOfPlayer = numOfPlayer;
    }

    public HashMap<String, Player> getPlayerHashMap() {
        return playerHashMap;
    }

    public Integer getNumOfPlayer() {
        return numOfPlayer;
    }
}
