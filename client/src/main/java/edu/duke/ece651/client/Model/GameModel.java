package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Spy;
import edu.duke.ece651.shared.map.Territory;
import edu.duke.ece651.shared.map.Unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * GameModel contains game info to be displayed into view(pass by controller)
 */
public class GameModel extends Model{
    private static GameModel gameModel;


    private ClientPlayerPacket clientPlayerPacket;

    private GameModel() {}

    public static GameModel getInstance() {
        if (gameModel == null) {gameModel = new GameModel();}
        return gameModel;
    }


    /**
     * Get player id
     * @return String
     */
    public String getPlayerID(){
        return this.clientPlayerPacket.getAccountID().getAccountID();
    }

    /**
     * Get food Res
     * @return
     */
    public int getFoodRes(){
        return this.clientPlayerPacket.getFoodResource();
    }

    /**
     * Get food Res
     * @return
     */
    public int getTechRes(){
        return this.clientPlayerPacket.getTechResource();
    }


    /**
     * Get tech level
     * @return tech level
     */
    public int getTechlevel(){
        return this.clientPlayerPacket.getTechLevel();
    }


    /**
     * Get terr's units list from terrName
     * @param terrName String
     * @param ans Arr
     */
    public void getTerrUnits(String terrName, ArrayList<Integer> ans){
        // Get Terr units form the model
        HashMap<String, Territory> myTerritories = GameModel.getInstance().getClientPlayerPacket().getMyTerritories();
        if(myTerritories.containsKey(terrName)){
            ArrayList<Unit> units = myTerritories.get(terrName).getUnits();
            // Set unit to the view
            for (int i = 0; i < units.size(); i++) {
                ans.add(units.get(i).getValue());
            }
        }
        else {
            // We cannot get enmity's info
            for (int i = 0; i < 7; i++) {
                ans.add(0);
            }
        }
    }

