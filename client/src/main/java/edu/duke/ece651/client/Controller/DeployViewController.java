package edu.duke.ece651.client.Controller;


import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.client.View.MainGameView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeployViewController implements Initializable {
    @FXML
    Text playerID_t,territories_t,food_t,techResource_n;
    @FXML
    Text level0_n,level1_n,level2_n,level3_n,level4_n,level5_n,level6_n;
    @FXML
    ChoiceBox<String>territorySelect;
    @FXML
    ChoiceBox<Integer>levelSelect;
    @FXML
    ChoiceBox<Integer>numberSelect;
    @FXML
    Text errorMsg;

    private final Stage window;
    private Model model;

    // these three lists are used in choiceBox
    private ObservableList<String> terrList;
    private ObservableList<Integer> numberList;
    private ObservableList<Integer> levelList;

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
    public void clickOnDone() throws IOException {
        // TODO: submit all deployment and get response from server, then enter the mainView
        new MainGameView().show(window,null);
    }

    private void setTerritoryList(ObservableList<String>l){
        // TODO: get available territories from server and then add them into observableArrayList
        l.add("a1");
        l.add("a2");
        l.add("a3");
    }

    private void setLevelList(ObservableList<Integer>l){
        // TODO: get available levels from server and then add them into observableArrayList
        l.add(1);
        l.add(2);
        l.add(3);
    }

    private void createAvailalbeNumList(ObservableList<Integer> l, int max){
        l.clear();
        for(int i=1; i<=max; i++){
            l.add(i);
        }
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
        // TODO: change it and get number of each units from server. remove initNum
        level0_n.setText("  "+ String.valueOf(initNum));
        level1_n.setText("  "+"0");
        level2_n.setText("  "+"0");
        level3_n.setText("  "+"0");
        level4_n.setText("  "+"0");
        level5_n.setText("  "+"0");
        level6_n.setText("  "+"0");
    }
    
    public DeployViewController(Stage window, Model model){
        this.window = window;
        this.model = model;
        levelList = FXCollections.observableArrayList();
        terrList = FXCollections.observableArrayList();
        numberList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int initNum = getInitalUnitsNumber();
        createAvailalbeNumList(numberList, initNum);
        setTerritoryList(terrList);
        setLevelList(levelList);

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
