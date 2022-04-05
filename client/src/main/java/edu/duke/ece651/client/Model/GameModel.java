package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.shared.IO.ClientActions.NewGameAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPLoginSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.RSPNewGameSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Player;

import java.io.IOException;

/**
 * GameModel contains game info to be displayed into view(pass by controller)
 */
public class GameModel extends Model{
    private Player player;

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
                // get the player obj from response

                // Update the GameModel

                // Return true
                return true;
            }
            else return false;

        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return false;
        }

    }


    /**
     * Start a new game action (debug mode)
     * @param numOfPlayerUsrInput usr input Num of Player
     * @return Player Obj?
     */
    public Boolean startNewGame(String numOfPlayerUsrInput, Boolean debugMode){
        if(debugMode) return true;
        try {
            // Send NewGameAction to server
            NewGameAction newGameAction = new NewGameAction(Integer.parseInt(numOfPlayerUsrInput));
            ClientSocket.getInstance().sendObject(newGameAction);

            // Recv object from Server
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPNewGameSuccess
            if(response.getClass() == RSPNewGameSuccess.class){
                // get the player obj from response

                // Update the GameModel

                // Return true
                return true;
            }
            else return false;

        } catch (IOException | ClassNotFoundException e) {
            return false;
        }

    }
}
