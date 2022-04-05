package edu.duke.ece651.client.Controller;


import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.client.View.MainGameView;
import edu.duke.ece651.client.View.MapView;
import edu.duke.ece651.shared.map.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
    // Passing Game Model
    private Model model;
    // these three lists are used in choiceBox
    private ObservableList<String> terrList;
    private ObservableList<Integer> numberList;
    private ObservableList<Integer> levelList;
    private int deployNum;


    /**
     * Constructor of DeployViewController
     * @param window Window
     * @param model GameModel
     */
    public DeployViewController(Stage window, Model model){
        this.window = window;
        this.model = model;
        levelList = FXCollections.observableArrayList();
        terrList = FXCollections.observableArrayList();
        numberList = FXCollections.observableArrayList();
    }


    @FXML
    public void clickOnViewMap() throws IOException {
        // TODO: get n_players
        new MapView().show(new Stage(),null,2);
    }

    @FXML
    public void clickOnCommit()  throws IOException {
        // Send commit request to model
        if(GameModel.getInstance().deCommit(true)){
            new MainGameView().show(window,null);
        }
        else {
            errorMsg.setText("Commit fail, you may have deployed unit or internet error");
        }
    }

    /**
     * When player finish one round of deployment send value to model
     */
    @FXML
    public void clickOnDone(){
        // Get user input
        String selectTerr = territorySelect.getValue();
        Integer selectLevel = levelSelect.getValue();
        Integer selectNumber = numberSelect.getValue();

        // Simple input check
        if(selectLevel==null || selectNumber==null || selectTerr==null){
            errorMsg.setText("You choose wrong value.");
            return;
        }

        // Send user select to model and get response
        if(GameModel.getInstance().doDeploy(true)){
            // Update the deployment view
            this.deployNum -= selectNumber;
            setUnitNumberText(this.deployNum);
        }
        else {
            this.errorMsg.setText("Invalid deployment number");
        }
    }

    /**
     * Set Territory List
     * @param l ObservableList
     */
    private void setTerritoryList(ObservableList<String>l){
        // Get available territories from server and then add them into observableArrayList
        HashMap<String, Territory> myTerritories = GameModel.getInstance().getClientPlayerPacket().getMyTerritories();
        for (String terrName : myTerritories.keySet()) {
            l.add(terrName);
        }
    }


    /**
     * Set the deployment level to the default 0
     * @param l ObservableList
     */
    private void setLevelList(ObservableList<Integer>l){
        l.add(0);
    }

    /**
     * Add the num of unit to the ObservableList
     * @param l ObservableList
     * @param deployNum
     */
    private void createAvailalbeNumList(ObservableList<Integer> l, int deployNum){
        l.clear();
        for(int i=1; i<=deployNum; i++){
            l.add(i);
        }
    }

    /**
     * Get init total deployment number from the model
     * @return total deployment from model
     */
    private int getInitialUnitsDeployNumber(){
        // Get total deployment number from model
        return GameModel.getInstance().getClientPlayerPacket().getTotalDeployment();
    }

    /**
     * Get PlayerID of in this game
     * @return PlayerID
     */
    private String getPlayerID(){
        return GameModel.getInstance().getClientPlayerPacket().getAccountID().getAccountID();
    }

    /**
     * Get food from model
     * @return food resource
     */
    private int getFoodResource(){
        return GameModel.getInstance().getClientPlayerPacket().getFoodResource();
    }

    /**
     * Get tech resource
     * @return tech resource
     */
    private int getTechResource(){
        return GameModel.getInstance().getClientPlayerPacket().getTechResource();
    }

    private void setTerrText(ObservableList<String>terrList){
        StringBuilder s = new StringBuilder("   ");
        for(String t:terrList){
            s.append(t).append(", ");
        }
        territories_t.setText(s.toString());
    }

    /**
     * Set the UnitNumber now
     * @param initNum
     */
    private void setUnitNumberText(int initNum){
        level0_n.setText("  "+ String.valueOf(initNum));
        level1_n.setText("  "+"0");
        level2_n.setText("  "+"0");
        level3_n.setText("  "+"0");
        level4_n.setText("  "+"0");
        level5_n.setText("  "+"0");
        level6_n.setText("  "+"0");
    }
    


    /**
     * Init the view, get data from GameModel
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get total deployment number form model and add them into list
        this.deployNum = getInitialUnitsDeployNumber();
        createAvailalbeNumList(this.numberList, deployNum);

        // Set Territory List
        setTerritoryList(this.terrList);
        setLevelList(this.levelList);

        // Set value from the view to select
        this.territorySelect.setItems(this.terrList);
        this.levelSelect.setItems(this.levelList);
        this.numberSelect.setItems(this.numberList);

        // Display player ID
        this.playerID_t.setText("   " + getPlayerID());
        this.food_t.setText("   " + getFoodResource());
        this.techResource_n.setText("   " + getTechResource());
        setTerrText(this.terrList); // Show terr text info
        setUnitNumberText(deployNum);
    }
}
