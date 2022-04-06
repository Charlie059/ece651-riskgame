package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.View.*;
import edu.duke.ece651.shared.map.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainGameViewController implements Initializable {
    @FXML
    Text playerID_t,territories_t,food_t,techResource_n;
    @FXML
    Text level0_n,level1_n,level2_n,level3_n,level4_n,level5_n,level6_n;

    private final Stage window;
    private ObservableList<String> terrList;

    public MainGameViewController(Stage window) {
        this.window = window;
        terrList = FXCollections.observableArrayList();
    }

    private void setMyTerritoryList(ObservableList<String>l){
        // Clear the list
        l.clear();
        // Get my terr list from model
        HashMap<String, Territory> myTerritories = GameModel.getInstance().getClientPlayerPacket().getMyTerritories();
        l.addAll(myTerritories.keySet());
    }


    private void setTerrText(ObservableList<String>terrList){
        StringBuilder s = new StringBuilder("   ");
        for(String t:terrList){
            s.append(t).append(", ");
        }
        territories_t.setText(s.toString());
    }

    /**
     * Set deployment unit num to 0
     */
    private void setUnitNumberText(){
        level0_n.setText("  "+"0");
        level1_n.setText("  "+"0");
        level2_n.setText("  "+"0");
        level3_n.setText("  "+"0");
        level4_n.setText("  "+"0");
        level5_n.setText("  "+"0");
        level6_n.setText("  "+"0");
    }

    /**
     * Get PlayerID
     * @return PlayerID
     */
    private String getPlayerID(){
        return GameModel.getInstance().getPlayerID();
    }

    /**
     * Get food Resource
     * @return food Resource
     */
    private int getFood(){
        return GameModel.getInstance().getFoodRes();
    }

    /**
     * Get Tech Resource
     * @return tech Resource
     */
    private int getTechResource(){
        return GameModel.getInstance().getTechRes();
    }


    /**
     * When user click the map view (need numOfPlayers)
     */
    @FXML
    public void clickOnViewMap(){
        // Get numOfPlayer from the Model
        int numOfPlayers =  GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();
        try {
            new MapView().show(new Stage(),null,numOfPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enter the AttackDialogView
     */
    @FXML
    public void clickOnAttack() {
        try {
            new AttackDialogView().show(new Stage(),null);
            // Update view
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enter the MoveDialogView
     */
    @FXML
    public void clickOnMove(){
        try {
            new MoveDialogView().show(new Stage(), null);
            // Update view
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enter UpgradeUnitDialogView
     */
    @FXML
    public void clickOnUpgradeUnitButton(){
        try {
            new UpgradeUnitDialogView().show(new Stage(),null);
            // Update view
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enter UpgradeTechDialogView
     */
    @FXML
    public void clickOnUpgradeTechButton() {
       if(!GameModel.getInstance().doUpgradeTech(true)) {
//           errMsg...
       }
        // Update view
        updateView();
    }


    /**
     * Commit button
     */
    @FXML
    public void clickOnDone(){
        //upgrade everything
        if(!GameModel.getInstance().doCommit(true)){
//            errMsg...
        }
        // Update view
        updateView();
    }

    /**
     * Close the game
     */
    @FXML
    public void clickOnExit(){
        window.close();
    }

    /**
     * Show continueGameView
     * @throws IOException
     */
    @FXML
    public void clickOnSwitchGame() throws IOException {
        if(SceneCollector.continueGameView == null){
            new ContinueGameView().show(window,null);
        }else{
            window.setScene(SceneCollector.continueGameView);
            window.show();
        }
    }

    /**
     * Update view
     */
    private void updateView(){
        // Set my Terr list
        setMyTerritoryList(terrList);

        // Set init playerID, food and tech resource
        playerID_t.setText("   " + getPlayerID());
        food_t.setText("   " + getFood());
        techResource_n.setText("   " + getTechResource());
        setTerrText(terrList);
        setUnitNumberText();
    }
    /**
     * Init the Main Game Vew
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateView();
    }
}
