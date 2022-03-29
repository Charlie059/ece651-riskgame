package edu.duke.ece651.shared.IO;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStreamTest {
    @Test
    public void test_ObjectStream() throws IOException, InterruptedException {
        MockServer mockServer= new MockServer(1651);
        MockClient mockClient = new MockClient(1651, "127.0.0.1");
        Socket clientSocket =  mockServer.acceptClient();

        ClientHandler clientSock = new ClientHandler(clientSocket, mockServer);
        new Thread(clientSock).start();


        MockClass recvObj = (MockClass) mockClient.recvObject();

        assertEquals(recvObj.getA(), 2);
    }
}