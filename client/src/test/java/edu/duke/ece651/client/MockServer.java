package edu.duke.ece651.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MockServer {

  private final int portNum;

  private final ServerSocket serversocket;
  private ExecutorService service;

  private ArrayList<Socket> clientSocketList;

  public MockServer(int portNum) throws IOException {
    this.portNum = portNum;
    this.serversocket = new ServerSocket(this.portNum);
    this.service = Executors.newFixedThreadPool(16); // The max number of threads by service
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
   * 
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
   * 
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
   * Get getServersocket
   * 
   * @return ServerSocket
   */
  public ServerSocket getServersocket() {
    return serversocket;
  }

  public ExecutorService getService() {
    return service;
  }

  public ArrayList<Socket> getClientSocketList() {
    return clientSocketList;
  }

}
