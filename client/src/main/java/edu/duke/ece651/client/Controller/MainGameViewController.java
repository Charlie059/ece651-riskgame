package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.View.*;
import edu.duke.ece651.shared.map.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainGameViewController implements Initializable {
    @FXML
    Text playerID_t,territories_t,food_t,techResource_t,points_t,techLevel_t;
    @FXML
    ListView<String> responseList;
    @FXML
    AnchorPane mapPane;

    private final Stage window;
    private final ObservableList<String> terrList;
    private final ObservableList<String> responses;
    private final boolean debug;
    private final int n_players;

    public MainGameViewController(Stage window, boolean debug) throws IOException {
        this.window = window;
        this.debug = debug;
        this.responses = FXCollections.observableArrayList();
        this.terrList = FXCollections.observableArrayList();
        this.n_players = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();
    }

    /**
     * Update view
     */
    private void updateView(){
        // Set my Terr list
        setMyTerritoryList(terrList);

        // Set plyaer info
        playerID_t.setText("   " + getPlayerID());
        food_t.setText("   " + getFood());
        techResource_t.setText("   " + getTechResource());
        techLevel_t.setText("   "+ getTechLevel());
        points_t.setText("   "+ "1234");
        setTerrText(terrList);

        // set map
        try {
            mapPane.getChildren().add(new MapView(null,debug).loadMap(n_players,null,"mainGameView"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Init the Main Game Vew
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        responseList.setItems(responses);
        updateView();
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
     * Get tech level
     * @return tech level
     */
    private int getTechLevel(){
        return GameModel.getInstance().getTechlevel();
    }




    /**
     * Enter the AttackDialogView
     */
    @FXML
    public void clickOnAttack() {
        try {
            new AttackDialogView().show(new Stage(),null,debug);
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
            new MoveDialogView().show(new Stage(), null, debug);
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enter UpgradeUnitDialogView
     */
    @FXML
    public void clickOnUpgradeUnits(){
        try {
            new UpgradeUnitDialogView().show(new Stage(),null, debug);
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnUpgradeTech() {
       if(!GameModel.getInstance().doUpgradeTech(debug)) {
            responses.add("Cannot upgrade, Server Check");
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
     */
    @FXML
    public void clickOnSwitchGame() throws IOException {
        if(SceneCollector.continueGameView == null){
            new ContinueGameView().show(window,null, debug);
        }else{
            window.setScene(SceneCollector.continueGameView);
            window.setTitle("Continue Game");
            window.show();
        }
    }

    @FXML
    public void clickOnTool(){
        try {
            new ToolsDialogView().show(new Stage(), null, debug);
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnMoveSpy(){
        try {
            new MoveSpyDialogView().show(new Stage(), null, debug);
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnDeploySpy(){
        try {
            new DeploySpyDiaglogView().show(new Stage(), null, debug);
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void clickOnCommit(){
        //upgrade everything
        if(!GameModel.getInstance().doCommit(debug)){
            System.out.println("Cannot commit, this should not happened");
        }
        // Update view
        updateView();
    }

    @FXML
    public void clickOnCloak(){
        // check pre-condition. if tech-level < 3, print error.
        responses.add("tech level does not reach 3!");
        try {
            new CloakDialogView().show(new Stage(), null, debug);
            updateView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
