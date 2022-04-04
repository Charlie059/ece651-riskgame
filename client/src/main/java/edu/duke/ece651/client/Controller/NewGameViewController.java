package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Checker.NewGameChecker;
import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.View.DeployView;
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
import java.util.HashMap;

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
        new DeployView().show(window,null);
    }

    @FXML
    public void clickOnBack(){
        window.setScene(SceneCollector.menuView);
        window.show();
    }



}
