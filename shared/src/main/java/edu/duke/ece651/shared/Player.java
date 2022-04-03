package edu.duke.ece651.shared;

import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.*;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private AccountID accountID; // player id
    private int numOfPlayers;
    private int foodResource;
    private int techResource;
    private int currTechLevel;
    private int nextTechLevel;
    private boolean isTechUpgraded;
    private boolean isFirstRound; // deploy
    private boolean isLose; // commit
    private boolean isGameOver;
    private boolean isWon;
    private HashMap<String, Territory> myTerritories;// all territories of the player
    private Map wholeMap;
    private int totalDeployment; // num of units
    public MapView myMapTextView;
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
        this.isTechUpgraded = false;
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

    public void playerMakeChoice(BufferedReader inputReader, PrintStream out) throws Exception {
        String choice;
        while (true) {
            out.println("What would you like to do?");
            out.println("(M)ove\n(A)ttack\n(UT) upgrade Tech\n(U)pgrade Unit\n(D)one");
            choice = inputReader.readLine();

            if (choice.equals("M")) {
                // TODO: MOVE
            } else if (choice.equals("A")) {
                // TODO: Attack
            } else if (choice.equals("UT")) {
                // TODO: Upgrade Tech Level
            } else if (choice.equals("U")) {
                //TODO: Upgrade Unit Level
            } else {
                // TODO: Commit
                break;
            }
        }
        return;
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
        this.wholeMap.getTerritoryList().get(from_name).removeUnitMultiLevels(moveUnits);
        this.wholeMap.getTerritoryList().get(to_name).addUnitMultiLevels(moveUnits);
        this.foodResource -= totalCost;
    }

    /**
     * temporally set player's nextTechLevel
     * mark player has updated the techlevel
     * @param next_level
     * @param cost
     */
    public void setUpgradeTech(int next_level, int cost) {
        this.nextTechLevel = next_level;
        this.isTechUpgraded = true;
        this.techResource -= cost;
    }

    /**
     * Update Player's techlevel, refresh TechUpgrade status
     * GameRunnable will call this function from Player itself in PlayerHashmap
     */
    public void doUpgradeTech()
    {
        this.currTechLevel = this.nextTechLevel;
        this.isTechUpgraded = false;
    }
    /**
     * temporally upgrade player's units in Territory where
     *
     * @param where
     * @param unitsToUpgrade
     */
    public void DoUpgradeUnit(String where, ArrayList<ArrayList<Integer>> unitsToUpgrade) {
        Territory terr = this.myTerritories.get(where);
        for (int i = 0; i < unitsToUpgrade.size(); i++) {
            int level = unitsToUpgrade.get(i).get(0);
            int num = unitsToUpgrade.get(i).get(1);
            //max level: 6, cannot upgrade level-6 units
            if (level < 6) {
                terr.removeUnitLevel(level, num, terr.getUnits());
                terr.addUnitLevel(level, num, terr.getUnits());
            }
        }
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


    public void sendUpgradeUnit(String where, ArrayList<ArrayList<Integer>> unitsToUpgrade) {
        UpdateUnitsAction UpdateUnits_action = new UpdateUnitsAction();
        UpdateUnits_action.setWhere(where).
                setUnitsToUpgrade(unitsToUpgrade);
    }

    public void sendUpgradeTech(int next_level, int currTechResource) {
        UpdateTechAction updateTechAction = new UpdateTechAction();
        updateTechAction.setNextLevel(next_level).
                setCurrTechResource(currTechResource);
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

    public Boolean isTechUpgraded(){
        return this.isTechUpgraded;
    }
}
