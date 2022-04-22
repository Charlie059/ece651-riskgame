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
        this.signupModel = SignupModel.getInstance();
        this.window = window;
        this.debug = debug;
    }

    /**
     * Show Start view when clickOnBack
     */
    @FXML
    public void clickOnBack(){
        this.window.setScene(SceneCollector.startView);
        window.setTitle("Login");
        this.window.show();
    }

    /**
     * Validate user signup request from server
     */
    @FXML
    public void clickOnSignUp(){
        String res = signupModel.signUp(userName.getText(),passWord.getText(), verificationCode.getText(),debug);
        if(res == null){
            msg.setText("Sign up a new account successfully!");
        }else{
            msg.setText(res);
        }
    }
}
