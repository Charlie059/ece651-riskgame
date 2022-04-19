package edu.duke.ece651.client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapViewController implements Initializable  {
    @FXML
    Button a1,a2,a3,b1,b2,b3,c1,c2,c3,d1,d2,d3,e1,e2,e3;

    private boolean debug;
    private final int n_players;
    private final Communication outsideController;
    private final ArrayList<Button> terrList;
    private String callFrom;

    public MapViewController(Communication outsideController, int n_players, boolean debug, String callFrom){
        this.debug = debug;
        this.outsideController = outsideController;
        this.n_players = n_players;
        this.callFrom = callFrom;
        this.terrList = new ArrayList<Button>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // change color and invisible mode based on callFrom string. If it is called from attack/move/mainGameView/deploy..., show war-fog.
        setTerrList();
        for(int i=0;i<n_players*3;i++){
            Button btn = terrList.get(i);
            btn.setStyle("-fx-background-color: "+getColor(btn.getText()));  // set different color for different territory.
            //also can set territory invisiable for war-fog here
//             a1.setVisible(false);
        }
    }

    /**
     * If user click terr.return terr name
     * @param ae ActionEvent
     *
     */
    @FXML
    public void clickOnTerr(ActionEvent ae){
        Object source = ae.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            //System.out.println(btn.getText());
            if(outsideController != null)
                outsideController.setTerrInfo(btn.getText());
        }
        else {  // situation should never come up
            throw new IllegalArgumentException("Invalid source " +
                    source +
                    " for ActionEvent");
        }
    }

    private String getColor(String terrName){
        return "#83ae52";  // get color from server
    }

    private void setTerrList(){
        terrList.add(a1);
        terrList.add(a2);
        terrList.add(a3);
        terrList.add(b1);
        terrList.add(b2);
        terrList.add(b3);
        terrList.add(c1);
        terrList.add(c2);
        terrList.add(c3);
        terrList.add(d1);
        terrList.add(d2);
        terrList.add(d3);
        terrList.add(e1);
        terrList.add(e2);
        terrList.add(e3);
    }


}
