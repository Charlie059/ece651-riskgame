package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Checker.NewGameChecker;
import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.gameInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class NewGameViewController {
    @FXML
    TextField n_players;
    @FXML
    TextField gameID;
    @FXML
    Text allert_T;

    private final Stage window;

    public NewGameViewController(Stage window){
        this.window = window;
    }


    @FXML
    public void clickOnStart() throws IOException {
        // Check input format
        boolean res = new NewGameChecker().doCheck(new String[]{n_players.getText()});
        if(!res){
            allert_T.setText("Invalid number of Players!");
            return;
        }

        // translate game info
        gameInfo newGame = new gameInfo(Integer.parseInt(gameID.getText()), Integer.parseInt(n_players.getText()),"this is a new game.");

        showDeployView();
        System.out.println("startNewGame");
    }

    @FXML
    public void clickOnBack(){
        window.setScene(SceneCollector.menuView);
        window.show();
    }


    private void showDeployView() throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/deployView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use loader's setControllerFactory to specify how to create controllers.
        //HashMap<Class<?>,Object> controllers = new HashMap<>();
        //controllers.put(DeployViewController.class, new DeployViewController(window));
        //loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 1280, 760);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        SceneCollector.continueGameView = scene;

        this.window.setScene(scene);
        this.window.show();
    }
}
