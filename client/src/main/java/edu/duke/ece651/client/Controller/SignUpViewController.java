package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.client.Model.SignupModel;
import edu.duke.ece651.client.SceneCollector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class SignUpViewController {
    @FXML
    TextField userName,passWord, Email, verificationCode;
    @FXML
    Text msg;

    private final Stage window;
    private final SignupModel signupModel;
    private boolean debug;

    public SignUpViewController(Stage window, boolean debug) {
        this.signupModel = new SignupModel();
        this.window = window;
        this.debug = debug;
    }

    /**
     * Show Start view when clickOnBack
     */
    @FXML
    public void clickOnBack(){
        this.window.setScene(SceneCollector.startView);
        this.window.show();
    }

    /**
     * Validate user signup request from server
     */
    @FXML
    public void clickOnSignUp(){
        boolean res = signupModel.signUp(userName.getText(),passWord.getText(),debug);
        if(res){
            msg.setText("Sign up a new account successfully!");
        }else{
            msg.setText("Sign up a new account failure!");
        }
    }
}
