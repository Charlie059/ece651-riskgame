package edu.duke.ece651.shared;

import edu.duke.ece651.shared.IO.ClientActions.AttackAction;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.AttackHashMap;
import edu.duke.ece651.shared.Wrapper.CommittedHashMap;
import edu.duke.ece651.shared.Wrapper.PlayerHashMap;
import edu.duke.ece651.shared.map.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    // All player joined this game
    private PlayerHashMap playerHashMap;
    private Integer numOfPlayer;
    private AttackHashMap attackHashMap;
    private volatile CommittedHashMap committedHashMap;
    private volatile Boolean gameOver;
    private volatile Boolean isCombatFinished;
    private volatile Boolean isBegin;

    private Map map;

    public Game(Integer numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
        this.playerHashMap = new PlayerHashMap();
        this.attackHashMap = new AttackHashMap();
        this.committedHashMap = new CommittedHashMap();
        this.gameOver = false;
        this.isCombatFinished = false;
        this.isBegin = false;
        this.map = new Map(numOfPlayer);
    }

    public void setOwnership(AccountID accountId){
        ArrayList<ArrayList<String>> group = this.map.getGroups();
        int groupNum = this.playerHashMap.size()-1;
        for(String terrName: group.get(groupNum)){
            this.map.getTerritoryList().get(terrName).setOwner(accountId);
        }
    }

    public synchronized PlayerHashMap getPlayerHashMap() {
        return playerHashMap;
    }


    public synchronized Integer getNumOfPlayer() {
        return numOfPlayer;
    }

    public synchronized AttackHashMap getAttackHashMap() {
        return attackHashMap;
    }

    public synchronized Boolean getGameOver() {
        return gameOver;
    }

    public synchronized void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public synchronized Boolean getCombatFinished() {
        return isCombatFinished;
    }

    public synchronized void setCombatFinished(Boolean combatFinished) {
        isCombatFinished = combatFinished;
    }

    public synchronized Boolean getBegin() {
        return isBegin;
    }

    public synchronized void setBegin(Boolean begin) {
        isBegin = begin;
    }

    public synchronized CommittedHashMap getCommittedHashMap() {
        return committedHashMap;
    }

    public Map getMap() {
        return this.map;
    }

}
