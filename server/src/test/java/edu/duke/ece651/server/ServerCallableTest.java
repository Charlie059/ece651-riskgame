package edu.duke.ece651.server;

import edu.duke.ece651.shared.Map;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerCallableTest {

    @Test
    void call() throws IOException, InterruptedException {
        Server server = new Server(12345);
        Map map = new Map(3);
        MockClient client = new MockClient(12345, "127.0.0.1");
        server.acceptClient();
        ServerCallable serverCallable = new ServerCallable(server.getClientSocketList().get(0),map);
        client.sendMsg("{\"playerID\": 1,\"actions\": [{\"actionType\": \"deploy\",\"to\": \"a1\",\"units\": [{\"level\": 1,\"value\": 3},{\"level\": 2,\"value\": 5}]}]}");
        serverCallable.call();
        client.recvMsg();
    }
}