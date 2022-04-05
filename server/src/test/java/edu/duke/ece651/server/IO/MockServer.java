package edu.duke.ece651.server.IO;

import edu.duke.ece651.shared.IO.ObjectStream;

import java.io.IOException;
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

    public void closeSocket() throws IOException {
        this.serversocket.close();
    }


    /**
     * Send object to server
     * @return true if success
     */
    public boolean sendObject(Object object) throws IOException {
        ObjectStream objectStream = new ObjectStream(this.clientSocket);
        return objectStream.sendObject(object);
    }


    /**
     * Recv object from server
     * @return true if success
     */
    public Object recvObject() throws IOException, ClassNotFoundException {
        ObjectStream objectStream = new ObjectStream(this.clientSocket);
        return objectStream.recvObject();
    }

}
