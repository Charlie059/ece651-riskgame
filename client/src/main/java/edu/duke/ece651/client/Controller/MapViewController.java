package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.GameModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapViewController implements Initializable  {
    @FXML
    Button a1,a2,a3,b1,b2,b3,c1,c2,c3,d1,d2,d3,e1,e2,e3;
    @FXML
    Polygon a1_shape,a2_shape,a3_shape,b1_shape,b2_shape,b3_shape,
    c1_shape,c2_shape,c3_shape,d1_shape,d2_shape,d3_shape,e1_shape,e2_shape,e3_shape;
    @FXML
    Text a1_cost,a2_cost,a3_cost,b1_cost,b2_cost,b3_cost,c1_cost,c2_cost,c3_cost,
            d1_cost,d2_cost,d3_cost,e1_cost,e2_cost,e3_cost;

    private boolean debug;
    private final int n_players;
    private final Communication outsideController;
    private final ArrayList<Button> terrList;
    private final ArrayList<Polygon> polygonList;
    private final ArrayList<Text> costList;
    private String callFrom;

    public MapViewController(Communication outsideController, int n_players, boolean debug, String callFrom){
        this.debug = debug;
        this.outsideController = outsideController;
        this.n_players = n_players;
        this.callFrom = callFrom;
        this.terrList = new ArrayList<Button>();
        this.polygonList = new ArrayList<Polygon>();
        this.costList = new ArrayList<Text>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeLists();

        // change color and invisible mode based on callFrom string. If it is called from attack/move/mainGameView/deploy..., show war-fog.
        for(int i=0;i<n_players*3;i++){
            Button btn = terrList.get(i);
            btn.setStyle("-fx-background-color: "+getColor(btn.getText()));  // set different color for different territory.


            // TODO let all view visible
//            // If in the deploy view, show every thing
//            if(this.callFrom.equals("deployView")) break;
//            //also can set territory invisiable for war-fog here
////            terrList.get(i).setVisible(false);
////            costList.get(i).setVisible(false);
////            polygonList.get(i).setVisible(false);
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
        return GameModel.getInstance().getTerrColor(terrName);
    }

    private void initializeLists(){
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
        costList.add(a1_cost);
        costList.add(a2_cost);
        costList.add(a3_cost);
        costList.add(b1_cost);
        costList.add(b2_cost);
        costList.add(b3_cost);
        costList.add(c1_cost);
        costList.add(c2_cost);
        costList.add(c3_cost);
        costList.add(d1_cost);
        costList.add(d2_cost);
        costList.add(d3_cost);
        costList.add(e1_cost);
        costList.add(e2_cost);
        costList.add(e3_cost);
        polygonList.add(a1_shape);
        polygonList.add(a2_shape);
        polygonList.add(a3_shape);
        polygonList.add(b1_shape);
        polygonList.add(b2_shape);
        polygonList.add(b3_shape);
        polygonList.add(c1_shape);
        polygonList.add(c2_shape);
        polygonList.add(c3_shape);
        polygonList.add(d1_shape);
        polygonList.add(d2_shape);
        polygonList.add(d3_shape);
        polygonList.add(e1_shape);
        polygonList.add(e2_shape);
        polygonList.add(e3_shape);

    }


}
