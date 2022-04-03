package edu.duke.ece651.server.IO;

import edu.duke.ece651.shared.IO.ObjectStream;

import java.io.IOException;
import java.net.Socket;

public class MockClient {

    private final String host;
    private final int portNum;
    private final Socket socket;


    public MockClient(int portNum, String host) throws IOException {
        this.portNum = portNum;
        this.host = host;
        this.socket = new Socket(this.host, this.portNum);
    }


    /**
     * Send object to server
     * @return true if success
     */
    public boolean sendObject(Object object) throws IOException {
        ObjectStream objectStream = new ObjectStream(this.socket);
        return objectStream.sendObject(object);
    }


    /**
     * Recv object from server
     * @return true if success
     */
    public Object recvObject() throws IOException, ClassNotFoundException {
        ObjectStream objectStream = new ObjectStream(this.socket);
        return objectStream.recvObject();
    }


    // Choose an action and return
    public Action chooseAction(String input){
        switch (input){
            // Signup Action
            case "1":{
                AccountID userInputAccountID = new AccountID("pod128g");
                String userInputPassword = "abc123";
                return new SignUpAction(userInputAccountID, userInputPassword);
            }
            // Login Action
            case "2":
            {
                AccountID userInputAccountID = new AccountID("pod128g");
                String userInputPassword = "abc123";
                return new LoginAction(userInputAccountID,userInputPassword);
            }

            // Logout Action
            case "3":
            {
                return new LogoutAction();
            }

            // New Game Action
            case "4":{
                return new NewGameAction(2);
            }

            // Join Game Action
            // Server will return a list of game
            case "5":{
                return new JoinAction();
            }

            // Choose Game Action
            case "6":{
                // Join GameID 1
                GameID gameID = new GameID(1);
                return new ChooseJoinGameAction(gameID);
            }


        }

        return null;
    }

    public static void main(String[] args) {
        try {
            MockClient mockClient = new MockClient(1651, "127.0.0.1");

            // Set the action
            Scanner scanner = new Scanner(System.in);
            String input =  scanner.nextLine();
            Action action =  mockClient.chooseAction(input);

            // Send action to server
            mockClient.sendObject(action);

            // Recv response to server
            try {
                Response response = (Response) mockClient.recvObject();
                System.out.println(response.getClass().getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
