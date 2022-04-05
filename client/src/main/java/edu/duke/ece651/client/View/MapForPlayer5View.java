package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MapForPlayer5View implements View{
    @Override
    public void show(Stage window, Model model) throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/mapForPlayer5.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
    }
}
