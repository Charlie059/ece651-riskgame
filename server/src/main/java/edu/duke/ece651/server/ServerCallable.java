package edu.duke.ece651.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
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
//    if(socket.isClosed()) return new ClientJSONParser("{\n" +
//            "  \"playerID\": 0,\n" +
//            "  \"actions\": []\n" +
//            "}\n", this.map);


    OutputStream out = this.socket.getOutputStream();
    InputStream in = this.socket.getInputStream();

    var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

    // send ServerJSON
    // For first Loop: without previously received ClientJSON, ServerJSON was created by
    // map(num_players), but this ServerJSON was omitted by Client site at first Loop
    // For other loop: this ServerJSON then will be seriously considered
    ServerJSON serverJSON = new ServerJSON(this.map);
    try {
      writer.write(serverJSON.convertTo() + "\n");
      writer.flush();
    }catch(SocketException e){

      return new ClientJSONParser("{\n" +
              "  \"playerID\": 0,\n" +
              "  \"actions\": []\n" +
              "}\n", this.map);
    }
    // rec ClientJSON
    // Note JSON is sent by String

    String s = reader.readLine();
    if (s == null) s = "{\n" +
            "  \"playerID\": 0,\n" +
            "  \"actions\": []\n" +
            "}\n";
    // parse CLientJSON
    ClientJSONParser clientJsonParser = new ClientJSONParser(s, this.map);
    return clientJsonParser;
  }

}
