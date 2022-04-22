package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SpecialSpyDialogController implements Initializable,Communication{
    @FXML
    Text terrName,lv0_n,lv1_n,lv2_n,lv3_n,lv4_n,lv5_n,lv6_n,spy_n;
    @FXML
    Pane mapPane;
    @FXML
    ChoiceBox<String> selectSpy, selectSpyType;

    private final Stage window;
    private final boolean debug;
    private final int n_player;

    public SpecialSpyDialogController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
        this.n_player = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();

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
