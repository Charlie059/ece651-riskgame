package edu.duke.ece651.shared;

import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private AccountID accountID; // player id
    private int foodResource;
    private int techResource;
    private int currTechLevel;
    private int nextTechLevel;
    private boolean isTechLevelUpgrade;
    private boolean isFirstRound; // deploy
    private boolean isLose; // commit
    private boolean isGameOver;
    private boolean isWon;
    private HashMap<String, Territory> myTerritories;// all territories of the player
    private Map wholeMap;
    private int totalDeployment; // num of units
    private boolean isLoserAsked;
    private boolean isNotDisplay;
    private GameID currentGameID;


    public Player(AccountID _id, GameID currentGameID, Map _map) {
        this.accountID = _id;
        isFirstRound = true;
        isLose = false;
        isGameOver = false;
        isWon = false;
        this.isLoserAsked = false;
        this.isNotDisplay = false;
        this.foodResource = 100;
        this.techResource = 100;
        this.currTechLevel = 1;
        this.isTechLevelUpgrade = false;
        this.currentGameID = currentGameID;
        this.wholeMap = _map;
        this.myTerritories = new HashMap<>();
        this.totalDeployment = this.wholeMap.numOfPlayers * 3;

    }


    public void setCurrentGameID(GameID gameID) {
        currentGameID = gameID;
    }

    public int getCurrentGameID() {
        return currentGameID.getCurrGameID();
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
        this.totalDeployment -= moveUnits;
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
        this.wholeMap.getTerritoryList().get(from_name).removeUnitMultiLevels(moveUnits);
        this.wholeMap.getTerritoryList().get(to_name).addUnitMultiLevels(moveUnits);
        this.foodResource -= totalCost;
    }

    /**
     * temporally set player's nextTechLevel
     * mark player has updated the techlevel
     * @param cost
     */
    public void setUpgradeTech(int cost) {
        //Temp value to restore whether player has upgraded tech
        this.isTechLevelUpgrade = true;
        this.techResource -= cost;
    }

    /**
     * Update Player's techlevel, refresh TechUpgrade status
     * GameRunnable will call this function from Player itself in PlayerHashmap
     */
    public void doUpgradeTech() {
        if (this.isTechLevelUpgrade == true) {
            this.currTechLevel += 1;
            this.isTechLevelUpgrade = false;
        }
    }

    /**
     * temporally upgrade player's units in Territory where
     *
     * @param where
     * @param oldLevel
     * @param newLevel
     */
    public void DoUpgradeUnit(String where, int oldLevel, int newLevel, int techCost) {
        Territory terr = this.myTerritories.get(where);
        this.techResource -= techCost;
        terr.removeUnitLevel(oldLevel, 1, terr.getUnits());
        terr.addUnitLevel(newLevel, 1, terr.getUnits());
    }

////////////////////////////////Helper functions////////////////////////////////////////////////////

    /**
     * helper function: check if input territory name matches names in myTerritories
     *
     * @param terrName
     * @param Territories
     * @return
     */
    public boolean isTerrNameMatch(String terrName, HashMap<String, Territory> Territories) {
        if (Territories.get(terrName) != null) {
            return true;
        }
        return false;
    }

    /**
     * helper function: check if input territory name matches names in myTerritories
     * and the whole map
     *
     * @param terrName
     * @param selfTerritories
     * @param otherTerritories
     * @return
     */
    public boolean isTerrNameMatchForAttack(String terrName, HashMap<String, Territory> selfTerritories,
                                            HashMap<String, Territory> otherTerritories) {
        if (isTerrNameMatch(terrName, selfTerritories)) {
            return false;
        }
        if (otherTerritories.get(terrName) != null) {
            return true;
        }
        return false;
    }


    public void sendUpgradeUnit(String where, int oldLevel, int newLevel) {
        UpgradeUnitsAction UpdateUnits_action = new UpgradeUnitsAction();
        UpdateUnits_action.setWhere(where).
                setOldLevel(oldLevel).
                setNewLevel(newLevel);
    }

    public void sendUpgradeTech(int next_level, int currTechResource) {
        UpgradeTechAction updateTechAction = new UpgradeTechAction();

    }

    /**
     * send player's units to an adjacent territory controlled by a different
     * player, in an attempt to gain control over that territory.
     *
     * @param attackUnits
     * @param from_name
     * @param to_name
     * @return
     */
    public boolean sendAttack(ArrayList<Unit> attackUnits, String from_name, String to_name) {
        try {
            AttackAction attack_action = new AttackAction();
            attack_action.setFrom(from_name).setTo(to_name).setUnits(attackUnits);
        } catch (Exception excep) {
            System.out.println("Attack Error: " + excep.getMessage());
            return false;
        }
        return true;
    }

    /**
     * move units from one territory to another territory
     *
     * @param moveUnits
     * @param from_name
     * @param to_name
     * @return
     */
    public boolean sendMove(ArrayList<ArrayList<Integer>> moveUnits, String from_name, String to_name) {
        try {
            MoveAction move_action = new MoveAction();
            move_action.setFrom(from_name).setTo(to_name).setUnits(moveUnits);

        } catch (Exception excep) {
            System.out.println("Move Error: " + excep.getMessage());
            return false;
        }
        return true;
    }

    public boolean sendDeploy(int numOfDeployedUnits, String to_name) {
        Territory to = myTerritories.get(to_name);
        DeployAction deploy_action = new DeployAction();
        deploy_action.setTo(to_name).setDeployUnits(numOfDeployedUnits);
        return true;
    }

    /////////////////////////////////////getters and setters///////////////////////////////////////////////////
    public Map getWholeMap() {
        return wholeMap;
    }

    public int getTotalDeployment() {
        return this.totalDeployment;
    }

    public void setTotalDeployment(int new_totalDeployment) {
        this.totalDeployment = new_totalDeployment;
    }

    public HashMap<String, Territory> getMyTerritories() {
        return this.myTerritories;
    }

    public int getTechResource() {
        return techResource;
    }

    public int getFoodResource() {
        return foodResource;
    }

    public int getCurrTechLevel() {
        return currTechLevel;
    }

    public boolean isTechLevelUpgrade() {
        return isTechLevelUpgrade;
    }

    /**
     * assign my territories
     */
    public void assignMyTerritories() {
        for (String terr : wholeMap.getTerritoryList().keySet()) {
            //if map owner equals the player's accountID
            if (this.accountID.equals(wholeMap.getTerritoryList().get(terr).getOwnerId())) {
                this.myTerritories.put(terr, wholeMap.getTerritoryList().get(terr));
            }
        }
    }


    /**
     * Helper function for Test
     */
    public void setCurrTechLevel(int currTechLevel) {
        this.currTechLevel = currTechLevel;
    }
}
