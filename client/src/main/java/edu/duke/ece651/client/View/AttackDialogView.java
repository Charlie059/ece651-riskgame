package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Controller.AttackDialogController;
import edu.duke.ece651.client.Model.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class AttackDialogView implements View {

    @Override
    public void show(Stage window, Model model, boolean debug) throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/attackDialogView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(AttackDialogController.class, new AttackDialogController(window,debug));
        loader.setControllerFactory(controllers::get);
        Pane pane = loader.load();

        // create scene and load css
        Scene scene = new Scene(pane);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());

        window.setScene(scene);
        window.setTitle("Attack movement");
        window.showAndWait();
    }
}
