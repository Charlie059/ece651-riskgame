package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Controller.MapViewController;
import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.shared.Wrapper.GameID;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MapView {
    private final String[] fxIDList = new String[]{"a1","a2","a3","b1","b2","b3","c1","c2","c3","d1","d2","d3","e1","e2","e3"};

    private String getColor(Model m, String terrName){

        if(GameModel.getInstance().getMyTerrList().contains(terrName)){
            return "#FF0000";
        }
        return "#83ae52";
    }

    public void show(Stage window, Model model, int n_players, boolean debug) throws IOException {
        String fxmlPath = "";
        if(n_players == 2){
            fxmlPath = "/xml/mapForPlayer2View.fxml";
        }
        else if(n_players == 3){
            fxmlPath = "/xml/mapForPlayer3View.fxml";
        }
        else if(n_players == 4){
            fxmlPath = "/xml/mapForPlayer4View.fxml";
        }
        else if(n_players == 5){
            fxmlPath = "/xml/mapForPlayer5View.fxml";
        }

        // load start view fxml
        URL xmlResource = getClass().getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader(xmlResource);

        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(MapViewController.class, new MapViewController(window,debug));
        loader.setControllerFactory(controllers::get);
        Pane p = loader.load();

        // create scene and load css
        Scene scene = new Scene(p);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        window.setScene(scene);
        window.show();

        //change background color of the button based on its owner.  get button object based on its fx:id
        for(int n = 1;n <= n_players; n++){
            for(int i = 0; i< 3 * n ; i++){
                String fxId_Btn = fxIDList[i];
                Button btn = (Button) scene.lookup("#"+fxId_Btn);
                btn.setStyle("-fx-background-color: "+ getColor(null,btn.getText()));
            }
        }

    }
}
