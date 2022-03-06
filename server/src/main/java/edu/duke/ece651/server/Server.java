package edu.duke.ece651.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {

  public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
    if (args.length != 1) {
      throw new IllegalArgumentException("Syntex: ./Server <port>");
    }

    System.out.println("On port" + args[0]);
    ServerSocket serversocket = new ServerSocket(Integer.parseInt(args[0]));

    // The max number of throwed threads by service
    ExecutorService service = Executors.newFixedThreadPool(2);
    ArrayList<Future<?>> futureList = new ArrayList<>();

    // add num_player
    ArrayList<Socket> clientSocketList = new ArrayList<Socket>();
    Socket hostsocket = serversocket.accept();
    clientSocketList.add(hostsocket);
    InputStream in = hostsocket.getInputStream();
    var hostreader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    String s = hostreader.readLine();
    Integer num_player = Integer.parseInt(s);

    for (int i = 0; i < num_player - 1; i++) {
      Socket clientsocket = serversocket.accept();
      clientSocketList.add(clientsocket);
    }

    for (int k = 0; k < num_player; k++) {
      try {
        ServerCallable task = new ServerCallable(clientSocketList.get(k));
        Future<?> future = service.submit(task);
        futureList.add(future);
      } finally {

      }
    }
  }

}
