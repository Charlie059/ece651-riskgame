package edu.duke.ece651.server;

import edu.duke.ece651.shared.IO.ClientActions.Action;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.Visitor.ActionCheckFeedbackVistor;
import edu.duke.ece651.shared.Visitor.ActionDoVisitor;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class CommunicatorRunnable implements Runnable {
    private Integer playerID = null;
    private Integer currGameID = null;
    private Socket clientSocket;
    private ObjectStream objectStream;
    private HashMap<Integer, Game> gameHashMap;
    private HashMap<Integer, Player> playerHashMap;

    public CommunicatorRunnable(Socket clientSocket, HashMap<Integer, Game> gameHashMap, HashMap<Integer, Player> playerHashMap) throws IOException {
        this.clientSocket = clientSocket;
        this.gameHashMap = gameHashMap;
        this.playerHashMap = playerHashMap;
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

            Action action = this.recvAction();
            action.accept(new ActionCheckFeedbackVistor());
            action.accept(new ActionDoVisitor());
            //action.accept(new ActionFeedbackVisitor());


        }
    }
}
