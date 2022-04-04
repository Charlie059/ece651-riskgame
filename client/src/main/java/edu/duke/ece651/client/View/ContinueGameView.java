package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Controller.ContinueGameViewController;
import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.client.SceneCollector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ContinueGameView implements View{
    @Override
    public void show(Stage window, Model model) throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/continueGameView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use loader’s setControllerFactory to specify how to create controllers.
        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(ContinueGameViewController.class, new ContinueGameViewController(window));
        loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        SceneCollector.continueGameView = scene;

        window.setScene(scene);
        window.show();
    }
}
