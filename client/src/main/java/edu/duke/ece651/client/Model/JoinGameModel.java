package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.client.GameInfo;
import edu.duke.ece651.shared.IO.ClientActions.JoinAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPOpenGameList;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Wrapper.GameID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JoinGameModel extends Model{

    public ObservableList<GameInfo> getGameLists(Boolean debugMode){
        // If in debug mode return true
        ObservableList<GameInfo> observableList = FXCollections.observableArrayList();

        GameInfo g1 = new GameInfo(1,2,"");
        observableList.add(g1);
        return observableList;
    }


    public ObservableList<GameInfo> getGameLists(){
        // Create ans
        ObservableList<GameInfo> observableList = FXCollections.observableArrayList();

        // Create a new JoinAction
        JoinAction joinAction = new JoinAction();
        // Send Action to Server
        try {
            ClientSocket clientSocket =  ClientSocket.getInstance();
            clientSocket.sendObject(joinAction);
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // Transform Response to RSPOpenGameList
            RSPOpenGameList rspOpenGameList = (RSPOpenGameList) response;

            // Assign available game to the ObservableList
            ArrayList<GameID> gameIDArrayList =  rspOpenGameList.getGameIDArrayList();
            for (int i = 0; i < gameIDArrayList.size(); i++) {
                GameID availableGameID =  gameIDArrayList.get(i);
                observableList.add(new GameInfo(availableGameID.getCurrGameID(), 2, ""));
            }
            return observableList;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return null;
        }
    }
}
