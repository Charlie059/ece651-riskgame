package edu.duke.ece651.server;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameController {
    private final Integer PORT_NUM = 1651;
    private Server server;
    //Synchronized Game Database synchronized between MultiThread
    private volatile GameHashMap gameHashMap;

    //Synchronized Account Database synchronized between MultiThread
   private volatile AccountHashMap accountHashMap;

    private ExecutorService service;


    public GameController() throws IOException {
        this.server = new Server(PORT_NUM);
        this.gameHashMap = new GameHashMap();
        this.accountHashMap = new AccountHashMap();
        this.service = Executors.newFixedThreadPool(16); // The max number of threads by service
    }

    /**
     * Accept New Client Connection
     * and Throw a New Communicator thread
     * @throws IOException
     */
    public void acceptNewClient() throws IOException {
        Socket clientSocket = this.server.getServersocket().accept();
        CommunicatorRunnable communicatorRunnable = new CommunicatorRunnable(clientSocket, this.gameHashMap, this.accountHashMap);
        Thread t = new Thread(communicatorRunnable);
        t.start();
    }


    public static void main(String[] args) {
        GameController gameController = null;
        try {
            gameController = new GameController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Main thread accept new client and throw communicator threads
        while (true) {
            try {
                gameController.acceptNewClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
