package edu.duke.ece651.shared.Wrapper;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.IO.ClientActions.AttackAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Wrapper class to ensure thread safe
 */
public class AttackHashMap {

    private HashMap<AccountID, ArrayList<AttackAction>> attackHashmap;

    // Create an empty hashmap
    public AttackHashMap(){
        this.attackHashmap = new HashMap<>();
    }

    /**
     * Put accountID as key and account as value
     * @param key
     * @param value
     */
    public synchronized void put(AccountID key, ArrayList<AttackAction> value){
        this.attackHashmap.put(key, value);
    }

    /**
     * Get the account by given accountID as key
     * @param key
     * @return Account
     */
    public synchronized ArrayList<AttackAction> get(AccountID key){
        return this.attackHashmap.get(key);
    }

    /**
     * Return the size of the hashmap
     * @return the size of hashmap
     */
    public synchronized int size(){
        return this.attackHashmap.size();
    }


    /**
     * Return the Key Set of hashmap
     * @return Set
     */
    public synchronized Set keySet(){
        return this.attackHashmap.keySet();
    }


    /**
     * Check if hashmap contains key
     * @param accountID
     * @return true if contains key
     */
    public synchronized boolean containsKey(AccountID accountID){
        return this.attackHashmap.containsKey(accountID);
    }


    /**
     * Check if hashmap contains value
     * @return true if contains value
     */
    public synchronized boolean containsValue(Account account){
        return this.attackHashmap.containsValue(account);
    }






}
