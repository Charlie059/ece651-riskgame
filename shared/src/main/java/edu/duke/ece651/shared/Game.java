package edu.duke.ece651.shared;

import edu.duke.ece651.shared.IO.ClientActions.AttackAction;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    // All player joined this game
    private  HashMap<AccountID, Player> playerHashMap;
    private Integer numOfPlayer;
    private HashMap<AccountID, ArrayList<AttackAction>> attackHashMap;
    private volatile Boolean gameOver;
    private volatile Boolean isCombatFinished;
    private volatile Boolean isBegin;
    private volatile HashMap<AccountID, Boolean> committedHashMap;


    public Game(Integer numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
        this.playerHashMap = new HashMap<>();
        this.attackHashMap = new HashMap<>();
        this.gameOver = false;
        this.isCombatFinished =false;
        this.isBegin = false;
        this.committedHashMap = new HashMap<>();
    }

    public HashMap<AccountID, Player> getPlayerHashMap() {
        return playerHashMap;
    }

    public synchronized void setPlayerHashMap(HashMap<AccountID, Player> playerHashMap) {
        this.playerHashMap = playerHashMap;
    }

    public Integer getNumOfPlayer() {
        return numOfPlayer;
    }

    public HashMap<AccountID, ArrayList<AttackAction>> getAttackHashMap() {
        return attackHashMap;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public synchronized void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Boolean getCombatFinished() {
        return isCombatFinished;
    }

    public synchronized void setCombatFinished(Boolean combatFinished) {
        isCombatFinished = combatFinished;
    }

    public Boolean getBegin() {
        return isBegin;
    }

    public synchronized void setBegin(Boolean begin) {
        isBegin = begin;
    }

    public HashMap<AccountID, Boolean> getCommittedHashMap() {
        return committedHashMap;
    }



}
