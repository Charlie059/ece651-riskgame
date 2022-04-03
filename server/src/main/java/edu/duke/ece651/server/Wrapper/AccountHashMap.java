package edu.duke.ece651.server.Wrapper;

import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.HashMap;
import java.util.Set;

/**
 * Wrapper class to ensure thread safe
 */
public class AccountHashMap {

    private HashMap<AccountID, Account> accountHashMap;

    // Create an empty hashmap
    public AccountHashMap(){
        this.accountHashMap = new HashMap<>();
    }

    /**
     * Put accountID as key and account as value
     * @param key
     * @param value
     */
    public synchronized void put(AccountID key, Account value){
        this.accountHashMap.put(key, value);
    }

    /**
     * Get the account by given accountID as key
     * @param key
     * @return Account
     */
    public synchronized Account get(AccountID key){
        return this.accountHashMap.get(key);
    }

    /**
     * Return the size of the hashmap
     * @return the size of hashmap
     */
    public synchronized int size(){
        return this.accountHashMap.size();
    }


    /**
     * Return the Key Set of hashmap
     * @return Set
     */
    public synchronized Set keySet(){
        return this.accountHashMap.keySet();
    }


    /**
     * Check if hashmap contains key
     * @param accountID
     * @return true if contains key
     */
    public synchronized boolean containsKey(AccountID accountID){
        return this.accountHashMap.containsKey(accountID);
    }


    /**
     * Check if hashmap contains value
     * @return true if contains value
     */
    public synchronized boolean containsValue(Account account){
        return this.accountHashMap.containsValue(account);
    }






}
