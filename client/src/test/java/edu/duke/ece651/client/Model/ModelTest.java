package edu.duke.ece651.client.Model;


import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.client.IO.MockServer;
import edu.duke.ece651.shared.IO.ClientActions.LoginAction;
import edu.duke.ece651.shared.IO.ClientActions.SignUpAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPLoginSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.RSPSignupSuccess;
import edu.duke.ece651.shared.Wrapper.AccountID;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void validateLogin() throws IOException {
        MockServer mockServer = new MockServer(1651);

        // Create a new Thread
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mockServer.acceptClient();

                    sendRSPLoginSuccess(mockServer);
                    sendOtherObject(mockServer);
                    sendRSPSignUpSuccess(mockServer);
                    sendOtherObject(mockServer);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        // Client send LoginAction and recv RSPLoginSuccess
        LoginModel loginModel = new LoginModel();
        assertEquals(true, loginModel.validateLogin("123","abc"));

        // Client send LoginAction and recv other Response
        loginModel.validateLogin("123","abc");

        // Client send Signup Action and recv RSPSignupSuccess
        SignupModel signupModel = new SignupModel();
        assertEquals(true, signupModel.signUp("123","abc"));


        // Client send Signup Action and recv other Response
        assertEquals(false, signupModel.signUp("123","abc"));
    }



    private void sendRSPSignUpSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send otherObject
        assertEquals(SignUpAction.class, mockServer.recvObject().getClass());
        mockServer.sendObject(new RSPSignupSuccess());
    }

    private void sendOtherObject(MockServer mockServer) throws IOException, ClassNotFoundException {
        mockServer.recvObject().getClass();
        // Test send otherObject
        mockServer.sendObject(new LoginAction(new AccountID("a"),"12"));
    }

    private void sendRSPLoginSuccess(MockServer mockServer) throws IOException, ClassNotFoundException {
        // Test send RSPLoginSuccess
        assertEquals(LoginAction.class, mockServer.recvObject().getClass());
        mockServer.sendObject(new RSPLoginSuccess());
    }


}