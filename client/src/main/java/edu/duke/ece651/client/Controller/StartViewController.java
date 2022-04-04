package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.LoginModel;
import edu.duke.ece651.client.Model.SignupModel;
import edu.duke.ece651.client.SceneCollector;
import edu.duke.ece651.client.View.MenuView;
import edu.duke.ece651.client.View.SignupView;
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

    private final LoginModel loginModel;
    private final SignupModel signupModel;
    private final Stage window;

    public StartViewController(LoginModel loginModel, SignupModel signupModel, Stage window) {
        this.loginModel = loginModel;
        this.signupModel = signupModel;
        this.window = window;
    }

    @FXML
    public void clickOnLogin() throws IOException {
        boolean res = loginModel.validateLogin(userName.getText(),passWord.getText());
        if(res){
            new MenuView().show(this.window, null);
        }else{
            error_msg.setText("Error in UserName or Password, Please enter again.");
        }
    }

    @FXML
    public void clickOnSignUp() throws IOException {
        new SignupView().show(this.window, signupModel);
    }





}
