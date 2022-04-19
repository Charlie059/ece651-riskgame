package edu.duke.ece651.client.Controller;


import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.View.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SpyDialogController implements Initializable,Communication {
    @FXML
    Text terrName,lv0_n,lv1_n,lv2_n,lv3_n,lv4_n,lv5_n,lv6_n,spy_n;
    @FXML
    ChoiceBox<String> selectTo;
    @FXML
    Text cost_t;
    @FXML
    Pane mapPane;

    private final Stage window;
    private final boolean debug;
    private final int n_player;
    private final ObservableList<String> toList;


    public SpyDialogController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
        this.n_player = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();;
        this.toList = FXCollections.observableArrayList();
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
        cost_t.setText("Unknown");
        selectTo.setItems(toList);

        //set map
        try {
            mapPane.getChildren().add(new MapView(null,debug).loadMap(n_player, this, "spyDialogView"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void clickOnConfirm(ActionEvent actionEvent) {
        window.close();
    }

    @FXML
    public void clickOnGetCost(ActionEvent actionEvent) {
        cost_t.setText("123");
    }

    @Override
    public void setTerrInfo(String clickTerr) {
        toList.clear();

        terrName.setText(clickTerr);
        lv0_n.setText(clickTerr);
        lv1_n.setText(clickTerr);
        lv2_n.setText(clickTerr);
        lv3_n.setText(clickTerr);
        lv4_n.setText(clickTerr);
        lv5_n.setText(clickTerr);
        lv6_n.setText(clickTerr);
        spy_n.setText(clickTerr);

        // set choiceboxes based on which territory you click.
        toList.add(clickTerr);
    }
}
