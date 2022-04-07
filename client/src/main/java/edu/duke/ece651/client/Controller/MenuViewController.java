package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.View.ContinueGameView;
import edu.duke.ece651.client.View.JoinGameView;
import edu.duke.ece651.client.View.NewGameView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MenuViewController {
    private final Stage window;
    private boolean debug;

    public MenuViewController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
    }

    @FXML
    public void clickOnNewGame() throws IOException {
        new NewGameView().show(this.window, null, debug);
    }

    @FXML
    public void clickOnContinue() throws IOException {
        new ContinueGameView().show(this.window, null, debug);
    }

    @FXML
    public void clickOnExit(){
        /* Maybe it needs some resource release */
        window.close();
    }

    @FXML
    public void clickOnJoin() throws IOException {
        new JoinGameView().show(this.window, null, debug);
    }






}


