package edu.duke.ece651.client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class MapViewController {
    @FXML
    Label terrSize,terrName,n_level0,n_level1,n_level2,n_level3,n_level4,n_level5,n_level6;

    private Stage window;

    public MapViewController(Stage window){
        this.window = window;
        // TODO: change background color of the button based on its owner.  get button object based on its fx:id
    }

    @FXML
    public void clickOnTerr(ActionEvent ae){
        Object source = ae.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            String terrName = btn.getText();

//            getTerrOwner(terrName);
//            showOwnerInText();
//            getTerrUnits(terrName);
//            showTerrUnitsInText();
//            or other possible info need to show

        }
        else {  // situation should never come up
            throw new IllegalArgumentException("Invalid source " +
                    source +
                    " for ActionEvent");
        }

    }

    /**
     * get owner of the specific territory based on its name. return playerID(1,2,3,4,5). Each Player ID corresponding a color?
     * or directly return a color(in string format)
     */
    private String getTerrOwner(String terrName){
        return "";
    }

    /**
     * get units number of the specific territory based on its name. return in a list, correspoing to the number of each level.
     */
    private ArrayList<Integer> getTerrUnits(String terrName) {return null;}

}
