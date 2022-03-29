package edu.duke.ece651.client;

import edu.duke.ece651.shared.IO.ObjectStream;

import java.io.*;
import java.net.Socket;

public class ClientNode {
    private final String host;
    private final int portNum;
    private final Socket socket;
    private final ObjectStream objectStream;

    public ClientNode(int portNum, String host) throws IOException {
        this.portNum = portNum;
        this.host = host;
        this.socket = new Socket(this.host, this.portNum);
        this.objectStream = new ObjectStream(this.socket);
    }

    /**
     * Send object to server
     * @return true if success
     */
    public boolean sendObject(Object object){
        return this.objectStream.sendObject(object);
    }


    /**
     * Recv object from server
     * @return true if success
     */
    public Object recvObject(){
        return this.objectStream.recvObject();
    }


}
