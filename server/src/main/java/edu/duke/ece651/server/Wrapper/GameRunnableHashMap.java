package edu.duke.ece651.server.Wrapper;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.GameRunnable;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GameRunnableHashMap {
    private HashMap<GameID, GameRunnable> gameRunnableHashMap;

    // Create an empty hashmap
    public GameRunnableHashMap() {
        this.gameRunnableHashMap = new HashMap<>();
    }

    /**
     * Put GameID as key and GameRunnable as value
     *
     * @param key
     * @param value
     */
    public synchronized void put(GameID key, GameRunnable value) {
        this.gameRunnableHashMap.put(key, value);
    }

    /**
     * Get the game by given GameRunnable as key
     *
     * @param key
     * @return Game
     */
    public synchronized GameRunnable get(GameID key) {
        return this.gameRunnableHashMap.get(key);
    }

}
