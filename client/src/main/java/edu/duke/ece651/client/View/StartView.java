package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Controller.StartViewController;
import edu.duke.ece651.client.Model.LoginModel;
import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.client.Model.SignupModel;
import edu.duke.ece651.client.SceneCollector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class StartView implements View{
    @Override
    public void show(Stage window, Model model, boolean debug) throws IOException {
        URL xmlResource = getClass().getResource("/xml/startView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use loader's setControllerFactory to specify how to create controllers.
        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(StartViewController.class, new StartViewController(window, debug));
        loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        SceneCollector.startView = scene;

        window.setScene(scene);
        window.setTitle("Login");
        window.show();
    }
}
