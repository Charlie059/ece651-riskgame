package edu.duke.ece651.client.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpgradeTechDialogController implements Initializable {
    @FXML
    ChoiceBox<Integer> selectLevel;
    @FXML
    Text error_msg;

//    private Stage window;
//    private ObservableList<Integer> levelList;
//    private boolean debug;

//    @FXML
//    public void clickOnBackButton(){
//        window.close();
//    }
//
//    @FXML
//    public void clickOnSubmitButton(){
//        // TODO: submit upgrade to server
//        window.close();
//    }

    public UpgradeTechDialogController(Stage window, boolean debug){
//        this.window = window;
//        this.debug = debug;
//        levelList = FXCollections.observableArrayList();
    }

//    private void getAvailableLevel(ObservableList<Integer> levelList){
//        // TODO: get availabel level from serer.
//        levelList.add(1);
//        levelList.add(2);
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        getAvailableLevel(levelList);
//        selectLevel.setItems(levelList);
    }
}
