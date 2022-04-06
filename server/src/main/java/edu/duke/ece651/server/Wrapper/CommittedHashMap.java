package edu.duke.ece651.server.Wrapper;

import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.HashMap;
import java.util.Set;


public class CommittedHashMap {

    private HashMap<AccountID, Boolean> committedHashmap;

    // Create an empty hashmap
    public CommittedHashMap(){
        this.committedHashmap = new HashMap<>();
    }

    /**
     * Put accountID as key and account as value
     * @param key
     * @param value
     */
    public synchronized void put(AccountID key, Boolean value){
        this.committedHashmap.put(key, value);
    }

    /**
     * Get the account by given accountID as key
     * @param key
     * @return Account
     */
    public synchronized Boolean get(AccountID key){
        return this.committedHashmap.get(key);
    }

    /**
     * Return the size of the hashmap
     * @return the size of hashmap
     */
    public synchronized int size(){
        return this.committedHashmap.size();
    }


    /**
     * Return the Key Set of hashmap
     * @return Set
     */
    public synchronized Set keySet(){
        return this.committedHashmap.keySet();
    }


    /**
     * Check if hashmap contains key
     * @param accountID
     * @return true if contains key
     */
    public synchronized boolean containsKey(AccountID accountID){
        return this.committedHashmap.containsKey(accountID);
    }


    /**
     * Check if hashmap contains value
     * @return true if contains value
     */
    public synchronized boolean containsValue(Boolean value){
        return this.committedHashmap.containsValue(value);
    }


    /**
     * Reset the committed hashmap's each filed to false
     */
    public synchronized void resetCommittedHashmap(){
            for (AccountID key : this.committedHashmap.keySet()) {
                this.committedHashmap.put(key, false);
            }
    }



}