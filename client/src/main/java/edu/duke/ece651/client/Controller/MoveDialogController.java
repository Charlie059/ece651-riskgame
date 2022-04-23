package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Checker.AttackChecker;
import edu.duke.ece651.client.Checker.MoveChecker;
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


public class MoveDialogController implements Initializable {
    @FXML
    TextField terrFrom,terrTo,selectLevel,selectNum;
    @FXML
    ListView<String> moveList;
    @FXML
    Text error_msg;

    private final Stage window;
    private ObservableList<String> list;
    private boolean debug;

    public MoveDialogController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
    }

    //DO NOTHING
    @FXML
    public void clickOnAddButton(){}

    @FXML
    public void clickOnSubmitButton(){

        // if pass local checker, then send request to model
        if(!GameModel.getInstance().doMove(new String[]{terrFrom.getText(), terrTo.getText(),selectLevel.getText(),selectNum.getText()}, debug)){
            this.error_msg.setText("Invalid value (Server check)");
        }
        else {
            String record = "Use "+ selectNum.getText() + " Level "+selectLevel.getText() + " units to attack Territory " + terrTo.getText() + " From "+terrFrom.getText();
            System.out.println(record);
            window.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();
        moveList.setItems(list);
    }
}
