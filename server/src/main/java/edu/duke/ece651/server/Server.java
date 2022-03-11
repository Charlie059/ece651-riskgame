package edu.duke.ece651.server;

import edu.duke.ece651.shared.PlayerCounter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {

  private final int portNum;
  private final ServerSocket serversocket;
  private ExecutorService service;
  private ArrayList<Future<?>> futureList;
  private ArrayList<Socket> clientSocketList;

  public Server(int portNum) throws IOException {
    this.portNum = portNum;
    this.serversocket = new ServerSocket(this.portNum);
    this.service = Executors.newFixedThreadPool(16); // The max number of threads by service
    this.futureList = new ArrayList<>();
    this.clientSocketList = new ArrayList<Socket>();
  }

  /**
   * Accept the connection from the client
   */
  public void acceptClient() throws IOException {
    this.clientSocketList.add(this.serversocket.accept());
  }

  /**
   * Send message to the client (Do NOT ADD \n)
   * @param playerID
   * @param msg
   * @throws IOException
   */
  public void sendMsg(int playerID, String msg) throws IOException {
    OutputStream out = clientSocketList.get(playerID - 1).getOutputStream();
    var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    writer.write(msg + "\n");
    writer.flush();
  }

  /**
   * Recv msg from client
   * @param playerID
   * @return the msg
   * @throws IOException
   */
  public String recvMsg(int playerID) throws IOException {
    InputStream in = clientSocketList.get(playerID - 1).getInputStream();
    var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    return reader.readLine();
  }

  /**
   * Clear the future list
   */
  public void clearFutureList(){
    this.futureList.clear();
  }


  public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
    // Init Server
    Server server = new Server(1651);
    // Init a player counter
    PlayerCounter p = null;

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

    // Entering the game loop
    while (true) {
      server.clearFutureList();

      // Thread send msg and receive parse
      for (int k = 0; k < server.clientSocketList.size(); k++) {
        try {
          ServerCallable task = new ServerCallable(server.clientSocketList.get(k));
          Future<?> future = server.service.submit(task);
          server.futureList.add(future);
        } finally {

        }
      }

      // Thread all end
      for (int i = 0; i < server.futureList.size(); i++) {
        // recv msg from each thread
        String receivedMessage = (String) server.futureList.get(i).get();
        System.out.println(receivedMessage);
      }
    }
  }

}
