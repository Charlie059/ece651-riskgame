package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.View.MapView;
import edu.duke.ece651.shared.Wrapper.AccountID;
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


public class SanctionDialogController implements Initializable,Communication {
        @FXML
        Text terrName, terrOwner, lv0_n,lv1_n,lv2_n,lv3_n,lv4_n,lv5_n,lv6_n,spy_n;
        @FXML
        ChoiceBox<String> selectPlayer;

        @FXML
        Pane mapPane;

        private final Stage window;
        private final boolean debug;
        private final int n_player;
        private String clickTerr;
        private final ObservableList<String> toList;


        public SanctionDialogController(Stage window, boolean debug){
            this.window = window;
            this.debug = debug;
            this.n_player = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();
            this.toList = FXCollections.observableArrayList();
        }


        @Override
        public void initialize(URL location, ResourceBundle resources) {
            terrOwner.setText("");
            terrName.setText("");
            lv0_n.setText("");
            lv1_n.setText("");
            lv2_n.setText("");
            lv3_n.setText("");
            lv4_n.setText("");
            lv5_n.setText("");
            lv6_n.setText("");
            spy_n.setText("");
            selectPlayer.setItems(toList);

            //set map
            try {
                mapPane.getChildren().add(new MapView(null,debug).loadMap(n_player, this, "sanctionDialogView"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @FXML
        public void clickOnSanction(ActionEvent actionEvent) {

            String res = GameModel.getInstance().useSanction(this.terrOwner.getText(), debug);
            if(res != null){
                System.out.println(res);
            }
            else {
                String record = "Use Sanction to " + this.terrOwner.getText();
                System.out.println(record);
                window.close();
            }
        }

        @Override
        public void setTerrInfo(String clickTerr) {
            // set terr info based on click terrName, may be copy from other finished controller
            terrName.setText(clickTerr);
            toList.clear();


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

                // Set my Account
                terrOwner.setText(GameModel.getInstance().getClientPlayerPacket().getAccountID().getAccountID());

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


                // Set account id
                HashMap<String, String> terr2Account =  GameModel.getInstance().getTerrToAccountHashmap();
                if (terr2Account.containsKey(clickTerr)) {
                    terrOwner.setText(terr2Account.get(clickTerr));
                }

                // Add select list
                this.toList.add(this.terrOwner.getText());
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
                terrOwner.setText("NA");
            }

        }
}
