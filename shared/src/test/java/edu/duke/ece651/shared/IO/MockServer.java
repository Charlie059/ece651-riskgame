package edu.duke.ece651.shared.IO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class MockServer {
    private final int portNum;
    private final ServerSocket serversocket;
    private Socket clientSocket;

    public MockServer(int portNum) throws IOException {
        this.portNum = portNum;
        this.serversocket = new ServerSocket(this.portNum);
    }

    /**
     * Accept the connection from the client
     */
    public Socket acceptClient() throws IOException {
       this.clientSocket = this.serversocket.accept();
       return this.clientSocket;
    }


    /**
     * Send object to client
     * @return true on success
     */
    public boolean sendObject(Object object) throws IOException {
        ObjectStream objectStream = new ObjectStream(this.clientSocket);
        return objectStream.sendObject(object);
    }


    /**
     * Recv object from client
     * @return true on success
     */
    public Object recvObject() throws IOException, ClassNotFoundException {
        ObjectStream objectStream = new ObjectStream(this.clientSocket);
        return objectStream.recvObject();
    }


}
