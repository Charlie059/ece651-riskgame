package edu.duke.ece651.client;

import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Territory;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    int id; //player id
    int numOfPlayers;
    boolean isFirstRound; //deploy
    boolean isLose; //commit
    boolean isGameOver;
    boolean isWon;
    HashMap<String, Territory> myTerritories;//all territories of the player
    Map wholeMap;
    HashMap<Integer, Integer>totalDeployment; //totalUnits: key: level, value: num of units
    ArrayList<Action> ActionList;//list of actions

    /**
     * Constructor
     * @param _id
     * @param _numOfPlayer
     */
    public Player(int _id, int _numOfPlayer){
        id = _id;
        numOfPlayers = _numOfPlayer;
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
     * deploy units to one of the player's territory
     * @param numOfDeployedUnits
     * @param to_name
     * @return boolean: whether deployed successfully
     */
    public boolean deploy(int numOfDeployedUnits, String to_name){
        if (numOfDeployedUnits <= totalDeployment.get(1)){ // get level 1
            Territory to = myTerritories.get(to_name);
            HashMap<Integer, Integer> unitMap = new HashMap<>();
            unitMap.put(1, numOfDeployedUnits);
            try {
                DeployAction deploy_action = new DeployAction(to, unitMap);
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
    public void playOneRound(){
        //play one round
        while(true) {
            //TODO: clear ActionList
            ActionList.clear();

            if (this.isFirstRound) {
                //TODO:deploy unit

                this.isFirstRound = false;
            }

            //not first round
            while (!this.isGameOver) {
                if (!this.isLose) {
                    //TODO:
                    //receive map from server (JSON)
                    //update isLose, isGameOver, isWon
                    // 1) Move 2) Attack 3) Commit
                } else {
                    //receive map from server
                    //update isGameOver
                    //3) commit
                }

            }
            //gameOver
            if (this.isWon) {
                //TODO: let player know he wins
            } else {
                //TODO: let player know he loses
            }


        }
    }
}
