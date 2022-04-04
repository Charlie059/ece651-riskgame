package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.shared.IO.ClientActions.LoginAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPLoginSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class LoginModel extends Model{
    public boolean validateLogin(String userName, String passWord){
        // Create a new LoginAction
        LoginAction loginAction = new LoginAction(new AccountID(userName),passWord);
        // Send to Server to validate
        try {
            ClientSocket clientSocket =  ClientSocket.getInstance();
            clientSocket.sendObject(loginAction);
            Response response = (Response) ClientSocket.getInstance().recvObject();
            return response.getClass() == RSPLoginSuccess.class;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return false;
        }
    }
}
