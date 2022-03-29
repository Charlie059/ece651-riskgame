package edu.duke.ece651.client;

import java.io.IOException;

public class ClientUI {
    public static void main(String[] args){
        try{
            ClientNode client_node = new ClientNode(12345, "localhost");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
