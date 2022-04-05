package edu.duke.ece651.client.Controller;

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
    TextField terrFrom,selectCurLevel,selectLevel,selectNum,selectUpgradeLevel;
    @FXML
    ListView<String> upgradeList;
    @FXML
    Text error_msg;

    private final Stage window;
    private ObservableList<String> list;

    public UpgradeUnitDialogController(Stage window){this.window = window;}

    @FXML
    public void clickOnAddButton(){
        // add format check if it is null.
        String record = "Update "+selectNum.getText()+" units to level "+selectUpgradeLevel.getText()+" from level "+selectCurLevel.getText()+" in "+terrFrom.getText();
        list.add(record);
        terrFrom.clear();
        selectNum.clear();
        selectLevel.clear();
    }

    @FXML
    public void clickOnSubmitButton(){
        //TODO: submit all the records in list to server
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();
        upgradeList.setItems(list);
    }
}
