package edu.duke.ece651.server.IO;

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


//    /**
//     * Send object to client
//     * @return true on success
//     */
//    public boolean sendObject(ObjectStream objectStream ,Object object) throws IOException {
//        return objectStream.sendObject(object);
//    }
//
//
//    /**
//     * Recv object from client
//     * @return true on success
//     */
//    public Object recvObject(ObjectStream objectStream) throws IOException, ClassNotFoundException {
//        return objectStream.recvObject();
//    }


}
