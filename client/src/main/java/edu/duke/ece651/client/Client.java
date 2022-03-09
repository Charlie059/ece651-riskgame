package edu.duke.ece651.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


public class Client {

  private Player player;
  private final String host;
  private final int portNum;
  private final InputStream in;
  private final OutputStream out;
  private final OutputStreamWriter writer;
  private final BufferedReader reader;
  private final Socket socket;


  /**
   * Constructor of Client Class by given portNum and host
   * @param portNum
   * @param host
   * @throws IOException
   */
  public Client(int portNum, String host) throws IOException {
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
   * @param msg to be send
   * @throws IOException
   */
  public void sendMsg(String msg) throws IOException {
      this.writer.write(msg + "\n");
      this.writer.flush();
  }

  /**
   * Receive messgae from the server
   * @return String
   * @throws IOException
   */
  public String recvMsg() throws IOException {
    return this.reader.readLine();
  }


  public static void main(String[] args) throws IOException, InterruptedException {
//    Socket socket = new Socket("127.0.0.1", 1651);
//    InputStream in = socket.getInputStream();
//    OutputStream out = socket.getOutputStream();
//    var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
//    var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    Client client = null;
    try {
      client = new Client(1651, "127.0.0.1");
    }
    catch (IOException ioException){
      System.out.println("Cannot connect to the server");
      int EXIT_FAILURE = 1;
      System.exit(EXIT_FAILURE);
    }

    String s = client.reader.readLine();
    System.out.println(s);

    if(s.equals("H")){
      System.out.println("You are Host!, please send number of players: ");
      int total_player = 2;
      System.out.println("You entered " + String.valueOf(total_player));
//      writer.write(String.valueOf(total_player)+"\n");
//      writer.flush();
      client.sendMsg(String.valueOf(total_player));
    }else{
      System.out.println("You are Player");
    }

    while(true){
      //receive()
//      String received_message = reader.readLine();
      String received_message = client.recvMsg();
      //parse()
      System.out.println(received_message);

      //confirm()
//      writer.write("I Know!" + "\n");
//      writer.flush();
      client.sendMsg(String.valueOf("I Know!"));
      TimeUnit.SECONDS.sleep(10);
    }
  }
}
