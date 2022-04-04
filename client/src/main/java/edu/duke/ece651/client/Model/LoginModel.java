package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.Client;
import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.shared.IO.ClientActions.LoginAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPMoveSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Visitor.RSPVisitor;
import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;

import java.io.IOException;

public class LoginModel extends Model{
    public boolean validateLogin(String userName, String passWord){
//        // TODO
//
//        LoginAction loginAction = new LoginAction(new AccountID(userName),passWord);
//        ClientSocket.getInstance().sendObject(loginAction);
//        Response response = (Response) ClientSocket.getInstance().recvObject();
//        response.accept(new RSPVisitor());
//        return result;
        return true;
    }
}
