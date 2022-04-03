package edu.duke.ece651.client;

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
    private final LoginAndSignUpModel lsM;

    public signUpViewController(LoginAndSignUpModel lsM, Stage window) {
        this.lsM = lsM;
        this.window = window;
    }

    @FXML
    public void clickOnBack(){
        this.window.setScene(SceneCollector.startView);
        this.window.show();
    }

    @FXML
    public void clickOnSignUp(){
        boolean res = lsM.signUp(userName.getText(),passWord.getText());
        if(res){
            msg.setText("Sign up a new account successfully!");
        }else{
            msg.setText("Repeated UserName.");
        }
    }

}
