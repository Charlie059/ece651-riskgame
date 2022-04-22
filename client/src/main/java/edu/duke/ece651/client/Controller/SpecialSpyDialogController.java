package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.View.MapView;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;
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
import java.util.UUID;

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
    private String clickTerr;

    public SpecialSpyDialogController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
        this.n_player = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();
        this.selectSpyList = FXCollections.observableArrayList();
        this.selectSpyTypeList = FXCollections.observableArrayList();
    }

    @FXML
    public void clickOnConfirm(ActionEvent actionEvent) {
        String res =  GameModel.getInstance().useSpecialSpy(clickTerr, selectSpy.getValue(), selectSpyType.getValue(), debug);
        if(res != null) System.out.println(res);
        else window.close();

    }

    @Override
    public void setTerrInfo(String clickTerr) {
        // set terr info based on click terrName, may be copy from other finished controller
        terrName.setText(clickTerr);

        // Set clickTerr
        this.clickTerr = clickTerr;

        // Clear the selectSpy
        selectSpyList.removeAll();

        // Get My Terr Info
        if (GameModel.getInstance().getMyTerrList().contains(clickTerr)) {
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
            if (spyInfo.containsKey(clickTerr)) {
                spy_n.setText(String.valueOf(spyInfo.get(clickTerr).size()));
            } else {
                spy_n.setText("0");
            }



        }
        // else if in the cache
        else if (GameModel.getInstance().getLocalEnemyTerrs().containsKey(clickTerr)) {
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
            if (spyInfo.containsKey(clickTerr)) {
                spy_n.setText(String.valueOf(spyInfo.get(clickTerr).size()));
            } else {
                spy_n.setText("0");
            }


        }
        // Inviable
        else {
            lv0_n.setText("NA");
            lv1_n.setText("NA");
            lv2_n.setText("NA");
            lv3_n.setText("NA");
            lv4_n.setText("NA");
            lv5_n.setText("NA");
            lv6_n.setText("NA");
            spy_n.setText("NA");
        }

        // init selectSpyList
        HashMap<String, ArrayList<Spy>> spyInfo = GameModel.getInstance().getClientPlayerPacket().getSpyInfo();
        if(spyInfo.containsKey(clickTerr)){

            // Get the spy list
            ArrayList<Spy> spyArrayList = spyInfo.get(clickTerr);
            for (int i = 0; i < spyArrayList.size(); i++){
                Integer spyType = spyArrayList.get(0).getSpyType();
                String spyTypeName;

                if(spyType == 1){
                    spyTypeName = "defaultType";
                }
                else if(spyType == 2){
                    spyTypeName = "rosenbergs";
                }
                else {
                    spyTypeName = "harrietTubman";
                }

                selectSpyList.add(spyTypeName);
                selectSpy.setItems(selectSpyList);
            }

        }
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
