package edu.duke.ece651.server.Wrapper;

import edu.duke.ece651.server.Game;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Wrapper class to ensure thread safe
 */
public class GameHashMap {

    private HashMap<GameID, Game> gameHashMap;

    // Create an empty hashmap
    public GameHashMap() {
        this.gameHashMap = new HashMap<>();
    }

    /**
     * Put GameID as key and Game as value
     *
     * @param key
     * @param value
     */
    public synchronized void put(GameID key, Game value) {
        this.gameHashMap.put(key, value);
    }

    /**
     * Get the game by given GameID as key
     *
     * @param key
     * @return Game
     */
    public synchronized Game get(GameID key) {
        return this.gameHashMap.get(key);
    }

    /**
     * Return the size of the hashmap
     *
     * @return the size of hashmap
     */
    public synchronized int size() {
        return this.gameHashMap.size();
    }


    /**
     * Return the Key Set of hashmap
     *
     * @return Set
     */
    public synchronized Set keySet() {
        return this.gameHashMap.keySet();
    }


    /**
     * Check if hashmap contains key
     *
     * @param gameID
     * @return true if contains key
     */
    public synchronized boolean containsKey(GameID gameID) {
        return this.gameHashMap.containsKey(gameID);
    }


    /**
     * Check if hashmap contains value
     *
     * @return true if contains value
     */
    public synchronized boolean containsValue(Game game) {
        return this.gameHashMap.containsValue(game);
    }


    /**
     * Find all open game list
     * @return ArrayList<GameID> with open gameID
     */
    public synchronized ArrayList<GameID> findOpenGameList() {
        ArrayList<GameID> gameIDArrayList = new ArrayList<>();
        for (GameID key : this.gameHashMap.keySet()) {
            Game game = this.gameHashMap.get(key);
            //If Game open
            if (game.getPlayerHashMap().size() < game.getNumOfPlayer()) {
                gameIDArrayList.add(key);
            }
        }
        return gameIDArrayList;
    }


    /**
     * Iterate the gameHashmap and find if contains specific accountID and return arrayList
     * @param accountID
     * @return ArrayList<GameID>
     */
    public synchronized ArrayList<GameID> findGameContainsKey(AccountID accountID){
            ArrayList<GameID>  gameIDArrayList = new ArrayList<>();
            for (GameID key : this.gameHashMap.keySet()) {
                Game game = this.gameHashMap.get(key);
                // If the game contains current accountID
                if (game.getPlayerHashMap().containsKey(accountID)) {
                    gameIDArrayList.add(key);
                }
            }
            return gameIDArrayList;
    }





}
