package edu.duke.ece651.server;

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
  // ServerJSON
  public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
    // if (args.length != 1) {
    //   throw new IllegalArgumentException("Syntex: ./Server <port>");
    // }
    System.out.println("On port" + "4444");
    ServerSocket serversocket = new ServerSocket(4444);

    // The max number of throwed threads by service
    ExecutorService service = Executors.newFixedThreadPool(16);
    ArrayList<Future<?>> futureList = new ArrayList<>();

    // Connect HostServer, ask num_player
    ArrayList<Socket> clientSocketList = new ArrayList<Socket>();
    Socket hostsocket = serversocket.accept();
    clientSocketList.add(hostsocket);
    InputStream in = hostsocket.getInputStream();
    OutputStream out = hostsocket.getOutputStream();

//    var hostwriter = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
    var hostwriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    var hostreader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));


    //Send special char to HostClient
    hostwriter.write("H" + "\n");
    hostwriter.flush();


    //Get num_player
    String s = hostreader.readLine();
    Integer num_player = Integer.parseInt(s);

    //TODO: Change to Client
    if (num_player == 1) {
      System.out.println("Syntex: player number should be bigger than 1!");
      return;
    }

    // connect all required number of player client
    for (int i = 0; i < num_player - 1; i++) {
      Socket clientsocket = serversocket.accept();
      OutputStream client_out = clientsocket.getOutputStream();
      var ClientWriter = new BufferedWriter(new OutputStreamWriter(client_out,StandardCharsets.UTF_8));
      ClientWriter.write("C\n");
      ClientWriter.flush();
      clientSocketList.add(clientsocket);
    }

    // Entering the game loop

    while (true) {

      futureList.clear();
      // Thread send receive parse
      for (int k = 0; k < clientSocketList.size(); k++) {
        try {
          ServerCallable task = new ServerCallable(clientSocketList.get(k));
          Future<?> future = service.submit(task);
          futureList.add(future);
        } finally {

        }
      }

      // Thread all end
      // RunGame()
      for (int i = 0; i < futureList.size(); i++) {
        String receivedMessage = (String) futureList.get(i).get();
        System.out.println(receivedMessage);
      }
    }
  }

}
