/**
 * Author: Yuxuan Yang AND Xuhui Gong
 */
package edu.duke.ece651.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MockClient {

  private final String host;
  private final int portNum;
  private final InputStream in;
  private final OutputStream out;
  private final OutputStreamWriter writer;
  private final BufferedReader reader;
  private final Socket socket;

  /**
   * Constructor of Client Class by given portNum and host
   * 
   * @param portNum
   * @param host
   * @throws IOException
   */
  public MockClient(int portNum, String host) throws IOException {
    this.portNum = portNum;
    this.host = host;
    this.socket = new Socket(this.host, this.portNum);
    this.in = socket.getInputStream();
    this.out = socket.getOutputStream();
    this.writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    this.reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
  }

  /**
   * Send a JSON to the server, and it adds a \n automatically
   * 
   * @param msg to be send
   * @throws IOException
   */
  public void sendMsg(String msg) throws IOException {
    this.writer.write(msg + "\n");
    this.writer.flush();
  }

  /**
   * Receive messgae from the server
   * 
   * @return String
   * @throws IOException
   */
  public String recvMsg() throws IOException {
    return this.reader.readLine();
  }

}
