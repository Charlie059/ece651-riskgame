package edu.duke.ece651.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import jdk.internal.util.xml.impl.Input;

public class Client {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 12345);
    OutputStream out = socket.getOutputStream();
    InputStream in = socket.getInputStream();
    var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    var writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));

    String s = reader.readLine();
    if(s.equals("H")){
      System.out.println("You are Host!, please send number of players: ");
      int total_player = 3;
      System.out.println("You entered " + String.valueOf(total_player));
      writer.write(String.valueOf(total_player));
      writer.flush();
    }else{
      System.out.println("You are Player");
    }

    while(true){
      //receive()
    String received_message = reader.readLine();
    //parse()
    System.out.println(received_message);
    //confirm()
    writer.write("I Know!");
    }
  }
}
