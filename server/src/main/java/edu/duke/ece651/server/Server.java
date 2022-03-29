package edu.duke.ece651.server;

import org.json.*;
import java.io.BufferedReader;
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
  private ArrayList<Socket> clientSocketList;


  public Server(int portNum) throws IOException {
    this.portNum = portNum;
    this.serversocket = new ServerSocket(this.portNum);
    this.service = Executors.newFixedThreadPool(16); // The max number of threads by service
    this.clientSocketList = new ArrayList<Socket>();
  }




}
