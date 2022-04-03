package edu.duke.ece651.client.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class newGameViewController {
    @FXML
    TextField n_players;
    @FXML
    TextField gameID;

    private final Stage window;

    public newGameViewController(Stage window){
        this.window = window;
    }

    @FXML
    public void clickOnStart(){
        System.out.println("startNewGame");

//        Response r = Client.recv();
//        r.accept(new ResponseVisitor)
    }

    @FXML
    public void clickOnBack(){
        window.setScene(SceneCollector.menuView);
        window.show();
    }
}
