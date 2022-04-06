package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Checker.MoveChecker;
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

    public UpgradeUnitDialogController(Stage window){this.window = window;}

    // DO NOTHING
    @FXML
    public void clickOnAddButton(){}

    // User click submit button
    @FXML
    public void clickOnSubmitButton(){
        // Local checker
        UpgradeChecker upgradeChecker = new UpgradeChecker();
        if(!upgradeChecker.doCheck(new String[]{terrFrom.getText(),selectCurLevel.getText(),selectNum.getText(), selectUpgradeLevel.getText()})){
            this.error_msg.setText("Invalid value, you may upgrade one unit one times");
            return;
        }

        // if pass local checker, then send request to model
        if(!GameModel.getInstance().doUpgradeUnits(new String[]{terrFrom.getText(),selectCurLevel.getText(),selectNum.getText(),selectUpgradeLevel.getText()}, true)){
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
