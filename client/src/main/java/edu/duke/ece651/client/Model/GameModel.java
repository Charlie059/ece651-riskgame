package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
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

    //new String[]{terrFrom.getText(), terrTo.getText(),selectLevel.getText(),selectNum.getText()})
    public boolean doAttack(String[] attackInfo, boolean debugMode){
        // For Debug only
        if(debugMode) return true;

        try {
            String from = attackInfo[0];
            String to = attackInfo[1];
            String level = attackInfo[2];
            String num = attackInfo[3];

            // Send a join action to server
//            AttackAction attackAction = new AttackAction(this.clientPlayerPacket.getCurrentGameID(),this.clientPlayerPacket.getAccountID(), from, to, );
//            ClientSocket.getInstance().sendObject(attackAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPAttackSuccess
            if(response.getClass() == RSPAttackSuccess.class) return true;

            // Change the model
//            this.clientPlayerPacket.doAttack();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Try to send commit request to server
     * @return true for commit successful
     */
    public boolean deCommit(Boolean debugMode){
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

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
         return false;
    }

    /**
     * Send deployAction to server, if success, change the model's terr info
     * @return true for deployment success, else print error message
     */
    public boolean doDeploy(String to, int deployUnits, boolean debugMode){
        // Debug use only
        if (debugMode){
            this.clientPlayerPacket.DoDeploy(to, deployUnits);
            return true;
        }
        // func
        try {
            // Send DeployAction to server
            DeployAction deployAction = new DeployAction(getClientPlayerPacket().getCurrentGameID().getCurrGameID(), to,deployUnits,getClientPlayerPacket().getAccountID().getAccountID());
            ClientSocket.getInstance().sendObject(deployAction);

            // Recv Response form server
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPDeploySuccess
            if(response.getClass() == RSPDeploySuccess.class){
                // Update the GameModel
                this.clientPlayerPacket.DoDeploy(to, deployUnits);
                // Return true
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            return null;
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

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
    private void mockData() {
        // Create mock data
        HashMap<String, Territory> myTerr = new HashMap<>();
        Territory territory1 = new Territory("b1");
        Territory territory2 = new Territory("b2");
        Territory territory3 = new Territory("b3");
        myTerr.put("b1",territory1);
        myTerr.put("b2",territory2);
        myTerr.put("b3",territory3);

        HashMap<String, ArrayList<String>> enemyTerritories = new HashMap<>();

        ArrayList<String> enemyTerrName = new ArrayList<>();
        enemyTerrName.add("a1");
        enemyTerrName.add("a2");
        enemyTerrName.add("a3");

        enemyTerritories.put("p1", enemyTerrName);

        ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(new GameID(1), new AccountID("abc"),2,100,100,100, 9, myTerr,enemyTerritories);
        this.clientPlayerPacket = clientPlayerPacket;
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
