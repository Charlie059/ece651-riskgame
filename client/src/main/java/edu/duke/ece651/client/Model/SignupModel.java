package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.Checker.Checker;
import edu.duke.ece651.client.Checker.PasswordChecker;
import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.shared.IO.ClientActions.LoginAction;
import edu.duke.ece651.shared.IO.ClientActions.SignUpAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPLoginSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSignupSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.io.IOException;

public class SignupModel extends Model{


    public boolean signUp(String userName, String passWord, Boolean debugMode){
        if(debugMode){
            return true;
        }

        // Use a local checker to check if password satisfy the following criteria:
        // Password must be a combination of 8 or more digits containing uppercase letters,
        // lowercase letters, numbers, special symbols (characters other than letters, numbers,
        // underscores)
        PasswordChecker passwordChecker = new PasswordChecker();
        String[] password = new String[1];
        password[0] = passWord;

        // If password is not secure enough
        if(!passwordChecker.doCheck(password)) return false;

        // Create a new LoginAction
        SignUpAction signUpAction = new SignUpAction(new AccountID(userName),passWord);
        // Send to Server to validate
        try {
            ClientSocket.getInstance().sendObject(signUpAction);
            Response response = (Response) ClientSocket.getInstance().recvObject();
            return response.getClass() == RSPSignupSuccess.class;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return false;
        }
    }
}
