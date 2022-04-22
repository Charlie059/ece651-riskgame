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
    private final ObservableList<String> selectSpyList;  // should be the terrs own by current player, add in the initialize
    private final ObservableList<String> selectSpyTypeList;

    public SpecialSpyDialogController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
        this.n_player = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();
        this.selectSpyList = FXCollections.observableArrayList();
        this.selectSpyTypeList = FXCollections.observableArrayList();
    }

    @FXML
    public void clickOnConfirm(ActionEvent actionEvent) {
        window.close();

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

        selectSpyTypeList.add("The Rosenbergs");
        selectSpyTypeList.add("Harriet Tubman");
        selectSpyType.setItems(selectSpyTypeList);

        //set map
        try {
            mapPane.getChildren().add(new MapView(null,debug).loadMap(n_player, this, "specialSpyDialogView"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
