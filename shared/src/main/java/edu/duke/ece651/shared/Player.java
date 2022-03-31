package edu.duke.ece651.shared;

import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.map.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String playerId; // player id
    private int numOfPlayers;
    private int foodResource;
    private int techResource;
    private int techLevel;
    private BufferedReader inputReader;
    private PrintStream out;
    private boolean isFirstRound; // deploy
    private boolean isLose; // commit
    private boolean isGameOver;
    private boolean isWon;
    private HashMap<String, Territory> myTerritories;// all territories of the player
    private Map wholeMap;
    private HashMap<Integer, Integer> totalDeployment; // totalUnits: key: level, value: num of units
    private ArrayList<Action> ActionList;// list of actions
    public MapView myMapTextView;
    private boolean isLoserAsked;
    private boolean isNotDisplay;
    private int currentGameID;


    public Player(String _id, int _numOfPlayer, BufferedReader _in, PrintStream _out) {
        playerId = _id;
        numOfPlayers = _numOfPlayer;
        InputStreamReader myReader = new InputStreamReader(System.in);
        isFirstRound = true;
        isLose = false;
        isGameOver = false;
        isWon = false;
        myTerritories = new HashMap<>();
        wholeMap = new Map(numOfPlayers);

        ActionList = new ArrayList<>();
        inputReader = _in;
        out = _out;
        this.myMapTextView = new MapTextView(numOfPlayers, out);
        this.isLoserAsked = false;
        this.isNotDisplay = false;
        this.foodResource = 100;
        this.techResource = 100;
        this.techLevel = 1;
    }

    public void setCurrentGameID(int gameID){
        currentGameID = gameID;
    }

    public int getCurrentGameID(){
        return currentGameID;
    }

    public void playerMakeChoice(BufferedReader inputReader, PrintStream out) throws Exception {
        String choice;
        while (true) {
            out.println("You are Player " + playerId + ", what would you like to do?");
            out.println("(M)ove\n(A)ttack\n(UT) upgrade Tech\n(U)pgrade Unit\n(D)one");
            choice = inputReader.readLine();

            if (choice.equals("M")) {
                // TODO: MOVE
                playerDoMove(inputReader, out);
                wholeMap.displayMap(myMapTextView);
            } else if (choice.equals("A")) {
                // TODO: Attack
                playerDoAttack(inputReader, out);

            }else if (choice.equals("UT")){
                // TODO: Upgrade Tech Level
                playerDoUpgradeTech(inputReader, out);
            }
            else if(choice.equals("U")){
                //TODO: Upgrade Unit Level
                playerDoUpgradeUnit(inputReader, out);
            }
            else {
                // TODO: Commit
                break;
            }
        }
        return;
    }

    public void playerDoDeploy(BufferedReader inputReader, PrintStream out) throws IOException, Exception {
        // player set the number of units
        for (String t : myTerritories.keySet()) {
            out.print(t + "\t");
        }
        out.print("\n");
        //TODO: receive deployUnits from GUI
        Integer unitNum = null;
        Integer level = 0;
        boolean isNumUnitsValid = false;

        for (String terrName : this.myTerritories.keySet()) {
            if (totalDeployment.get(level) == 0) {
                break;
            }
            try {
                int num = Integer.parseInt(inputReader.readLine());
                unitNum = num;
                if (totalDeployment.get(level) >= unitNum && unitNum >= 0) {
                    isNumUnitsValid = true;
                    // totalDeployment.replace(level, totalDeployment.get(level) - unitNum);
                    out.println(
                            "you have deployed " + unitNum + " level-" + level + " units to " + "Territory " + terrName + "!");
                } else {
                    out.println("Deployment Error: Number of level-" + level
                            + " units cannot be less than o or exceed the total " + "number of deployment");
                }

            } catch (NumberFormatException e) {
                isNumUnitsValid = false;
                out.println("Deployment Error: " + e.getMessage());
            }
            ArrayList<Unit> deployUnits = new ArrayList<>();
            deploy(unitNum, terrName, deployUnits);
        }
    }

    public boolean deploy(int numOfDeployedUnits, String to_name, ArrayList<Unit> deployUnits) {
        Territory to = myTerritories.get(to_name);

        try {
            DeployAction deploy_action = new DeployAction();
            deploy_action.setGameID(currentGameID).setTo(to_name).setUnits(deployUnits).setPlayerID(playerId);
            totalDeployment.replace(1, totalDeployment.get(1) - numOfDeployedUnits);
            ActionList.add(deploy_action);
        } catch (Exception excep) {
            out.println("Deployment Error: " + excep.getMessage());
            return false;
        }
        return true;
    }

    /**
     * player inputs parameters of move action
     *
     * @param inputReader
     * @param out
     * @throws IOException
     */

    public void playerDoMove(BufferedReader inputReader, PrintStream out) throws IOException {
        String from_name, to_name;
        ArrayList<Unit> moveUnits = new ArrayList<>();

        Integer level;
        Integer unitNum;
        //from name
        from_name = inputReader.readLine();
        //to name
        to_name = inputReader.readLine();
        //level
        int l = Integer.parseInt(inputReader.readLine());
        level = l;
        //num
        int num = Integer.parseInt(inputReader.readLine());
        unitNum = num;
        move(moveUnits, from_name, to_name);
    }
    /**
     * move units from one territory to another territory
     *
     * @param moveUnits
     * @param from_name
     * @param to_name
     * @return
     */
    public boolean move(ArrayList<Unit> moveUnits, String from_name, String to_name) {
        try {
            //Territory from = myTerritories.get(from_name);
            //Territory to = myTerritories.get(to_name);
            MoveAction move_action = new MoveAction();
            move_action.setFrom(from_name).setTo(to_name).setUnits(moveUnits).
                    setGameID(currentGameID).setPlayerID(playerId);

            ActionList.add(move_action);
        } catch (Exception excep) {
            System.out.println("Move Error: " + excep.getMessage());
            return false;
        }
        return true;
    }


    /**
     * Receive player's inputs for attack action
     *
     * @param inputReader
     * @param out
     * @throws IOException
     */
    public void playerDoAttack(BufferedReader inputReader, PrintStream out) throws IOException {
        String from_name, to_name;
        ArrayList<Unit> attackUnits = new ArrayList<>();
        // player chooses to_name
        Integer level;
        Integer unitNum;
        from_name = inputReader.readLine();
        int l = Integer.parseInt(inputReader.readLine());
        level = l;
        int num = 0;
        unitNum = num;
        to_name = inputReader.readLine();
        attack(attackUnits, from_name, to_name);
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
    public boolean attack(ArrayList<Unit> attackUnits, String from_name, String to_name) {
        try {
            AttackAction attack_action = new AttackAction();
            attack_action.setFrom(from_name).setTo(to_name).setUnits(attackUnits).
                    setGameID(currentGameID).setPlayerID(playerId);
            ActionList.add(attack_action);
        } catch (Exception excep) {
            System.out.println("Attack Error: " + excep.getMessage());
            return false;
        }
        return true;
    }

    /**
     * player upgrades technology level
     * @param inputReader
     * @param out
     * @throws IOException
     * @throws Exception
     */
    public void playerDoUpgradeTech(BufferedReader inputReader, PrintStream out) throws IOException, Exception {
        int next_level = techLevel + 1;
        int currTechResource = techResource;
        upgradeTech(next_level, currTechResource);
    }

    public void upgradeTech(int next_level, int currTechResource){
        UpdateTechAction UpdateTech_action = new UpdateTechAction();
        UpdateTech_action.setPlayerID(playerId).setGameID(currentGameID).
                setNextLevel(next_level).setCurrTechResource(currTechResource);
        ActionList.add(UpdateTech_action);
        //TODO: Return Action to client
    }
    public void playerDoUpgradeUnit(BufferedReader inputReader, PrintStream out) throws IOException {
        String where;
        ArrayList<ArrayList<Integer>> unitsToUpgrade = new ArrayList<>();
        //{{oldlevel:0, leveltoupgrade: 1}, {1,2}, {0,1}}
        // player chooses to_name
        Integer level;
        Integer unitNum;
        where = inputReader.readLine();
        int l = Integer.parseInt(inputReader.readLine());
        level = l;
        int num = 0;
        unitNum = num;
        upgradeUnit(where, unitsToUpgrade);
    }

    public void upgradeUnit(String where, ArrayList<ArrayList<Integer>> unitsToUpgrade){
        UpdateUnitsAction UpdateUnits_action = new UpdateUnitsAction();
        UpdateUnits_action.setPlayerID(playerId).setGameID(currentGameID).setWhere(where).
                setUnitsToUpgrade(unitsToUpgrade);
        ActionList.add(UpdateUnits_action);
    }

////////////////////////////////Helper functions////////////////////////////////////////////////////
    /**
     * temporally update player's move action to player's own map
     *
     * @param moveUnits
     * @param from_name
     * @param to_name
     * @param _map
     * @param playerID
     * @return
     */
    public boolean updateMove(ArrayList<ArrayList<Integer>> moveUnits, String from_name, String to_name, Map _map,
                              int playerID) {
        // rule checker
        /*
        MoveChecker moveChecker = new MoveChecker(_map, moveUnits, from_name, to_name, id);
        if (moveChecker.doCheck(id, from_name, to_name) != null) {
            out.println(moveChecker.doCheck(id, from_name, to_name));
            return false;
        } else {
            _map.getTerritoryList().get(from_name).removeUnitMultiLevels(moveUnits);
            _map.getTerritoryList().get(to_name).addUnitMultiLevels(moveUnits);
            return true;
        }

         */
        return false;
    }

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

    public Map getWholeMap() {
        return wholeMap;
    }
}
