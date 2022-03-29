package edu.duke.ece651.shared.IO;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MockClient {

    private final String host;
    private final int portNum;
    private final Socket socket;

    public MockClient(int portNum, String host) throws IOException {
        this.portNum = portNum;
        this.host = host;
        this.socket = new Socket(this.host, this.portNum);
    }

    /**
     * Send object to server
     * @return true if success
     */
    public boolean sendObject(Object object) throws IOException {
        ObjectStream objectStream = new ObjectStream(this.socket);
        return objectStream.sendObject(object);
    }


    /**
     * Recv object from server
     * @return true if success
     */
    public Object recvObject() throws IOException, ClassNotFoundException {
        return new ObjectStream(this.socket).recvObject();
    }


}
