package edu.duke.ece651.server.Wrapper;

import edu.duke.ece651.server.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PlayerHashMap {

    private HashMap<AccountID, Player> playerHashMap;

    // Create an empty hashmap
    public PlayerHashMap() {
        this.playerHashMap = new HashMap<>();
    }

    /**
     * Put AccountID as key and Player as value
     *
     * @param key
     * @param value
     */
    public synchronized void put(AccountID key, Player value) {
        this.playerHashMap.put(key, value);
    }

    /**
     * Get the Player by given AccountID as key
     *
     * @param key
     * @return Player
     */
    public synchronized Player get(AccountID key) {
        return this.playerHashMap.get(key);
    }

    /**
     * Return the size of the hashmap
     *
     * @return the size of hashmap
     */
    public synchronized int size() {
        return this.playerHashMap.size();
    }


    /**
     * Return the Key Set of hashmap
     *
     * @return Set
     */
    public synchronized Set keySet() {
        return this.playerHashMap.keySet();
    }


    public HashMap<AccountID, Player> getPlayerHashMap() {
        return playerHashMap;
    }

    /**
     * Check if hashmap contains key
     *
     * @param accountID
     * @return true if contains key
     */
    public synchronized boolean containsKey(AccountID accountID) {
        return this.playerHashMap.containsKey(accountID);
    }


    /**
     * Check if hashmap contains value
     *
     * @return true if contains value
     */
    public synchronized boolean containsValue(Player player) {
        return this.playerHashMap.containsValue(player);
    }

    /**
     * Update all player's Tech Level
     */
    public void updatePlayersTechLevel() {
        for (AccountID key : this.playerHashMap.keySet()) {
            this.playerHashMap.get(key).doUpgradeTech();
        }
    }

    /**
     * @param myAccountID
     * @return
     */
    public HashMap<String, ArrayList<String>> getEnemyTerritories(AccountID myAccountID) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        for (AccountID key : this.playerHashMap.keySet()) {
            if (key != myAccountID) {
                ArrayList<String> territoryNameList = new ArrayList<>();
                for (String territoryName : this.playerHashMap.get(key).getMyTerritories().keySet()) {
                    territoryNameList.add(territoryName);
                }
                result.put(key.getAccountID(), territoryNameList);
            }
        }
        return result;
    }

}
