package edu.duke.ece651.server;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.ClientJSONParser;
import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.PlayerCounter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GameController {
    Map map;
    Server server;

    public GameController(){
        try {
            // init Server
            this.server = new Server(1651);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Init the Player Number and assign the player id
     * @return false if IOException
     */
    public boolean init(){
        // Init a player counter
        PlayerCounter p = null;

        try {
            // Accept HostServer, ask num_player
            server.acceptClient();

            //Send special char to HostClient
            server.sendMsg(1, String.valueOf(p.getInstance().getCurrent_id()));

            //Get num_player
            Integer num_player = Integer.parseInt(server.recvMsg(1));

            // Connect all required number of player client
            for (int i = 0; i < num_player - 1; i++) {
                server.acceptClient();
                // Server send player id and total_num_player
                server.sendMsg(i + 2,p.getInstance().getCurrent_id() + " " + num_player); // assign from player id 2 to ...
            }

            // Init the map
            this.map = new Map(num_player);
        }
        catch (IOException ioException){
            ioException.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Play the game
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void play() throws ExecutionException, InterruptedException {
        // Entering the game loop
        while (true) {
            server.clearFutureList();

            // Thread send msg and receive parse
            for (int k = 0; k < server.getClientSocketList().size(); k++) {
                try {
                    ServerCallable task = new ServerCallable(server.getClientSocketList().get(k));
                    Future<?> future = server.getService().submit(task);
                    server.getFutureList().add(future);
                } finally {

                }
            }

            // Thread all end
            for (int i = 0; i < server.getFutureList().size(); i++) {
                // recv msg from each thread
                String receivedMessage = (String) server.getFutureList().get(i).get();

                // Parse Client JSON
                ClientJSONParser clientJSONParser = new ClientJSONParser(receivedMessage, map);
                clientJSONParser.doParse();
                ArrayList<Action> actionArr = clientJSONParser.getActionArrayList();
//        clientJSONParser.getPlayerID();
                System.out.println(receivedMessage);
            }
        }
    }


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        GameController gc = new GameController();
        gc.init();

        try {
            gc.play();
        }
        catch (ExecutionException executionException){
            executionException.printStackTrace();
        }
        catch (InterruptedException interruptedException){
            interruptedException.printStackTrace();
        }


    }


}
