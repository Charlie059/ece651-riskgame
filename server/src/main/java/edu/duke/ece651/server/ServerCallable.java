package edu.duke.ece651.server;

import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ServerCallable implements Callable<String> {

  private Socket socket;

  ServerCallable(Socket socket) {
    this.socket = socket;
  }

  public String call() throws IOException, InterruptedException {
    OutputStream out = this.socket.getOutputStream();
    InputStream in = this.socket.getInputStream();

    var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

    //send
    writer.write("This is Message from the Server" + "\n");
    writer.flush();

    //rec
    String s = reader.readLine();
    return s;
  }

}
