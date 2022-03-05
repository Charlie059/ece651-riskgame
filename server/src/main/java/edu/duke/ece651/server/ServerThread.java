package edu.duke.ece651.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//
public class ServerThread extends Thread {

  // private ServerSocket server;
  // private Integer port;
  private Socket socket;
  private ConnectionCallback callback;
  private String threadname;

  /**
   * Constructor
   */
  public ServerThread(Socket socket, String threadName, ThreadGroup tg, ConnectionCallback callback) {
    // this.port = port;
    super(tg, threadName);
    this.threadname = threadName;
    this.socket = socket;
    this.callback = callback;
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
        s = s + this.threadname;
        if (s.equals("bye")) {
          break;
        }

        System.out.println(
            "Port" + " [" + socket.getLocalPort() + "] " + "Inet" + " [" + socket.getInetAddress() + "] : " + s);
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
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      throw new IllegalArgumentException("Syntex: ./Server <port>");
    }
    System.out.println("On port" + args[0]);
    ServerSocket serversocket = new ServerSocket(Integer.parseInt(args[0]));
    ThreadGroup tg1 = new ThreadGroup("Sub Thread");
    ConnectionCallback callback = new ConnectionCallback();
    int i = 0;
    while (true) {
      try {
        i++;
        ArrayList<String> temp = callback.getCallBack();
        if (temp != null) {
          for (int k = 0; k < temp.size(); k++) {
            System.out.print(temp.get(k));
          }
        }
        String s = String.valueOf(i);
        Socket client = serversocket.accept();
        System.out.println("Current Thread Number: " + tg1.activeCount());
        ServerThread myserverthread = new ServerThread(client, s, tg1, callback);
        myserverthread.start();
      } catch (IOException ex) {
      }

    }
  }
}
