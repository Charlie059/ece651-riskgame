package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.client.View.DeployView;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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


    /*
     For testing UI Only
     */
    public boolean deCommit(boolean debugMode){
        return true;
    }

    /**
     * Try to send commit request to server
     * @return true for commit successful
     */
    public boolean deCommit(){
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
    public boolean doDeploy(String to, int deployUnits){
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
     * DEBUG MODE
     * @param debug
     * @return
     */
    public boolean doDeploy(boolean debug){
        // Change the terr's info
        this.clientPlayerPacket.DoDeploy("b1",3);
        this.clientPlayerPacket.DoDeploy("b2",3);
        this.clientPlayerPacket.DoDeploy("b3",3);
        return true;
    }

    /**
     * Debug Mode
     * @param debug
     * @return
     */
    public Boolean joinGame(boolean debug) {

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

        ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(new GameID(1), new AccountID("abc"),3,100,100,100, 9, myTerr,enemyTerritories);
        this.clientPlayerPacket = clientPlayerPacket;

        return true;
    }

    /**
     * User choose to join game with specific gameID
     * @param gameID int
     * @return ture for join game success
     */
    public Boolean joinGame(int gameID){
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
    public Boolean startNewGame(String numOfPlayerUsrInput){
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


    /**
     * Start a new game action (DEBUG Mode)
     * @param numOfPlayerUsrInput
     * @param debugMode
     * @return
     */
    public Boolean startNewGame(String numOfPlayerUsrInput, Boolean debugMode){
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

        return true;
    }




    public ClientPlayerPacket getClientPlayerPacket() {
        return clientPlayerPacket;
    }
}
