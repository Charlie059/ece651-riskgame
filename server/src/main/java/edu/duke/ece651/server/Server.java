package edu.duke.ece651.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import com.google.errorprone.annotations.ForOverride;

//
public class Server extends Thread {

  private ServerSocket server;
  private Integer port;
  private Socket socket;

  /**
   * Constructor
   */
  public Server(Integer port) throws SocketException, IOException {
    this.port = port;
    this.server = new ServerSocket(this.port);

    try {
      this.socket = server.accept();
    } finally {
    }
  }

  /*
   * run()
   */
  public void run() {
    try {
      InputStream in = socket.getInputStream();
      var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
      for (;;) {
        String s = reader.readLine();
        if (s.equals("bye")) {
          break;
        }
        
        System.out.println("Port"+" ["+socket.getLocalPort()+"] "+ "Inet"+" ["+socket.getInetAddress()+"] : "+ s);
      }
      // read method read byte of data from input stream and return int 0-255
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      try {
        this.socket.close();
      } catch (IOException ignore) {
      }
    }
  }

  /*
   * Gamecontroller(...,..,....,port...)
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("Syntex: ./Server <port>");
    }
    System.out.println("On port" + args[0]);
    int i = 0;
    while (true) {
      try {
        Server server = new Server(Integer.parseInt(args[0]));
        server.start();
      } catch (IOException ex){
      }

    }
  }
}
