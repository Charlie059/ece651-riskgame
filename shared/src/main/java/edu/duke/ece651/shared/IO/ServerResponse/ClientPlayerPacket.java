package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientPlayerPacket implements Serializable {
    private GameID currentGameID;
    private AccountID accountID;
    private int numOfPlayers;
    private int foodResource;
    private int techResource;
    private int techLevel;
    private int totalDeployment; // num of units
    private Boolean isLose;
    private Boolean isWin;
    private HashMap<String, Territory> myTerritories;// all territories of the player

    private HashMap<String, ArrayList<String>> enemyTerritories; // HashMap<String -> AccountID String, ArrayList<String> -> ArrayList of Territories>


    public ClientPlayerPacket(GameID currentGameID, AccountID accountID, int numOfPlayers, int foodResource, int techResource, int techLevel, int totalDeployment, HashMap<String, Territory> myTerritories, HashMap<String, ArrayList<String>> enemyTerritories, Boolean isLose, Boolean isWin) {
        this.currentGameID = currentGameID;
        this.accountID = accountID;
        this.numOfPlayers = numOfPlayers;
        this.foodResource = foodResource;
        this.techResource = techResource;
        this.techLevel = techLevel;
        this.totalDeployment = totalDeployment;
        this.myTerritories = myTerritories;
        this.enemyTerritories = enemyTerritories;
        this.isLose = isLose;
        this.isWin = isWin;
    }


    public GameID getCurrentGameID() {
        return currentGameID;
    }

    public AccountID getAccountID() {
        return accountID;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public int getFoodResource() {
        return foodResource;
    }

    public int getTotalDeployment() {
        return totalDeployment;
    }

    public int getTechResource() {
        return techResource;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public HashMap<String, Territory> getMyTerritories() {
        return myTerritories;
    }

    public HashMap<String, ArrayList<String>> getEnemyTerritories() {
        return enemyTerritories;
    }

    public Boolean getLose() {
        return isLose;
    }

    public Boolean getWin() {
        return isWin;
    }

    /**
     * temporally do deploy on player's map
     *
     * @param to
     * @param moveUnits
     */
    public void doDeploy(String to, int moveUnits) {
        Territory to_terr = this.getMyTerritories().get(to);
        to_terr.addUnitLevel(0, moveUnits, to_terr.getUnits());
    }

    /**
     * temporally update player's move action to player's own map
     *
     * @param moveUnits
     * @param from_name
     * @param to_name
     * @return
     */
    public void doMove(String from_name, String to_name, ArrayList<ArrayList<Integer>> moveUnits, int totalCost) {
        this.myTerritories.get(from_name).removeUnitMultiLevels(moveUnits);
        this.myTerritories.get(to_name).addUnitMultiLevels(moveUnits);
        this.foodResource -= totalCost;
    }

    /**
     * temporally upgrade player's techLevel
     *
     * @param next_level
     * @param cost
     */
    public void doUpgradeTech(int next_level, int cost) {
        this.techLevel = next_level;
        this.techResource -= cost;
    }

    /**
     * temporally upgrade player's units in Territory where
     *
     * @param where
     * @param oldLevel
     * @param newLevel
     */
    public void doUpgradeUnit(String where, int oldLevel, int newLevel, int techCost) {
        Territory terr = this.myTerritories.get(where);
        this.techResource -= techCost;
        terr.removeUnitLevel(oldLevel, 1, terr.getUnits());
        terr.addUnitLevel(newLevel, 1, terr.getUnits());
    }

    /**
     * player temporarily reduces units in territory to and reduce food resource
     *
     * @param from_name
     * @param to_name
     * @param attackUnits
     * @param totalCost
     */
    public void doAttack(String from_name, String to_name, ArrayList<ArrayList<Integer>> attackUnits, int totalCost) {
        this.getMyTerritories().get(from_name).removeUnitMultiLevels(attackUnits);
        this.foodResource -= totalCost;
    }


}
