package edu.duke.ece651.shared.IO;



import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final MockServer mockServer;
    public ClientHandler(Socket socket, MockServer mockServer) {
        this.clientSocket = socket;
        this.mockServer= mockServer;
    }

    public void run() {
        try {
            this.mockServer.sendObject(new MockClass(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

