package edu.duke.ece651.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {

  public static void main(String[] args) throws IOException,ExecutionException, InterruptedException {
    if (args.length != 1) {
      throw new IllegalArgumentException("Syntex: ./Server <port>");
    }
    System.out.println("On port" + args[0]);
    ServerSocket serversocket = new ServerSocket(Integer.parseInt(args[0]));
    ExecutorService service = Executors.newFixedThreadPool(2);
    ArrayList<Future<?>> futureList = new ArrayList<>();
    while (true) {
      try {
        Socket client = serversocket.accept();
        ServerRunnable task = new ServerRunnable(client);
        Future<?> future = service.submit(task);
        futureList.add(future);
      } finally {
        for (int k = 0; k < futureList.size(); k++) {
          Future<?> future = futureList.get(k);
          System.out.println(future.get());
        }
      }
    }
  }
}
