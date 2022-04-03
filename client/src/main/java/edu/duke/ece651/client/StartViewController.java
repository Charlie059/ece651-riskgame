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
import java.util.HashMap;


public class StartViewController {
    @FXML
    TextField userName;
    @FXML
    TextField passWord;   // initialized by FMLLoader
    @FXML
    Text error_msg;

    private final LoginAndSignUpModel lsM;
    private final Stage window;

    public StartViewController(LoginAndSignUpModel lsM, Stage window) {
        this.lsM = lsM;
        this.window = window;
    }

    @FXML
    public void clickOnLogin() throws IOException {
        boolean res = lsM.validateLogin(userName.getText(),passWord.getText());
        if(res){
            showMenuView();
        }else{
            error_msg.setText("Error in UserName or Password, Please enter again.");
        }
    }

    @FXML
    public void clickOnSignUp(){
        // may be don't need to implement in evolution2
        System.out.println("sign up");
    }

    private void showMenuView() throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/menuView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use loader’s setControllerFactory to specify how to create controllers.
        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(MenuViewController.class, new MenuViewController(window));
        loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        SceneCollector.menuView = scene;

        this.window.setScene(scene);
        this.window.show();
    }

}
