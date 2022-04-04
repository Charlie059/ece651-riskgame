package edu.duke.ece651.client.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AttackDialogController implements Initializable {
    @FXML
    TextField terrFrom,terrTo,selectLevel,selectNum;
    @FXML
    ListView<String> attackList;

    private Stage window;
    private ObservableList<String> list;

    public AttackDialogController(Stage window){this.window = window;}

    @FXML
    public void clickOnAddButton(){
        String record = "Use "+ selectNum.getText() + " Level "+selectLevel.getText() + " units to attack Territory " + terrTo.getText() + " From "+terrFrom.getText();
        list.add(record);
        terrFrom.clear();
        terrTo.clear();
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
        attackList.setItems(list);
    }
}
