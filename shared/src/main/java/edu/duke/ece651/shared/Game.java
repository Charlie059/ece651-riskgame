package edu.duke.ece651.shared;

import java.util.HashMap;

public class Game {
    private  HashMap<String, Player> playerHashMap;//PlayerID, Player

    public Game() {
        this.playerHashMap = new HashMap<>();
    }

    public HashMap<String, Player> getPlayerHashMap() {
        return playerHashMap;
    }
}
