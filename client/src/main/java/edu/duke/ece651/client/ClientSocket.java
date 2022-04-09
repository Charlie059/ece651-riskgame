package edu.duke.ece651.client;

import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.IO.ServerResponse.Response;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {
    private static ClientSocket clientSocket;
    private final String host;
    private final int portNum;
    private final Socket socket;


    private ClientSocket(String host) throws IOException {
        this.portNum = 1651;
        this.host = host;
        this.socket = new Socket(this.host, this.portNum);
    }


    /**
     * Get the instance of ClientSocket
     * @return the instance
     */
    public static ClientSocket getInstance() throws IOException {
        String HOST = "207.246.90.49";
        if (clientSocket == null) {clientSocket = new ClientSocket(HOST);}
        return clientSocket;
    }


    /**
     * Send object to server
     *
     * @return true if success
     */
    public boolean sendObject(Object object) throws IOException {
        ObjectStream objectStream = new ObjectStream(this.socket);
        return objectStream.sendObject(object);
    }


    /**
     * Recv object from server
     *
     * @return true if success
     */
    public Object recvObject() throws IOException, ClassNotFoundException {
        ObjectStream objectStream = new ObjectStream(this.socket);
        return objectStream.recvObject();
    }


}
