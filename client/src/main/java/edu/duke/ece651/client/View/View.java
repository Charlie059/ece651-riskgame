package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Model.Model;
import javafx.stage.Stage;

import java.io.IOException;

public interface View {
    void show(Stage window, Model model, boolean debug) throws IOException;
}
