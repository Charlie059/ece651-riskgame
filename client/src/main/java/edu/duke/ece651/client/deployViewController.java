package edu.duke.ece651.client;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class deployViewController implements Initializable {
    @FXML
    Text playerID_t,territories_t,food_t,techResource_n;
    @FXML
    Text level1_n,level2_n,level3_n,level4_n,level5_n,level6_n;
    @FXML
    ChoiceBox<String>territorySelect;
    @FXML
    ChoiceBox<Integer>levelSelect;
    @FXML
    ChoiceBox<Integer>numberSelect;
    @FXML
    Text errorMsg;

    private final Stage window;

    @FXML
    public void clickOnViewMap(){
        Stage mapWindow = new Stage();
        // TODO load map view..
        mapWindow.show();
    }

    @FXML
    public void clickOnCommit(){
        String selectTerr = territorySelect.getValue();
        Integer selectLevel = levelSelect.getValue();
        Integer selectNumber = numberSelect.getValue();

        // simple input check
        if(selectLevel==null || selectNumber==null || selectTerr==null){
            errorMsg.setText("You choose wrong value.");
            return;
        }

        // TODO: commit and record one deployment (double check with those already commit deployment?)
        System.out.println(territorySelect.getValue()+" "+levelSelect.getValue()+" "+numberSelect.getValue());
    }

    @FXML
    public void clickOnDone(){
        // TODO: submit all deploy and enter gameView
    }

    private ObservableList<String> getTerritoryList(){
        // TODO: get available territories from server
        return FXCollections.observableArrayList("a1","a2","a3");
    }

    private ObservableList<Integer>getLevelList(){
        // TODO: get available levels from server
        return FXCollections.observableArrayList(1,2,3,4,5,6);
    }

    private ObservableList<Integer>createAvailalbeNumList(int max){
        ObservableList<Integer> l = FXCollections.observableArrayList();
        for(int i=1; i<=max; i++){
            l.add(i);
        }
        return l;
    }

    private int getInitalUnitsNumber(){
        // TODO: get initial level 1 units number from server
        return 10;
    }

    private int getPlayerID(){
        // TODO: get PlayerID of in this game
        return 1;
    }

    private int getFood(){
        // TODO: get Food resource in this game
        return 1;
    }

    private int getTechResource(){
        // TODO: get TechResource of in this game
        return 1;
    }

    private void setTerrText(ObservableList<String>terrList){
        StringBuilder s = new StringBuilder("   ");
        for(String t:terrList){
            s.append(t).append(", ");
        }
        territories_t.setText(s.toString());
    }

    private void setUnitNumberText(int initNum){
        level1_n.setText("  "+ String.valueOf(initNum));
        level2_n.setText("  "+"0");
        level3_n.setText("  "+"0");
        level4_n.setText("  "+"0");
        level5_n.setText("  "+"0");
        level6_n.setText("  "+"0");
    }


    public deployViewController(Stage window){
        this.window = window;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int initNum = getInitalUnitsNumber();
        ObservableList<String> terrList = getTerritoryList();
        ObservableList<Integer> numberList = createAvailalbeNumList(initNum);
        ObservableList<Integer> levelList = getLevelList();

        territorySelect.setItems(terrList);
        levelSelect.setItems(levelList);
        numberSelect.setItems(numberList);
        playerID_t.setText("   " + getPlayerID());
        food_t.setText("   " + getFood());
        techResource_n.setText("   " + getTechResource());
        setTerrText(terrList);
        setUnitNumberText(initNum);
    }
}
