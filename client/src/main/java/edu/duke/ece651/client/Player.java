package edu.duke.ece651.client;

import edu.duke.ece651.shared.Checker.MoveChecker;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.map.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private int id; // player id
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

    public ArrayList<Integer> TechLevelUpgradeList;
    public ArrayList<Integer> UnitLevelUpgradeList;


    public Player(int _id, int _numOfPlayer, BufferedReader _in, PrintStream _out) {
        id = _id;
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
        setTechLevelUpgradeList();
        setUnitLevelUpgradeList();
    }

    public void setCurrentGameID(int gameID){
        currentGameID = gameID;
    }

    public int getCurrentGameID(){
        return currentGameID;
    }

    private void setTechLevelUpgradeList(){
        TechLevelUpgradeList = new ArrayList<>();
        TechLevelUpgradeList.add(0); //level 0: cost 0
        TechLevelUpgradeList.add(50);//level 1->2: cost 50
        TechLevelUpgradeList.add(75);//level 2->3: cost 75
        TechLevelUpgradeList.add(125);//level 3->4: cost 125
        TechLevelUpgradeList.add(200);//level 4->5: cost 125
        TechLevelUpgradeList.add(300);//level 5->6: cost 125
    }

    private void setUnitLevelUpgradeList(){
        UnitLevelUpgradeList = new ArrayList<>();
        UnitLevelUpgradeList.add(0); //level 0: cost 0
        UnitLevelUpgradeList.add(3); //level 0->1: cost 3
        UnitLevelUpgradeList.add(8);//level 1->2: cost 8
        UnitLevelUpgradeList.add(19);//level 2->3: cost 19
        UnitLevelUpgradeList.add(25);//level 3->4: cost 25
        UnitLevelUpgradeList.add(35);//level 4->5: cost 35
        UnitLevelUpgradeList.add(50);//level 5->6: cost 50
    }
    public void playerMakeChoice(BufferedReader inputReader, PrintStream out) throws Exception {
        String choice;
        while (true) {
            out.println("You are Player " + id + ", what would you like to do?");
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
        Integer unitNum = null;
        Integer level = 0;
        boolean isNumUnitsValid = false;
        while (true) {
            if (totalDeployment.get(level) == 0) {
                break;
            }
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
                deploy(unitNum, terrName);
            }
        }
    }

    public boolean deploy(int numOfDeployedUnits, String to_name) {

        if (numOfDeployedUnits <= totalDeployment.get(1)) { // get level 1
            Territory to = myTerritories.get(to_name);
            HashMap<Integer, Integer> unitNumber = new HashMap<>();
            unitNumber.put(1, numOfDeployedUnits);
            try {
                DeployAction deploy_action = new DeployAction();

                totalDeployment.replace(1, totalDeployment.get(1) - numOfDeployedUnits);
                ActionList.add(deploy_action);
            } catch (Exception excep) {
                out.println("Deployment Error: " + excep.getMessage());
                return false;
            }
            return true;
        } else {
            System.out.println("Deployment Error: deployed unit number cannot exceed initial deployment number");
            return false;
        }
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
            move_action.setFrom(from_name).setTo(to_name).setUnits(moveUnits).setGameID(currentGameID);

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
            attack_action.setFrom(from_name).setTo(to_name).setUnits(attackUnits).setGameID(currentGameID);
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
        // player chooses to_name
        int next_level = techLevel + 1;
        if (next_level <= 6){
            int cost = TechLevelUpgradeList.get(next_level);
            if (cost > techResource){
                out.println("Tech Upgrade Error: you don't have enough tech resource!");
            }
            else{
                techResource -= cost;
                techLevel += 1;
                upgradeTech();
            }
        }
    }

    public void upgradeTech(){
        UpdateTechAction UpdateTech_action = new UpdateTechAction();
        ActionList.add(UpdateTech_action);
    }
    public void playerDoUpgradeUnit(BufferedReader inputReader, PrintStream out) throws IOException {
        String where;
        ArrayList<ArrayList<Integer>> unitsToUpgrade = new ArrayList<>();
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

}
