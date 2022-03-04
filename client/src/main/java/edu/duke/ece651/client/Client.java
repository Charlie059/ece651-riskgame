package edu.duke.ece651.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 12345);
    OutputStream out = socket.getOutputStream();
    var writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
    int i = 0;

    while (i < 30) {
      String s = String.valueOf(i);
      writer.write(s);
      writer.flush();
      i++;
    }
    writer.write("bye");
  }
}
