package edu.duke.ece651.client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapViewController implements Initializable {
    @FXML
    Label terrOwner,terrName,n_level0,n_level1,n_level2,n_level3,n_level4,n_level5,n_level6;

    private Stage window;

    public MapViewController(Stage window){
        this.window = window;
    }

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
     * get owner of the specific territory based on its name. return playerID(1,2,3,4,5). Each Player ID corresponding a color?
     * or directly return a color(in string format)
     */
    private String getTerrOwner(String terrName){
        return "fff";
    }

    /**
     * get units number of the specific territory based on its name. return in a list, correspoing to the number of each level.
     */
    private ArrayList<Integer> getTerrUnits(String terrName) {
        ArrayList<Integer>l = new ArrayList<Integer>();
        l.add(3);
        l.add(0);
        l.add(0);
        l.add(0);
        l.add(0);
        l.add(0);
        l.add(0);
        return l;
    }

    private void showTerrUnitsInText(ArrayList<Integer>l){
        n_level0.setText(String.valueOf(l.get(0)));
        n_level1.setText(String.valueOf(l.get(1)));
        n_level2.setText(String.valueOf(l.get(2)));
        n_level3.setText(String.valueOf(l.get(3)));
        n_level4.setText(String.valueOf(l.get(4)));
        n_level5.setText(String.valueOf(l.get(5)));
        n_level6.setText(String.valueOf(l.get(6)));

    }

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
