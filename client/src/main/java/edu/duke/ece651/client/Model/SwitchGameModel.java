package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.client.GameInfo;
import edu.duke.ece651.shared.IO.ClientActions.JoinAction;
import edu.duke.ece651.shared.IO.ClientActions.SwitchGameAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPOpenGameList;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSwitchGameList;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Wrapper.GameID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class SwitchGameModel extends Model{


    public ObservableList<GameInfo> getGameLists(Boolean debugMode){
        if(debugMode){
            // If in debug mode return true
            ObservableList<GameInfo> observableList = FXCollections.observableArrayList();

            GameInfo g1 = new GameInfo(1,2,"");
            GameInfo g2 = new GameInfo(2,2,"");
            observableList.add(g1);
            observableList.add(g2);
            return observableList;
        }
        // Create ans
        ObservableList<GameInfo> observableList = FXCollections.observableArrayList();

        // Create a new switchGameAction
        SwitchGameAction switchGameAction = new SwitchGameAction();
        // Send Action to Server
        try {
            ClientSocket.getInstance().sendObject(switchGameAction);
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // Transform Response to RSPOpenGameList
            RSPSwitchGameList rspSwitchGameList = (RSPSwitchGameList) response;

            // Assign available game to the ObservableList
            ArrayList<GameID> gameIDArrayList =  rspSwitchGameList.getGameIDArrayList();
            for (int i = 0; i < gameIDArrayList.size(); i++) {
                GameID availableGameID =  gameIDArrayList.get(i);
                observableList.add(new GameInfo(availableGameID.getCurrGameID(), 0, ""));
            }
            return observableList;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return null;
        }
    }
}
