package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.View.MapView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
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

    public UnitDeployDialogController(Stage window, boolean debug) {
        this.window = window;
        this.debug = debug;
        this.n_player = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();;
    }

    @FXML
    public void clickOnConfirm(ActionEvent actionEvent) {
    }

    @Override
    public void setTerrInfo(String terrName) {
        // set terr info based on click terrName, may be copy from other finished controller
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        terrName.setText("");
        lv0_n.setText("");
        lv1_n.setText("");
        lv2_n.setText("");
        lv3_n.setText("");
        lv4_n.setText("");
        lv5_n.setText("");
        lv6_n.setText("");
        spy_n.setText("");

        //set map
        try {
            mapPane.getChildren().add(new MapView(null,debug).loadMap(n_player, this, "unitDeployDialogView"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
