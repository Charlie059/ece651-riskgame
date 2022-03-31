package edu.duke.ece651.server;

import edu.duke.ece651.shared.Wrapper.CurrGameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.ClientActions.Action;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Visitor.ActionCheckDoFeedbackVisitor;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class CommunicatorRunnable implements Runnable {
    private AccountID accountID;
    private CurrGameID currGameID;
    private Socket clientSocket;
    private ObjectStream objectStream;
    private HashMap<Integer, Game> gameHashMap;
    private HashMap<String, Account> accountHashMap;

    public CommunicatorRunnable(Socket clientSocket, HashMap<Integer, Game> gameHashMap, HashMap<String, Account> accountHashMap) throws IOException {
        this.clientSocket = clientSocket;
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        //TODO Extract ObjectStream Send Recv Method
        //TODO Everytime when use objectStream, construct
        this.objectStream = new ObjectStream(this.clientSocket);
    }

    /**
     * Receive Client Action Object
     * @return Action Object or Null(to indicate error)
     */
    private Action recvAction() {
        try {
            return (Action) objectStream.recvObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            //Receive Action
            Action action = this.recvAction();
            //Check Do Feedback action
            action.accept(new ActionCheckDoFeedbackVisitor(this.accountID, this.currGameID,this.clientSocket,this.accountHashMap,this.gameHashMap));

        }
    }
}
