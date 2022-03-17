package edu.duke.ece651.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

import edu.duke.ece651.shared.ClientJSONParser;
import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.ServerJSON;
import edu.duke.ece651.shared.ServerJSONParser;

public class ServerCallable implements Callable<ClientJSONParser> {

  private Socket socket;
  private Map map;

  ServerCallable(Socket socket, Map map) {
    this.socket = socket;
    this.map = map;
  }

  public ClientJSONParser call() throws IOException, InterruptedException {
    OutputStream out = this.socket.getOutputStream();
    InputStream in = this.socket.getInputStream();

    var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

    // send ServerJSON
    ServerJSON serverJSON = new ServerJSON(this.map);
    writer.write(serverJSON.convertTo() + "\n");
    writer.flush();

    // rec ClientJSON
    // Note JSON is sent by String

    String s = reader.readLine();

    // parse CLientJSON
    ClientJSONParser clientJsonParser = new ClientJSONParser(s, this.map);
    return clientJsonParser;
  }

}
