package edu.duke.ece651.shared.IO;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStreamTest {
    @Test
    public void test_ObjectStream() throws IOException, InterruptedException, ClassNotFoundException {
        MockServer mockServer= new MockServer(4444);
        MockClient mockClient = new MockClient(4444, "127.0.0.1");
        Socket clientSocket =  mockServer.acceptClient();

        // Throw a new thread
        ClientHandler clientSock = new ClientHandler(clientSocket, mockServer);
        new Thread(clientSock).start();

        MockClass recvObj = (MockClass) mockClient.recvObject();
        assertEquals(recvObj.getA(), 2);
        clientSocket.close();

    }


}