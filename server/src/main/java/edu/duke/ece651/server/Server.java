package edu.duke.ece651.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server {

  private ServerSocket server;
  private Integer port;
  private Socket socket;

  public Server(Integer port) throws SocketException, IOException {
    this.port = port;
    this.server = new ServerSocket(this.port);

    try {
      this.socket = server.accept();
      InputStream in = socket.getInputStream();
      var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
      for (;;) {
        String s = reader.readLine();
        if (s.equals("bye")) {
          break;
        }
        System.out.println(s);
      }
      // read method read byte of data from input stream and return int 0-255

    } finally {

    }
  }
}
