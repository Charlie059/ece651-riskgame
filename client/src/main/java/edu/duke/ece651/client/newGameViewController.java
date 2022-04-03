package edu.duke.ece651.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class newGameViewController {
    @FXML
    TextField n_players;
    @FXML
    TextField gameID;
    @FXML
    Text allert_T;

    private final Stage window;

    public newGameViewController(Stage window){
        this.window = window;
    }

    @FXML
    public void clickOnStart() throws IOException {
        // check input format..
        boolean res = checkInput(n_players.getText(),gameID.getText());
        if(!res){
            allert_T.setText("Invalid number of Players or gameID!");
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

    private boolean checkInput(String n, String ID){
        // check n_players
        for(int i=0;i<n.length();i++){
            if(!Character.isDigit(n.charAt(i))){
                return false;
            }
        }
        int N = Integer.parseInt(n);
        if(N<2 || N>5){
            return false;
        }

        // check game ID
        for(int i=0;i<ID.length();i++){
            if(!Character.isDigit(ID.charAt(i))){
                return false;
            }
        }

        return true;
    }

    private void showDeployView() throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/deployView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use loader’s setControllerFactory to specify how to create controllers.
        //HashMap<Class<?>,Object> controllers = new HashMap<>();
        //controllers.put(deployViewController.class, new deployViewController(window));
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
