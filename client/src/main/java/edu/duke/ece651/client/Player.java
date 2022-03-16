package edu.duke.ece651.client;

import edu.duke.ece651.shared.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private int id; //player id
    private int numOfPlayers;
    private BufferedReader inputReader;
    private PrintStream out;
    private boolean isFirstRound; //deploy
    private boolean isLose; //commit
    private boolean isGameOver;
    private boolean isWon;
    private HashMap<String, Territory> myTerritories;//all territories of the player
    private Map wholeMap;
    private HashMap<Integer, Integer>totalDeployment; //totalUnits: key: level, value: num of units
    private ArrayList<Action> ActionList;//list of actions

    /**
     * get player id
     * @return int of id
     */
    public int getId() {
        return id;
    }
    public BufferedReader getInputReader(){return inputReader;}
    public PrintStream getOut(){return out;}
    public HashMap<String, Territory> getMyTerritories(){return myTerritories;}
    public HashMap<Integer, Integer> getTotalDeployment(){return totalDeployment;}
    public Map getWholeMap(){return wholeMap;}
    public ArrayList<Action> getActionList(){return ActionList;}
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
        totalDeployment.put(1, myTerritories.size() * 3);// add 3 * territory# units to level 1
    }

    /**
     * ask player to select an action
     * @param inputReader
     * @param out
     * @return
     */
    public void playerMakeChoice(BufferedReader inputReader, PrintStream out) throws IOException{
        String choice;
        boolean isChoiceValid= false;
        while(true){
            do {
                out.println("You are Player " + id + ", what would you like to do?");
                out.println("(M)ove\n(A)ttack\n(D)one\n");
                choice = inputReader.readLine();
                if (choice.equals("M") || choice.equals("m") || choice.equals("A") || choice.equals("a")
                        || choice.equals("D") || choice.equals("d")) {
                    isChoiceValid = true;
                }
                else{
                    out.println("Input Error: please enter a valid choice\n(M)ove\n(A)ttack\n(D)one\n");
                }
            } while(!isChoiceValid);

            choice = choice.toUpperCase();
            if (choice.equals("M")){
                //TODO: MOVE
                playerDoMove(inputReader, out);
            }
            else if (choice.equals("A")){
                //TODO: Attack
               playerDoAttack(inputReader, out);
            }
            else {
                //TODO: Commit
                break;
            }
        }
        return;
    }

    public void playerDoDeploy(BufferedReader inputReader, PrintStream out) throws IOException, Exception {
        //player set the number of units
        out.println("Player "+ id+ ": you have " + this.totalDeployment.get(1) + " level-1 units");
        out.println("You can deploy them in your " + this.myTerritories.size() + " territories");
        Integer unitNum;
        Integer level = 1;
        boolean isNumUnitsValid = false;
        while(true) {
            if (totalDeployment.get(level) == 0) {
                break;
            }
            for (String terrName : this.myTerritories.keySet()) {
                if (totalDeployment.get(level) == 0) {
                    break;
                }
                do {
                    out.println("Player " + id + ", how many number of level-1 units do you want to deploy in territory "
                            + terrName + "?");
                    int num = Integer.parseInt(inputReader.readLine());
                    unitNum = num;
                    if (totalDeployment.get(level) >= unitNum && unitNum >= 0) {
                        isNumUnitsValid = true;
                        //totalDeployment.replace(level, totalDeployment.get(level) - unitNum);
                        out.println("you have deployed " + unitNum + " level-" + level + " units to " +
                                "Territory " + terrName + "!");
                    } else {
                        out.println("Number of level " + level + "-units cannot be less than o or exceed the total " +
                                "number of deployment");
                    }
                } while (!isNumUnitsValid);
                deploy(unitNum, terrName);
            }
        }
    }

    /**
     * deploy units to one of the player's territory
     * @param numOfDeployedUnits
     * @param to_name
     * @return boolean: whether deployed successfully
     */
    public boolean deploy(int numOfDeployedUnits, String to_name) throws Exception {

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
                out.println("Deployment Error: Illegal Argument Found");
                return false;
            }
            return true;
        }
        else{
            System.out.println("Deployment Error: deployed unit number cannot exceed initial deployment number");
            return false;
        }
    }

    /**
     * helper function: check if input territory name matches names in myTerritories
     * @param terrName
     * @param myTerritories
     * @return
     */
    public boolean isTerrNameMatch(String terrName,  HashMap<String, Territory> myTerritories){
        for(String terr: myTerritories.keySet()) {
            if (terrName.equals(terr)){
                return true;
            }
        }
        return false;
    }

    /**
     * player inputs parameters of move action
     * @param inputReader
     * @param out
     * @throws IOException
     */
    public void playerDoMove(BufferedReader inputReader, PrintStream out) throws IOException{
        String from_name, to_name;
        HashMap<Integer, Integer> moveUnits = new HashMap<>();
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
                out.println("(" + i + ") Level "+ i+ " has " + totalDeployment.get(i)+ " units.");
            }
            int l = Integer.parseInt(inputReader.readLine());
            level = l;
            //check if level exists in totalDeployment
            isLevelValid = totalDeployment.get(level) == null? false: true;
            if (isLevelValid == false){
                out.println("Level not found: please enter a valid level!");
            }
        } while(!isLevelValid);
        //player set the number of units
        do{
            out.println("Player " + id + ", how many number of level " + level +" units do you want to move?");
            int num = Integer.parseInt(inputReader.readLine());
            unitNum = num;
            if ( totalDeployment.get(level) >= unitNum){
                isNumUnitsValid = true;
            }
            else{
                out.println("Number of level " + level + " units cannot exceed the maximum " +
                        "number of that unit in territory " + from_name);
            }
        }while(!isNumUnitsValid);
        moveUnits.put(level, unitNum);
        //player set territory he wants to move to
        do {
            out.println("Player " + id + ", enter the name of the territory you want to move units to:");
            for(String terrName: myTerritories.keySet()) {
                if (terrName != from_name) {
                    out.println(terrName);
                }
            }
            to_name = inputReader.readLine();
            //check if from_name exists
            isToValid = isTerrNameMatch(to_name,  myTerritories);
            if (isToValid == false){
                out.println("No territory name found: please enter a valid name!");
            }
        } while(!isToValid);
        move(moveUnits, from_name, to_name);
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

    public void playerDoAttack(BufferedReader inputReader, PrintStream out) throws IOException{
        String from_name, to_name;
        HashMap<Integer, Integer> attackUnits = new HashMap<>();
        boolean isFromValid = false;
        boolean isLevelValid = false;
        boolean isNumUnitsValid = false;
        boolean isToValid = true;

        //player chooses to_name
        do {
            out.println("Player " + id + ", enter the name of the territory you want to send units from?");
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
        //player chooses attackUnits
        Integer level;
        Integer unitNum;
        do{
            out.println("Player " + id + ", which level of unit do you want to send?");
            out.println("Territory "+ from_name+ ":");
            for(Integer i :totalDeployment.keySet()) {
                out.println("   (" + i + ") Level "+ i+ " has " + totalDeployment.get(i)+ " units.");
                out.println();
            }
            int l = Integer.parseInt(inputReader.readLine());
            level = l;
            //check if level exists in totalDeployment
            isLevelValid = totalDeployment.get(level) == null? false: true;
            if (isLevelValid == false){
                out.println("Level not found: please enter a valid level!");
            }
        } while(!isLevelValid);
        //player set the number of units
        do{
            out.println("Player " + id + ", how many number of level " + level +" units do you want to send?");
            int num = Integer.parseInt(inputReader.readLine());
            unitNum = num;
            if ( totalDeployment.get(level) >= unitNum){
                isNumUnitsValid = true;
            }
            else{
                out.println("Number of level " + level + " units cannot exceed the maximum " +
                        "number of that unit in territory " + from_name);
            }
        }while(!isNumUnitsValid);
        attackUnits.put(level, unitNum);
        //player set territory he wants to attack
        do {
            out.println("Player " + id + ", enter the name of the territory you want to attack:");
            for(String terrName: wholeMap.getTerritoryList().keySet()) {
                //should not display player's own territories
                if (wholeMap.getTerritoryList().get(terrName).getOwner() != this.id) {
                    out.println(wholeMap.getTerritoryList().get(terrName).getName()+ ": ");
                    out.println("   Owner: player" + wholeMap.getTerritoryList().get(terrName).getOwner());
                    out.println("   Units:");
                    for(Integer i: wholeMap.getTerritoryList().get(terrName).getUnits().keySet()){
                        out.println("       Level " + i + " Units: " +
                                wholeMap.getTerritoryList().get(terrName).getUnits().get(i));
                    }
                    out.println();
                }
            }
            to_name = inputReader.readLine();
            //check if from_name exists
            isToValid = isTerrNameMatch(to_name,  myTerritories);
            if (isToValid == false){
                out.println("No territory name found: please enter a valid name!");
            }
        } while(!isToValid);
        attack(attackUnits, from_name, to_name);
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
    public void playOneRound() throws IOException, Exception {
        ActionList.clear();

        // Deploy Round
        if (this.isFirstRound) {
            //TODO: DEPLOY
            playerDoDeploy(inputReader, out);
            this.isFirstRound = false;
            return;
        }

        //if not in the first round (deploy round)
        if (!this.isGameOver) {
            if (!this.isLose) {
                playerMakeChoice(inputReader, out);
                return;
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