    public boolean doUpgradeTech(boolean debugMode){
        if (debugMode){
            this.clientPlayerPacket.doUpgradeTech(1,10);
            return true;
        }

        try {
            // Send upgradeTech action to server
            UpgradeTechAction upgradeTechAction = new UpgradeTechAction();
            ClientSocket.getInstance().sendObject(upgradeTechAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPUpgradeTechSuccess
            if(response.getClass() != RSPUpgradeTechSuccess.class) return false;

            // Cast the response
            RSPUpgradeTechSuccess rspUpgradeTechSuccess = (RSPUpgradeTechSuccess) response;

            //Update model
            this.clientPlayerPacket.doUpgradeTech(this.clientPlayerPacket.getTechLevel(), rspUpgradeTechSuccess.getTechCost());
            return true;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {}
        return false;
    }
    /**
     * doAtack action, send req to server and get response then change view
     * @param attackInfo
     * @param debugMode
     * @return true for success
     */
    public boolean doAttack(String[] attackInfo, boolean debugMode){
        // For Debug only
        if(debugMode){
            ArrayList<ArrayList<Integer>> units = new ArrayList<>();
            ArrayList<Integer> unit = new ArrayList<>();
            unit.add(0);
            unit.add(1);
            units.add(unit);
            this.clientPlayerPacket.doAttack(attackInfo[0], attackInfo[1], units, 10);
            return true;
        }

        try {
            String from = attackInfo[0];
            String to = attackInfo[1];
            String level = attackInfo[2];
            String num = attackInfo[3];

            // Add attack unit to array
            ArrayList<ArrayList<Integer>> units = new ArrayList<>();
            ArrayList<Integer> levelAndNum = new ArrayList<>();
            levelAndNum.add(Integer.parseInt(level));
            levelAndNum.add(Integer.parseInt(num));
            units.add(levelAndNum);

            // Send a attackAction to server
            AttackAction attackAction = new AttackAction(from, to, units);
            ClientSocket.getInstance().sendObject(attackAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPAttackSuccess
            if(response.getClass() != RSPAttackSuccess.class) return false;

            // Cast and Get the response filed
            RSPAttackSuccess rspAttackSuccess = (RSPAttackSuccess) response;

            // Change the model
            this.clientPlayerPacket.doAttack(rspAttackSuccess.getFrom(), rspAttackSuccess.getTo(), rspAttackSuccess.getUnits(), rspAttackSuccess.getTotalCost());
            return true;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {}
        return false;
    }

    /**
     * Upgrade one unit
     * @return true for success
     */
    public boolean doUpgradeUnit(String[] upgradeInfo, boolean debugMode){
        if(debugMode){
            this.clientPlayerPacket.doUpgradeUnit("b1", 0,1,10);
            return true;
        }

        try {
            String from = upgradeInfo[0];
            String selectCurLevel = upgradeInfo[1];
            String selectNum = upgradeInfo[2];
            String selectUpgradeLevel = upgradeInfo[3];

            // Send a join action to server
            UpgradeUnitsAction upgradeUnitsAction = new UpgradeUnitsAction(from, Integer.parseInt(selectCurLevel),Integer.parseInt(selectUpgradeLevel));
            ClientSocket.getInstance().sendObject(upgradeUnitsAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPUpgradeUnitsSuccess
            if(response.getClass() != RSPUpgradeUnitsSuccess.class) return false;

            // Cast and Get the response filed
            RSPUpgradeUnitsSuccess rspUpgradeUnitsSuccess = (RSPUpgradeUnitsSuccess) response;

            // Change the model
            this.clientPlayerPacket.doUpgradeUnit(rspUpgradeUnitsSuccess.getWhere(), rspUpgradeUnitsSuccess.getOldLevel(), rspUpgradeUnitsSuccess.getNewLevel(), rspUpgradeUnitsSuccess.getTechCost());
            return true;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {}
        return false;
    }

    /**
     * Plyaer do move action, send server to check
     * @param moveInfo
     * @param debugMode
     * @return true for success
     */
    public boolean doMove(String[] moveInfo, boolean debugMode){
        // For Debug only
        if(debugMode){
            ArrayList<ArrayList<Integer>> units = new ArrayList<>();
            ArrayList<Integer> unit = new ArrayList<>();
            unit.add(0);
            unit.add(1);
            units.add(unit);
            this.clientPlayerPacket.doMove(moveInfo[0], moveInfo[1], units, 10);
            return true;
        }

        try {
            String from = moveInfo[0];
            String to = moveInfo[1];
            String level = moveInfo[2];
            String num = moveInfo[3];

            // Add move unit to array
            ArrayList<ArrayList<Integer>> units = new ArrayList<>();
            ArrayList<Integer> levelAndNum = new ArrayList<>();
            levelAndNum.add(Integer.parseInt(level));
            levelAndNum.add(Integer.parseInt(num));
            units.add(levelAndNum);

            // Send a join action to server
            MoveAction moveAction = new MoveAction(from, to, units);
            ClientSocket.getInstance().sendObject(moveAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPMoveSuccess
            if(response.getClass() != RSPMoveSuccess.class) return false;

            // Cast and Get the response filed
            RSPMoveSuccess rspMoveSuccess = (RSPMoveSuccess) response;

            // Change the model
            this.clientPlayerPacket.doMove(rspMoveSuccess.getFrom(), rspMoveSuccess.getTo(), rspMoveSuccess.getUnits(), rspMoveSuccess.getTotalCost());
            return true;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {}
        return false;
    }



    /**
     * Try to send commit request to server
     * @return true for commit successful
     */
    public boolean doCommit(Boolean debugMode){
        // For debug only
        if(debugMode) return true;
        // func
         try {
             // Send DeployAction to server
             CommitAction commitAction = new CommitAction();
             ClientSocket.getInstance().sendObject(commitAction);

             // Recv Response form server
             Response response = (Response) ClientSocket.getInstance().recvObject();

             // If response is RSP
             if(response.getClass() == RSPCommitSuccess.class){
                 // Get the player obj from response
                 ClientPlayerPacket clientPlayerPacket = ((RSPCommitSuccess) response).getClientPlayerPacket();

                 // If clientPlayerPacket is null return false
                 if(clientPlayerPacket == null) return false;

                 // Update the GameModel
                 this.clientPlayerPacket = clientPlayerPacket;
                 // Return true
                 return true;
             }

        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {}
         return false;
    }

    /**
     * Send deployAction to server, if success, change the model's terr info
     * @return true for deployment success, else print error message
     */
    public boolean doDeploy(String to, int deployUnits, boolean debugMode){
        // Debug use only
        if (debugMode){
            this.clientPlayerPacket.doDeploy(to, deployUnits);
            return true;
        }
        // func
        try {
            // Send DeployAction to server
            DeployAction deployAction = new DeployAction(to,deployUnits,getClientPlayerPacket().getAccountID().getAccountID());
            ClientSocket.getInstance().sendObject(deployAction);

            // Recv Response form server
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPDeploySuccess
            if(response.getClass() == RSPDeploySuccess.class){
                // Update the GameModel
                this.clientPlayerPacket.doDeploy(to, deployUnits);
                // Return true
                return true;
            }
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {}
        return false;
    }



    /**
     * User choose to sitch game with specific gameID
     * @param gameID int
     * @return ture for join game success
     */
    public Boolean switchGame(int gameID, boolean debugMode){
        // Debug use only
        if(debugMode){
            mockData();
            return true;
        }

        // func
        try {
            // Send a chooseSwitchGameAction  to server
            ChooseSwitchGameAction chooseSwitchGameAction= new ChooseSwitchGameAction(new GameID(gameID));
            ClientSocket.getInstance().sendObject(chooseSwitchGameAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPChooseJoinGameSuccess
            if(response.getClass() == RSPChooseSwitchGameSuccess.class){
                // Get the player obj from response
                ClientPlayerPacket clientPlayerPacket = ((RSPChooseSwitchGameSuccess) response).getClientPlayerPacket();

                // If clientPlayerPacket is null return false
                if(clientPlayerPacket == null) return false;

                // Update the GameModel
                this.clientPlayerPacket = clientPlayerPacket;
                // Return true
                return true;
            }

        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {}
        return false;
    }


    /**
     * User choose to join game with specific gameID
     * @param gameID int
     * @return ture for join game success
     */
    public Boolean joinGame(int gameID, boolean debugMode){
        // Debug use only
        if(debugMode){
            mockData();
            return true;
        }

        // func
        try {
            // Send a join action to server
            ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(gameID));
            ClientSocket.getInstance().sendObject(chooseJoinGameAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPChooseJoinGameSuccess
            if(response.getClass() == RSPChooseJoinGameSuccess.class){
                // Get the player obj from response
                ClientPlayerPacket clientPlayerPacket = ((RSPChooseJoinGameSuccess) response).getClientPlayerPacket();

                // If clientPlayerPacket is null return false
                if(clientPlayerPacket == null) return false;

                // Update the GameModel
                this.clientPlayerPacket = clientPlayerPacket;
                // Return true
                return true;
            }

        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {}
        return false;
    }


    /**
     * Start a new game action
     * @param numOfPlayerUsrInput usr input Num of Player
     * @return true for start new game success
     */
    public Boolean startNewGame(String numOfPlayerUsrInput, boolean debugMode){
        // Debug use only
        if(debugMode){
            mockData();
            return true;
        }
        // func
        try {
            // Send NewGameAction to server
            NewGameAction newGameAction = new NewGameAction(Integer.parseInt(numOfPlayerUsrInput));
            ClientSocket.getInstance().sendObject(newGameAction);

            // Recv object from Server
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPNewGameSuccess
            if(response.getClass() == RSPNewGameSuccess.class){
                // Get the player obj from response
                ClientPlayerPacket clientPlayerPacket = ((RSPNewGameSuccess) response).getClientPlayerPacket();

                // If clientPlayerPacket is null return false
                if(clientPlayerPacket == null) return false;

                // Update the GameModel
                this.clientPlayerPacket = clientPlayerPacket;
                // Return true
                return true;
            }
            else return false;

        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return false;
        }

    }



    public ClientPlayerPacket getClientPlayerPacket() {
        return clientPlayerPacket;
    }


    /**
     * Mock clientPlayerPacket FOR TESTING ONLY
     */
    public ClientPlayerPacket mockData() {
        // Create mock data
        // Create mock data
        HashMap<String, Territory> myTerr = new HashMap<>();
        Territory territory1 = new Territory("b1");
        Territory territory2 = new Territory("b2");
        Territory territory3 = new Territory("b3");
        myTerr.put("b1", territory1);
        myTerr.put("b2", territory2);
        myTerr.put("b3", territory3);

        HashMap<AccountID, HashMap<String, ArrayList<Integer>>> enemyTerritoriesV2 = new HashMap<>();

        HashMap<String, ArrayList<Integer>> terr = new HashMap<>();

        ArrayList<Integer> enemyTerrUnits1 = new ArrayList<>();
        enemyTerrUnits1.add(7);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);

        ArrayList<Integer> enemyTerrUnits2 = new ArrayList<>();
        enemyTerrUnits2.add(1);
        enemyTerrUnits2.add(4);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);

        terr.put("a1", enemyTerrUnits1);
        terr.put("a2", enemyTerrUnits2);
        enemyTerritoriesV2.put(new AccountID("p1"), terr);

        HashMap<String, ArrayList<Spy>> spyInfo = new HashMap<>();
        ArrayList<Spy> spyArrayList = new ArrayList<>();
        spyArrayList.add(new Spy(new AccountID("p2")));
        spyInfo.put("a1", spyArrayList);

        ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(new GameID(1), new AccountID("abc"), 2, 100, 100, 2, 9, myTerr, new HashMap<String, ArrayList<String>>(), false, false, enemyTerritoriesV2, spyInfo);
        this.clientPlayerPacket = clientPlayerPacket;
        return clientPlayerPacket;

    }


    /**
     * Get Enemy Array TerrList
     * @return ArrayList<String>
     */
    public HashSet<String> getEnemyTerrList(){
        HashSet<String> ans = new HashSet<>();
        HashMap<String, ArrayList<String>> enemyTerritories = this.clientPlayerPacket.getEnemyTerritories();

        for(String playerID:enemyTerritories.keySet()){
           ArrayList<String> enemyTerritoriesList = enemyTerritories.get(playerID);
           ans.addAll(enemyTerritoriesList);
        }
        return ans;
    }

    /**
     * Get my TerrList
     * @return MyTerrList
     */
    public HashSet<String> getMyTerrList(){
        HashSet<String> ans = new HashSet<>();
        HashMap<String, Territory> myTerritories = this.clientPlayerPacket.getMyTerritories();

        for(String myTerr:myTerritories.keySet()){
            ans.add(myTerr);
        }
        return ans;

    }



}
