package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.View.AttackDialogView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainGameViewController implements Initializable {
    @FXML
    Text playerID_t,territories_t,food_t,techResource_n;
    @FXML
    Text level1_n,level2_n,level3_n,level4_n,level5_n,level6_n;

    private final Stage window;
    private ObservableList<String> terrList;

    public MainGameViewController(Stage window) {
        this.window = window;
        terrList = FXCollections.observableArrayList();
    }

    private void setTerritoryList(ObservableList<String>l){
        // TODO: get available territories from server and then add them into observableArrayList
        l.add("a1");
        l.add("a2");
        l.add("a3");
    }

    private int getInitalUnitsNumber(){
        // TODO: get initial level 1 units number from server
        return 10;
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
        level1_n.setText("  "+ String.valueOf(initNum));
        level2_n.setText("  "+"0");
        level3_n.setText("  "+"0");
        level4_n.setText("  "+"0");
        level5_n.setText("  "+"0");
        level6_n.setText("  "+"0");
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

    @FXML
    public void clickOnViewMap(){}
    @FXML
    public void clickOnAttack() throws IOException {
        new AttackDialogView().show(new Stage(),null);
    }
    @FXML
    public void clickOnMove(){}
    @FXML
    public void clickOnUpgradeUnitButton(){}
    @FXML
    public void clickOnUpgradeTechButton(){}
    @FXML
    public void clickOnDone(){}
    @FXML
    public void clickOnExit(){
        window.close();
    }
    @FXML
    public void clickOnSwitchUser(){}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int initNum = getInitalUnitsNumber();
        setTerritoryList(terrList);

        playerID_t.setText("   " + getPlayerID());
        food_t.setText("   " + getFood());
        techResource_n.setText("   " + getTechResource());
        setTerrText(terrList);
        setUnitNumberText(initNum);
    }
}
