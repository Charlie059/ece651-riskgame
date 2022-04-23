package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Checker.UpgradeChecker;
import edu.duke.ece651.client.Model.GameModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpgradeUnitDialogController implements Initializable {
    @FXML
    TextField terrFrom,selectCurLevel,selectNum,selectUpgradeLevel;
    @FXML
    ListView<String> upgradeList;
    @FXML
    Text error_msg;

    private final Stage window;
    private ObservableList<String> list;
    private boolean debug;

    public UpgradeUnitDialogController(Stage window, boolean debug){this.window = window;this.debug = debug;}

    // DO NOTHING
    @FXML
    public void clickOnAddButton(){}

    // User click submit button
    @FXML
    public void clickOnSubmitButton(){

        // if pass local checker, then send request to model
        if(!GameModel.getInstance().doUpgradeUnit(new String[]{terrFrom.getText(),selectCurLevel.getText(),selectNum.getText(),selectUpgradeLevel.getText()}, debug)){
            this.error_msg.setText("Invalid value (Server check)");
        }
        else {
            String record = "Upgrade "+ selectNum.getText() + " Level "+selectCurLevel.getText() + " units From "+terrFrom.getText() + " to level " + selectUpgradeLevel.getText();
            System.out.println(record);
            window.close();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();
        upgradeList.setItems(list);
    }
}
