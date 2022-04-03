package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.ClientActions.Action;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.Account;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class CommunicatorRunnable implements Runnable {
    private AccountID accountID;
    private GameID gameID;
    private Socket clientSocket;
    private volatile GameHashMap gameHashMap;
    private volatile AccountHashMap accountHashMap;
    private Integer runtime = -1; //Diagnosis mode: 0 for inf loop else indicate num of loops

    /**
     * Create Communicator thread to communicate socket one to one
     *
     * @param clientSocket   Current communicate Socket
     * @param gameHashMap    Database contains all created Game
     * @param accountHashMap Datebase contains all created Account
     * @param runtime        Default -1 for normal mode, if runtime >= 0, Diagnosis Mode is on and is limited on while(runtime)
     * @throws IOException
     */
    public CommunicatorRunnable(Socket clientSocket, GameHashMap gameHashMap, AccountHashMap accountHashMap, Integer runtime) throws IOException {
        this.clientSocket = clientSocket;
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        //TODO Extract ObjectStream Send Recv Method
        //TODO Everytime when use objectStream, construct
        this.runtime = runtime;
    }

    public CommunicatorRunnable(Socket clientSocket, GameHashMap gameHashMap, AccountHashMap accountHashMap) throws IOException {
        this.clientSocket = clientSocket;
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        //TODO Extract ObjectStream Send Recv Method
        //TODO Everytime when use objectStream, construct
    }


    public CommunicatorRunnable(AccountID accountID, GameID gameID, Socket clientSocket, AccountHashMap accountHashMap, GameHashMap gameHashMap, Integer runtime) throws IOException {
        this.accountID = accountID;
        this.gameID = gameID;
        this.clientSocket = clientSocket;
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        this.runtime = runtime;
    }

    /**
     * Receive Client Action Object
     *
     * @return Action Object or Null(to indicate error)
     */
    private Action recvAction() {
        try {
            ObjectStream objectStream = new ObjectStream(this.clientSocket);
            return (Action) objectStream.recvObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AccountID getAccountID() {
        return accountID;
    }

    public GameID getGameID() {
        return gameID;
    }


    @Override
    public void run() {
        Integer runtimeCounter = this.runtime;
        while (true) {
            // if runtimeCounter == 0 break
            if (runtimeCounter == 0) break;
            else if (runtimeCounter > 0) runtimeCounter--;

            //Receive Action
            Action action = this.recvAction();
            //Check Do Feedback action
            action.accept(new ActionCheckDoFeedbackVisitor(this.accountID, this.gameID, this.clientSocket, this.accountHashMap, this.gameHashMap));
        }
    }
}
