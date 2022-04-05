package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.shared.IO.ClientActions.NewGameAction;
import edu.duke.ece651.shared.IO.ServerResponse.ClientPlayerPacket;
import edu.duke.ece651.shared.IO.ServerResponse.RSPNewGameSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
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

    private ClientPlayerPacket clientPlayerPacket;

    // TODO using visitor pattern to change map

    /**
     * Start a new game action
     * @param numOfPlayerUsrInput usr input Num of Player
     * @return Player Obj?
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
     * DEBUG Mode
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

        ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(new GameID(1), new AccountID("abc"),3,100,100,100, 9, myTerr,enemyTerritories);
        this.clientPlayerPacket = clientPlayerPacket;

        return true;
    }
}
