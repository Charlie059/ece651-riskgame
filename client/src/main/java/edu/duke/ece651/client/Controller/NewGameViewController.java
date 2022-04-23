package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Checker.NewGameChecker;
import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.View.DeployView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class NewGameViewController {
    @FXML
    TextField n_players;
    @FXML
    TextField gameID;
    @FXML
    Text allert_T;

    private final Stage window;
    private boolean debug;


    public NewGameViewController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
    }

    /**
     * Player clicks the new game button
     */
    @FXML
    public void clickOnStart() {
        // Check input format
        if(!new NewGameChecker().doCheck(new String[]{n_players.getText()})){
            allert_T.setText("Invalid number of Players!");
            return;
        }

        // Pass userInput to GameModel (if model return false then return); else model will get data from server
        if(!GameModel.getInstance().startNewGame(n_players.getText(), debug)) return;

        // If server accept request then show the deployment view
        try {
            new DeployView().show(this.window, null, debug);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnBack(){
        window.setScene(SceneCollector.menuView);
        window.show();
    }



}
