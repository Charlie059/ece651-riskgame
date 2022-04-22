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
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;


public class AttackDialogController implements Initializable,Communication {
    @FXML
    Text terrName,lv0_n,lv1_n,lv2_n,lv3_n,lv4_n,lv5_n,lv6_n,spy_n;
    @FXML
    ChoiceBox<String> selectTo;
    @FXML
    ChoiceBox<Integer>selectNum,selectLevel;
    @FXML
    Pane mapPane;

    private final Stage window;
    private final boolean debug;
    private final int n_player;
    private final ObservableList<String> toList;
    private final ObservableList<Integer> numList;
    private final ObservableList<Integer> levelList;
    private String clickTerr;


    public AttackDialogController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
        this.n_player = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();;
        this.toList = FXCollections.observableArrayList();
        this.numList = FXCollections.observableArrayList();
        this.levelList = FXCollections.observableArrayList();
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
        selectNum.setItems(numList);
        selectTo.setItems(toList);
        selectLevel.setItems(levelList);

        //set map
        try {
            mapPane.getChildren().add(new MapView(null,debug).loadMap(n_player, this, "attackDialogView"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnAttack(ActionEvent actionEvent) {
        if(!GameModel.getInstance().doAttack(new String[]{this.clickTerr, selectTo.getValue() , String.valueOf(selectLevel.getValue()), String.valueOf(selectNum.getValue())}, debug)){
            System.out.println("Invalid value (Server check)");
        }
        else {
            String record = "Use "+ selectNum.getValue() + " Level "+selectLevel.getValue() + " units to attack Territory " + selectTo.getValue() + " From "+this.clickTerr;
            System.out.println(record);
            window.close();
        }

    }

    @Override
    public void setTerrInfo(String clickTerr) {
        numList.clear();
        toList.clear();
        levelList.clear();

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


            // Get all enemyTerrList
            ArrayList<String> attackTerrNames = GameModel.getInstance().getAttackTerrName();
            toList.addAll(attackTerrNames);

            // Set the max value to the max value of unitNumList
            for(int i = 0; i < Collections.max(unitNumList); i++){
                numList.add(i + 1);
            }

            // Add levelList
            levelList.add(0);
            levelList.add(1);
            levelList.add(2);
            levelList.add(3);
            levelList.add(4);
            levelList.add(5);
            levelList.add(6);
        }
        // else if in the cache
        else if(GameModel.getInstance().getLocalEnemyTerrs().containsKey(clickTerr)){
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


            // Add levelList
            levelList.add(0);
            levelList.add(1);
            levelList.add(2);
            levelList.add(3);
            levelList.add(4);
            levelList.add(5);
            levelList.add(6);
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

    }
}
