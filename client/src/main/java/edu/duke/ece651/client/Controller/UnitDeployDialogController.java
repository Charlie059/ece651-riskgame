package edu.duke.ece651.client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UnitDeployDialogController implements Initializable,Communication{
    @FXML
    Text terrName,lv0_n,lv1_n,lv2_n,lv3_n,lv4_n,lv5_n,lv6_n,spy_n;
    @FXML
    Pane mapPane;

    private final Stage window;
    private final boolean debug;
    private final int n_player;
    private String clickTerr;

    public UnitDeployDialogController(Stage window, boolean debug, int n_player) {
        this.window = window;
        this.debug = debug;
        this.n_player = n_player;
    }

    @FXML
    public void clickOnConfirm(ActionEvent actionEvent) {
    }

    @Override
    public void setTerrInfo(String terrName) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
