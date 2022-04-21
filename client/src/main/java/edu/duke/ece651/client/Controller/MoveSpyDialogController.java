package edu.duke.ece651.client.Controller;


import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.View.MapView;
import edu.duke.ece651.shared.map.Spy;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class MoveSpyDialogController implements Initializable,Communication {
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
    private String clickTerr;


    public MoveSpyDialogController(Stage window, boolean debug){
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
            mapPane.getChildren().add(new MapView(null,debug).loadMap(n_player, this, "moveSpyDialogView"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void clickOnConfirm(ActionEvent actionEvent) {
        if(!GameModel.getInstance().doMoveSpy(new String[]{this.clickTerr, this.selectTo.getValue()}, debug)){
            System.out.println("Invalid value (Server check)");
        }
        else {
            String record = "Move Spy from " + this.clickTerr + " to " +  this.selectTo.getValue();
            System.out.println(record);
            window.close();
        }
    }

    @FXML
    public void clickOnGetCost(ActionEvent actionEvent) {
        cost_t.setText("NA");
    }

    @Override
    public void setTerrInfo(String clickTerr) {
        toList.clear();


        terrName.setText(clickTerr);

        // Set clickTerr
        this.clickTerr = clickTerr;


        // Get My Terr Info
        if(GameModel.getInstance().getMyTerrList().contains(clickTerr)){
            ArrayList<Integer> unitNumList = new ArrayList<>();
            GameModel.getInstance().getTerrUnits(clickTerr, unitNumList);

            lv0_n.setText(String.valueOf(unitNumList.get(0)));
            lv1_n.setText(String.valueOf(unitNumList.get(1)));
            lv2_n.setText(String.valueOf(unitNumList.get(2)));
            lv3_n.setText(String.valueOf(unitNumList.get(3)));
            lv4_n.setText(String.valueOf(unitNumList.get(4)));
            lv5_n.setText(String.valueOf(unitNumList.get(5)));
            lv6_n.setText(String.valueOf(unitNumList.get(6)));

            // Setup spy
            HashMap<String, ArrayList<Spy>> spyInfo = GameModel.getInstance().getClientPlayerPacket().getSpyInfo();
            if(spyInfo.containsKey(clickTerr)){
                spy_n.setText(String.valueOf(spyInfo.get(clickTerr).size()));
            }
            else{
                spy_n.setText("0");
            }



        }
        // else if in the cache
        else if(GameModel.getInstance().getLocalEnemyTerrs().containsKey(clickTerr)) {
            // Get the units number
            ArrayList<Integer> unitNumList = GameModel.getInstance().getLocalEnemyTerrs().get(clickTerr);
            lv0_n.setText(String.valueOf(unitNumList.get(0)));
            lv1_n.setText(String.valueOf(unitNumList.get(1)));
            lv2_n.setText(String.valueOf(unitNumList.get(2)));
            lv3_n.setText(String.valueOf(unitNumList.get(3)));
            lv4_n.setText(String.valueOf(unitNumList.get(4)));
            lv5_n.setText(String.valueOf(unitNumList.get(5)));
            lv6_n.setText(String.valueOf(unitNumList.get(6)));


            // Setup spy
            HashMap<String, ArrayList<Spy>> spyInfo = GameModel.getInstance().getClientPlayerPacket().getSpyInfo();
            if(spyInfo.containsKey(clickTerr)){
                spy_n.setText(String.valueOf(spyInfo.get(clickTerr).size()));
            }
            else{
                spy_n.setText("0");
            }
        }
        // Inviable
        else{
            lv0_n.setText("NA");
            lv1_n.setText("NA");
            lv2_n.setText("NA");
            lv3_n.setText("NA");
            lv4_n.setText("NA");
            lv5_n.setText("NA");
            lv6_n.setText("NA");
            spy_n.setText("NA");
        }
        // Get all TerrList
        this.toList.addAll(GameModel.getInstance().getAllTerrName());
    }
}
