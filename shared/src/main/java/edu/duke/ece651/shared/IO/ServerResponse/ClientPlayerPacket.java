package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientPlayerPacket {
    private GameID currentGameID;
    private AccountID accountID;
    private int numOfPlayers;
    private int foodResource;
    private int techResource;
    private int techLevel;
    private int totalDeployment; // num of units
    private HashMap<String, Territory> myTerritories;// all territories of the player

    private HashMap<String, ArrayList<String>> enemyTerritories; // HashMap<String -> AccountID String, ArrayList<String> -> ArrayList of Territories>



    public ClientPlayerPacket(GameID currentGameID, AccountID accountID, int numOfPlayers, int foodResource, int techResource, int techLevel, int totalDeployment, HashMap<String, Territory> myTerritories, HashMap<String, ArrayList<String>> enemyTerritories) {
        this.currentGameID = currentGameID;
        this.accountID = accountID;
        this.numOfPlayers = numOfPlayers;
        this.foodResource = foodResource;
        this.techResource = techResource;
        this.techLevel = techLevel;
        this.totalDeployment = totalDeployment;
        this.myTerritories = myTerritories;
        this.enemyTerritories = enemyTerritories;
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
    /**
     * temporally do deploy on player's map
     * @param to
     * @param moveUnits
     */
    public void DoDeploy(String to, int moveUnits){
        Territory to_terr = this.getMyTerritories().get(to);
        to_terr.addUnitLevel(0, moveUnits, to_terr.getUnits());
    }

    /**
     * temporally update player's move action to player's own map
     *
     * @param moveUnits
     * @param from_name
     * @return
     */
    public void DoMove(String from_name, ArrayList<ArrayList<Integer>> moveUnits, int totalCost) {
        this.myTerritories.get(from_name).removeUnitMultiLevels(moveUnits);
        this.myTerritories.get(from_name).addUnitMultiLevels(moveUnits);
        this.foodResource -= totalCost;
    }

    /**
     * temporally upgrade player's techLevel
     * @param next_level
     * @param cost
     */
    public void DoUpgradeTech(int next_level, int cost){
        this.techLevel = next_level;
        this.techResource -= cost;
    }

    /**
     * temporally upgrade player's units in Territory where
     * @param where
     * @param unitsToUpgrade
     */
    public void DoUpgradeUnit(String where, ArrayList<ArrayList<Integer>> unitsToUpgrade){
        Territory terr = this.myTerritories.get(where);
        for(int i = 0; i < unitsToUpgrade.size(); i++){
            int level = unitsToUpgrade.get(i).get(0);
            int num = unitsToUpgrade.get(i).get(1);
            //max level: 6, cannot upgrade level-6 units
            if (level < 6) {
                terr.removeUnitLevel(level, num, terr.getUnits());
                terr.addUnitLevel(level, num, terr.getUnits());
            }
        }
    }


    /**
     * player temporarily reduces units in territory to and reduce food resource
     * @param from_name
     * @param to_name
     * @param attackUnits
     * @param totalCost
     */
    public void doAttack(String from_name, String to_name, ArrayList<ArrayList<Integer>> attackUnits, int totalCost){
        this.getMyTerritories().get(from_name).removeUnitMultiLevels(attackUnits);
        this.foodResource -= totalCost;
    }


}
