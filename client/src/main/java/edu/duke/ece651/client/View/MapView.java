package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Controller.Communication;
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
    private Model model;
    private boolean debug;

    public MapView(Model model, boolean debug){
        this.debug = debug;
        this.model = model;
    }

    public Pane loadMap(int n_players, Communication outsideController) throws IOException {
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
        controllers.put(MapViewController.class, new MapViewController(outsideController, n_players, debug));
        loader.setControllerFactory(controllers::get);

        return loader.load();
    }
}
