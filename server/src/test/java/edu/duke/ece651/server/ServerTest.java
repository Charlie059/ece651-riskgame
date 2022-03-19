package edu.duke.ece651.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;


class ServerTest {

    @Test
    void ServerTest() throws IOException {
        Server server = new Server(4444);
        MockClient client = new MockClient(4444,"127.0.0.1");
        server.acceptClient();
        server.getService();
        server.sendMsg(1,"HELLO CLIENT");
        Assertions.assertEquals("HELLO CLIENT",client.recvMsg());
        client.sendMsg("HELLO SERVER");
        Assertions.assertEquals("HELLO SERVER",server.recvMsg(1));
        server.getServersocket();
        server.getClientSocketList();
    }
}

