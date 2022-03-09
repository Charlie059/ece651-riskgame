package edu.duke.ece651.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class Client {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("127.0.0.1", 1651);
    System.out.println("Connection Success");
    InputStream in = socket.getInputStream();
    OutputStream out = socket.getOutputStream();
    var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
    var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

    String s = reader.readLine();
    System.out.println(s);

    if(s.equals("H")){
      System.out.println("You are Host!, please send number of players: ");
      int total_player = 2;
      System.out.println("You entered " + String.valueOf(total_player));
      writer.write(String.valueOf(total_player)+"\n");
      writer.flush();
    }else{
      System.out.println("You are Player");
    }

    while(true){
      //receive()
      String received_message = reader.readLine();
      System.out.println("Client received message:");

      //parse()
      System.out.println(received_message);

      //confirm()
      writer.write("I Know!" + "\n");
      writer.flush();

    }
  }
}
