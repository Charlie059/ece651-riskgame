package edu.duke.ece651.client.Controller;


import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.client.View.MainGameView;
import edu.duke.ece651.client.View.MapView;
import edu.duke.ece651.shared.map.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DeployViewController implements Initializable {
    @FXML
    Text playerID_t,territories_t,food_t,techResource_t,level0_t,points_t;
    @FXML
    ChoiceBox<String>territorySelect;
    @FXML
    ChoiceBox<Integer>levelSelect;
    @FXML
    ChoiceBox<Integer>numberSelect;
    @FXML
    ListView<String> responseList;
    @FXML
    AnchorPane mapPane;

    private final Stage window;
    private final boolean debug;
    private Model model;   //useless

    // these three lists are used in choiceBox
    private final ObservableList<String> terrList;
    private final ObservableList<Integer> numberList;
    private final ObservableList<Integer> levelList;
    private final ObservableList<String> responses;
    private int deployNum;
    private final int n_Player;


    /**
     * Constructor of DeployViewController
     * @param window Window
     * @param model GameModel
     */
    public DeployViewController(Stage window, Model model, int n_Player, boolean debug){
        this.window = window;
        this.model = model;
        this.debug = debug;
        this.n_Player = n_Player;
        levelList = FXCollections.observableArrayList();
        terrList = FXCollections.observableArrayList();
        numberList = FXCollections.observableArrayList();
        responses = FXCollections.observableArrayList();
    }

    /**
     * Init the view, get data from GameModel
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set responseList
        responseList.setItems(responses);

        // Get total deployment number form model and add them into list
        this.deployNum = getInitialUnitsDeployNumber();
        createAvailalbeNumList(this.numberList, deployNum);

        // Set Territory List and level list
        setTerritoryList(this.terrList);
        setLevelList(this.levelList);

        // Set value to choice boxes
        this.territorySelect.setItems(this.terrList);
        this.levelSelect.setItems(this.levelList);
        this.numberSelect.setItems(this.numberList);

        // display player info
        this.playerID_t.setText("   " + getPlayerID());
        this.food_t.setText("   " + getFoodResource());
        this.techResource_t.setText("   " + getTechResource());
        this.points_t.setText("   " + "1234");
        setTerrText(this.terrList); // Show terr text info
        setUnitNumberText(deployNum);

        // load map
        try {
            this.loadMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadMap() throws IOException {
        mapPane.getChildren().add(new MapView(null,debug).loadMap(n_Player,null,"deployView"));
    }

    /**
     * When user click commit
     */
    @FXML
    public void clickOnCommit(){
        // Send commit request to model
        if(GameModel.getInstance().doCommit(debug)){
            try {
                new MainGameView().show(window,null, debug);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            responses.add("Commit fail, you may have deployed unit or internet error");
        }
    }

    /**
     * When player finish one round of deployment send value to model
     */
    @FXML
    public void clickOnDeploy(){
        // Get user input
        String selectTerr = territorySelect.getValue();
        Integer selectLevel = levelSelect.getValue();
        Integer selectNumber = numberSelect.getValue();

        // Simple input check
        if(selectLevel==null || selectNumber==null || selectTerr==null){
            responses.add("You choose wrong value.");
            return;
        }

        // Send user select to model and get response
        if(GameModel.getInstance().doDeploy(selectTerr, selectNumber, debug)){
            // Update the deployment view
            this.deployNum -= selectNumber;
            setUnitNumberText(this.deployNum);
            // Refresh UI
            createAvailalbeNumList(this.numberList, this.deployNum);
            responses.add("Deploy "+selectNumber.toString()+" lv "+ selectLevel.toString()+" units in "+selectTerr);
        }
        else {
            responses.add("Invalid deployment number");
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

    private void setUnitNumberText(int initNum){
        level0_t.setText("  "+ String.valueOf(initNum));
    }



}
