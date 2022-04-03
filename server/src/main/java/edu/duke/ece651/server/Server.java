package edu.duke.ece651.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int portNum;
    private final ServerSocket serversocket;


    public Server(int portNum) throws IOException {
        this.portNum = portNum;
        this.serversocket = new ServerSocket(this.portNum);
    }

    public ServerSocket getServersocket() {
        return serversocket;
    }
}
