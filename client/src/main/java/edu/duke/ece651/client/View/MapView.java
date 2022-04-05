package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Controller.MapViewController;
import edu.duke.ece651.client.Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MapView {
    public void show(Stage window, Model model, int n_players) throws IOException {
        String fxmlPath = "";
        if(n_players == 2){
            fxmlPath = "/xml/mapForPlayer2View.fxml";
        }

        // load start view fxml
        URL xmlResource = getClass().getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use loader setControllerFactory to specify how to create controllers.
        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(MapViewController.class, new MapViewController(window));
        loader.setControllerFactory(controllers::get);
        SplitPane sp = loader.load();

        // create scene and load css
        Scene scene = new Scene(sp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());

        window.setScene(scene);
        window.showAndWait();

    }
}
