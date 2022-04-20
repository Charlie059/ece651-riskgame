package edu.duke.ece651.client.View;


import edu.duke.ece651.client.Controller.MoveSpyDialogController;
import edu.duke.ece651.client.Model.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MoveSpyDialogView implements View {

    @Override
    public void show(Stage window, Model model, boolean debug) throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/moveSpyDialogView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(MoveSpyDialogController.class, new MoveSpyDialogController(window,debug));
        loader.setControllerFactory(controllers::get);
        Pane pane = loader.load();

        // create scene and load css
        Scene scene = new Scene(pane);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());

        window.setScene(scene);
        window.setTitle("Spy movement");
        window.showAndWait();
    }
}