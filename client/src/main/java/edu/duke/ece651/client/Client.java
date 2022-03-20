/**
 * Author: Yuxuan Yang AND Xuhui Gong
 */
package edu.duke.ece651.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeUnit; // TODO DELETE

import edu.duke.ece651.shared.ClientJSON;
import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.MapTextView;
import edu.duke.ece651.shared.ServerJSONParser;

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
   * 
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

  /**
   * Client init player. If cilent is first to connect the server then we define
   * this client is host. Host should input the total number of player and then
   * server will be based on that value to let the client join in.
   * 
   * @throws IOException
   */
  public void initPlayer() throws IOException {
    String msg = this.recvMsg();
    if (msg.equals("1")) { // If msg indicate that player is the host
      System.out.println("You are Host!, please send number of players: ");

      // TODO user input checker class
      Scanner reader = new Scanner(System.in);

      ClientRuleChecker checkerHostNumPlayer = new CheckerHostNumPlayer();
      String userInput;
      String checkerOutput;
      do {
        System.out.println("Enter a number: ");
        userInput = reader.nextLine();
        checkerOutput = checkerHostNumPlayer.doCheck(userInput);
        System.out.println("You entered " + String.valueOf(userInput));
        System.out.println(checkerOutput);
      } while (!checkerOutput.isEmpty());

      int total_player = Integer.parseInt(userInput);

      this.sendMsg(String.valueOf(total_player));

      // Create a new player object for client
      this.player = new Player(Integer.parseInt(msg), total_player,
          new BufferedReader(new InputStreamReader(System.in)), System.out);
    } else { // If msg indicate that player is non-host
      String[] parsedMsg = msg.split("\\s+");

      // Create a new player object for client
      this.player = new Player(Integer.parseInt(parsedMsg[0]), Integer.parseInt(parsedMsg[1]),
          new BufferedReader(new InputStreamReader(System.in)), System.out);
    }

  }

  public static void main(String[] args) throws IOException, InterruptedException, Exception {
    // Init client TCP SOCKET
    Client client = null;
    try {
      client = new Client(1651, "207.246.90.49");
    } catch (IOException ioException) {
      System.out.println("Cannot connect to the server");
      int EXIT_FAILURE = 1;
      System.exit(EXIT_FAILURE);
    }

    // First handshake, Client recv msg from server and create player object
    client.initPlayer();
    // Map map = new Map(client.player.getnumOfPlayers());

    /**
     * TODO System.out is not a appropriate PrintStream
     */
     while (true) {
      // Client recv ServerJson
      // The first time received an empty ServerJSON, however, this ServerJSON was
      // omitted by player.playOneRound()
      String received_message = client.recvMsg();

      client.player.setRecvJSON(received_message);

      client.player.playOneRound();

      //If GameOver, then disconnect socket and return the program
      if(client.player.getIsGameOver()){
        client.socket.close();
        return;
      }
      ClientJSON converter = new ClientJSON(client.player.getId(), client.player.getActionList());

      // Client send the actionJSON(ie. ClientJSON) to the server
      client.sendMsg(String.valueOf(converter.convertTo()));

     
    }
  }
}
