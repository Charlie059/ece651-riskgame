package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.shared.map.Territory;
import edu.duke.ece651.shared.map.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MapViewController implements Initializable {
    @FXML
    Label terrOwner,terrName,n_level0,n_level1,n_level2,n_level3,n_level4,n_level5,n_level6;

    private Stage window;
    public MapViewController(Stage window){
        this.window = window;
    }

    /**
     * If user click terr
     * @param ae ActionEvent
     */
    @FXML
    public void clickOnTerr(ActionEvent ae){
        Object source = ae.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            terrName.setText(btn.getText());
            terrOwner.setText(getTerrOwner(terrName.getText()));
            showTerrUnitsInText(getTerrUnits(terrName.getText()));
            //or other possible info need to show
        }
        else {  // situation should never come up
            throw new IllegalArgumentException("Invalid source " +
                    source +
                    " for ActionEvent");
        }
    }

    /**
     * Get owner of the specific territory based on its name. Return playerID
     * @param terrName String
     * @return PlayID
     */
    private String getTerrOwner(String terrName){
        // Get data from the model
        HashMap<String, Territory> myTerritories = GameModel.getInstance().getClientPlayerPacket().getMyTerritories();
        if (myTerritories.containsKey(terrName)){
            return GameModel.getInstance().getClientPlayerPacket().getAccountID().getAccountID();
        }
        else{
            // Get data from the model
            HashMap<String, ArrayList<String>> enemyTerritories =  GameModel.getInstance().getClientPlayerPacket().getEnemyTerritories();
            // iterate hashmap
            for (String playerID : enemyTerritories.keySet()) {
               ArrayList<String> terrs =  enemyTerritories.get(playerID);
                // get terrs name
                for (int i = 0; i < terrs.size(); i++) {
                    if(terrs.get(i).equals(terrName)){
                        return playerID;
                    }
                }
            }

        }
        return "Enemy";
    }

    /**
     * Get units number of the specific territory based on its name. return in a list, correspoing to the number of each level.
     * @param terrName String
     * @return ArrayList<Integer> of Units
     */
    private ArrayList<Integer> getTerrUnits(String terrName) {
        // TerrUnits ans
        ArrayList<Integer> ans = new ArrayList<Integer>();
        GameModel.getInstance().getTerrUnits(terrName, ans);
        return ans;
    }

    /**
     * Display units in view
     * @param l
     */
    private void showTerrUnitsInText(ArrayList<Integer>l){
        n_level0.setText(String.valueOf(l.get(0)));
        n_level1.setText(String.valueOf(l.get(1)));
        n_level2.setText(String.valueOf(l.get(2)));
        n_level3.setText(String.valueOf(l.get(3)));
        n_level4.setText(String.valueOf(l.get(4)));
        n_level5.setText(String.valueOf(l.get(5)));
        n_level6.setText(String.valueOf(l.get(6)));
    }

    /**
     * Init the map in view
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        terrOwner.setText("");
        terrName.setText("");
        n_level0.setText("");
        n_level1.setText("");
        n_level2.setText("");
        n_level3.setText("");
        n_level4.setText("");
        n_level5.setText("");
        n_level6.setText("");
    }
}
