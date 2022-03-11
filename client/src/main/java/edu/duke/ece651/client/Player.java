package edu.duke.ece651.client;

import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Territory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Player {

    private int id; //player id
    private int numOfPlayers;
    private BufferedReader inputReader;
    private PrintStream out;
    private boolean isFirstRound; //deploy
    private boolean isLose; //commit
    private boolean isGameOver;
    private boolean isWon;
    HashMap<String, Territory> myTerritories;//all territories of the player
    Map wholeMap;
    HashMap<Integer, Integer>totalDeployment; //totalUnits: key: level, value: num of units
    ArrayList<Action> ActionList;//list of actions

    /**
     * get player id
     * @return int of id
     */
    public int getId() {
        return id;
    }


    /**
     * Constructor
     * @param _id
     * @param _numOfPlayer
     */
    public Player(int _id, int _numOfPlayer){
        id = _id;
        numOfPlayers = _numOfPlayer;
        InputStreamReader myReader = new InputStreamReader(System.in);
        inputReader = new BufferedReader(myReader);
        out = new PrintStream(OutputStream.nullOutputStream());
        isFirstRound = true;
        isLose = false;
        isGameOver= false;
        isWon = false;
        myTerritories = new HashMap<>();
        wholeMap = new Map(numOfPlayers);
        setInitialTerritories(myTerritories, wholeMap);
        initializeDeployment();
        ActionList = new ArrayList<>();
    }

    /**
     * Constructor 2
     * @param _id
     * @param _numOfPlayer
     * @param _in
     * @param _out
     */
    public Player(int _id, int _numOfPlayer, BufferedReader _in, PrintStream _out){
        id = _id;
        numOfPlayers = _numOfPlayer;
        InputStreamReader myReader = new InputStreamReader(System.in);
        isFirstRound = true;
        isLose = false;
        isGameOver= false;
        isWon = false;
        myTerritories = new HashMap<>();
        wholeMap = new Map(numOfPlayers);
        setInitialTerritories(myTerritories, wholeMap);
        initializeDeployment();
        ActionList = new ArrayList<>();
        inputReader = _in;
        out = _out;
    }

    /**
     * set player's initial territory based on player's id and wholeMap's groups
     * @param _territories
     * @param _map
     */
    public void setInitialTerritories(HashMap<String, Territory> _territories, Map _map){
        for(int i = 0; i < _map.getGroups().size(); i++){
            if (i+1 == id){
                for(int j = 0; j < _map.getGroups().get(i).size(); j++){
                    String currTerrName = _map.getGroups().get(i).get(j);
                    _territories.put(currTerrName, _map.getTerritoryList().get(currTerrName));
                }
                break;
            }
        }
    }

    public void initializeDeployment(){
        totalDeployment = new HashMap<>();
        totalDeployment.put(1, myTerritories.size() * 3);// add 9 level 1 units
    }

    /**
     * ask player to select an action
     * @param inputReader
     * @param out
     * @return
     */
    public String playerMakeChoice(BufferedReader inputReader, PrintStream out) throws IOException{
        String choice;
        boolean isChoiceValid= true;
        do {
            out.println("You are Player " + id + ", what would you like to do?");
            out.println("(M)ove\n(A)ttack\n(D)one\n");
            choice = inputReader.readLine();
            if (choice == "M" || choice == "m" || choice == "A" || choice == "a"
                    || choice == "D" || choice == "d") {
                return choice;
            }
            else{
                out.println("Input Error: please enter a valid choice\n(M)ove\n(A)ttack\n(D)one\n");
                isChoiceValid = false;
            }
        } while(isChoiceValid == false);

        choice = choice.toUpperCase();
        if (choice == "M"){
            //TODO: MOVE

//                move();
        }
        else if (choice == "A"){
            //TODO: Attack
//                attack();
        }
        else{
            //TODO: Commit
        }

        return choice;
    }

    /**
     * deploy units to one of the player's territory
     * @param numOfDeployedUnits
     * @param to_name
     * @return boolean: whether deployed successfully
     */
    public boolean deploy(int numOfDeployedUnits, String to_name){
        if (numOfDeployedUnits <= totalDeployment.get(1)){ // get level 1
            Territory to = myTerritories.get(to_name);
            HashMap<Integer, Integer> unitNumber = new HashMap<>();
            unitNumber.put(1, numOfDeployedUnits);
            try {
                DeployAction deploy_action = new DeployAction(to, unitNumber);
                deploy_action.doAction();
                totalDeployment.replace(1, totalDeployment.get(1) - numOfDeployedUnits);
                ActionList.add(deploy_action);
            }
            catch(IllegalArgumentException illegalArg){
                System.out.println("Deployment Error: Illegal Argument Found");
                return false;
            }
            return true;
        }
        else{
            System.out.println("Deployment Error: deployed unit number cannot exceed initial deployment number");
            return false;
        }
    }

    public boolean isTerrNameMatch(String terrName,  HashMap<String, Territory> myTerritories){
        for(String terr: myTerritories.keySet()) {
            if (terrName == terr){
                return true;
            }
        }
        return false;
    }


    public void playerDoMove(BufferedReader inputReader, PrintStream out) throws IOException{
        String from_name, to_name;
        HashMap<Integer, Integer> moveUnits;
        boolean isFromValid = false;
        boolean isLevelValid = false;
        boolean isNumUnitsValid = false;
        boolean isToValid = true;

        //player chooses to_name
        do {
            out.println("Player " + id + ", enter the name of the territory you want to move units from?");
            for(String terrName: myTerritories.keySet()) {
                out.println(terrName);
            }
            from_name = inputReader.readLine();
            //check if from_name exists
            isFromValid = isTerrNameMatch(from_name,  myTerritories);
            if (isFromValid == false){
                out.println("No territory name found: please enter a valid name!");
            }
        } while(!isFromValid);
        //player chooses moveUnits
        Integer level;
        Integer unitNum;
        do{
            out.println("Player " + id + ", which level of unit do you want to move?");
            for(Integer i :totalDeployment.keySet()) {
                //out.println("Level "+ i+ ": ");
            }
            int l = Integer.parseInt(inputReader.readLine());
            level = l;
            //check if level exists
            isLevelValid = totalDeployment.get(level) == null? false: true;
            if (isLevelValid == false){
                out.println("Level not found: please enter a valid level!");
            }
        } while(!isLevelValid);

        //player set the number of units
        do{
            out.println("Player " + id + ", how many number of level " + level +" units do you want to move?");
            int num = Integer.parseInt(inputReader.readLine());
            if ( totalDeployment.get(level) >= num){
                isNumUnitsValid = true;
            }
            else{
//                out.println("Number of Units cannot exceed the maximum number of units in territory " + );
            }
        }while(!isNumUnitsValid);


        out.println("Player "+id+ ", where do you want to move to?");

    }

    /**
     * move units from one territory to another territory
     * @param moveUnits
     * @param from_name
     * @param to_name
     * @return
     */

    public boolean move(HashMap<Integer, Integer> moveUnits, String from_name, String to_name){
        try {
            Territory from = myTerritories.get(from_name);
            Territory to = myTerritories.get(to_name);
            MoveAction move_action = new MoveAction(from, to, moveUnits);
            move_action.doAction();
            ActionList.add(move_action);
        }
        catch(IllegalArgumentException illegalArg){
            System.out.println("Move Error: Illegal Argument Found");
            return false;
        }
        return true;
    }

    /**
     *send their units to an adjacent territory controlled by a different
     * player, in an attempt to gain control over that territory.
     * @param attackUnits
     * @param from_name
     * @param to_name
     * @return
     */
    public boolean attack(HashMap<Integer, Integer> attackUnits, String from_name, String to_name){
        try {
            Territory from = myTerritories.get(from_name);
            Territory to = myTerritories.get(to_name);
            AttackAction attack_action = new AttackAction(from, to, attackUnits);
            attack_action.doAction();
            ActionList.add(attack_action);
        }
        catch(IllegalArgumentException illegalArg){
            System.out.println("Attack Error: Illegal Argument Found");
            return false;
        }
        return true;
    }


    //TODO why play one round is using while
    public void playOneRound() throws IOException {
        ActionList.clear();

        // Deploy Round
        if (this.isFirstRound) {
            //TODO: DEPLOY
            playerDoDeploy(inputReader, out);
            /*
            HashMap<Integer, Integer> unitHashMap = new HashMap<>();
            unitHashMap.put(1,2);
            unitHashMap.put(2,4);
            unitHashMap.put(3,1);

            deploy(5, "a1");
             */
            this.isFirstRound = false;
            return;
        }

        //if not in the first round (deploy round)
        while (!this.isGameOver) {
            if (!this.isLose) {
                playerMakeChoice(inputReader, out);
                /*
                String playerChoice = this.inputReader.readLine();
                playerChoice = playerChoice.toUpperCase();
                if (playerChoice.equals("M")){
                    //TODO: MOVE
                    HashMap<Integer, Integer> unitHashMap = new HashMap<>();
                    unitHashMap.put(1,2);
                    unitHashMap.put(2,4);
                    unitHashMap.put(3,1);

                    move(unitHashMap, "a1", "a2");
                }
                else if (playerChoice.equals("A")){
                    //TODO: Attack
                    HashMap<Integer, Integer> unitHashMap = new HashMap<>();
                    unitHashMap.put(1,2);
                    unitHashMap.put(2,4);
                    unitHashMap.put(3,1);
                    attack(unitHashMap, "a2", "a3");
                }
                else{
                    // commit the action and send to server
                    return;
                }
                */
            } else { // if player lose, auto commit the action and send to server
                return;
            }
        }
        // if gameOver
        if (this.isWon) {
            //TODO: let player know he wins
            out.println("You are WIN");
        } else {
            //TODO: let player know he loses
            out.println("You are LOSE");
        }
    }
}
