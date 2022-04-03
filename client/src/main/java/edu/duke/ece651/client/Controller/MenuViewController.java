package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.SceneCollector;
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

    public MenuViewController(Stage window){
        this.window = window;
    }

    @FXML
    public void clickOnNewGame() throws IOException {
        showNewGameView();
    }

    @FXML
    public void clickOnContinue() throws IOException {
        showContinueView();
    }

    @FXML
    public void clickOnExit(){
        /* Maybe it needs some resource release */
        window.close();
    }

    @FXML
    public void clickOnJoin() throws IOException {
        showJoinGameView();
    }

    private void showNewGameView() throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/newGameView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use hashMap to collect controllers.
        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(newGameViewController.class, new newGameViewController(window));
        loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        SceneCollector.newGameView = scene;

        this.window.setScene(scene);
        this.window.show();
    }

    private void showContinueView() throws IOException{
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/continueGameView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use hashMap to collect controllers.
        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(continueGameViewController.class, new continueGameViewController(window));
        loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        SceneCollector.continueGameView = scene;

        this.window.setScene(scene);
        this.window.show();
    }

    private void showJoinGameView() throws IOException{
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/JoinGameView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use hashMap to collect controllers.
        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(joinGameViewController.class, new joinGameViewController(window));
        loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        SceneCollector.joinGameView = scene;

        this.window.setScene(scene);
        this.window.show();
    }
}


