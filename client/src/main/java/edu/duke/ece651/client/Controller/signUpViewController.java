package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.SignupModel;
import edu.duke.ece651.client.SceneCollector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class signUpViewController {
    @FXML
    TextField userName;
    @FXML
    TextField passWord;
    @FXML
    Text msg;

    private final Stage window;
    private final SignupModel signupModel;

    public signUpViewController(SignupModel signupModel, Stage window) {
        this.signupModel = signupModel;
        this.window = window;
    }

    @FXML
    public void clickOnBack(){
        this.window.setScene(SceneCollector.startView);
        this.window.show();

        //If visit Object1{
        // this.window1.show();
        // }else object 2 {
        // this.window2.show
        // }
    }

    @FXML
    public void clickOnSignUp(){

        boolean res = signupModel.signUp(userName.getText(),passWord.getText());
        if(res){
            msg.setText("Sign up a new account successfully!");
        }else{
            msg.setText("Repeated UserName.");
        }
    }

}
