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


public class MoveDialogController implements Initializable {
    @FXML
    TextField terrFrom,terrTo,selectLevel,selectNum;
    @FXML
    ListView<String> moveList;
    @FXML
    Text error_msg;

    private final Stage window;
    private ObservableList<String> list;

    public MoveDialogController(Stage window){this.window = window;}

    @FXML
    public void clickOnAddButton(){
        String record = "Move "+ selectNum.getText() + " Level "+selectLevel.getText() + " units from Territory " + terrFrom.getText() + " to "+terrTo.getText();
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
        moveList.setItems(list);
    }
}
