package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void init() throws IOException, ExecutionException, InterruptedException {
        GameController gc = new GameController();
        MockClient client1 = new MockClient(1651, "127.0.0.1");
        MockClient client2 = new MockClient(1651, "127.0.0.1");
        client1.sendMsg("2");
        gc.init();

        ArrayList<Action> actionList = new ArrayList<>();
        HashMap<Integer, Integer> unitHashMap = new HashMap<>();
        unitHashMap.put(1, 2);
        unitHashMap.put(2, 4);
        unitHashMap.put(3, 1);

        actionList.add(new DeployAction(new Territory("a1"), unitHashMap));
        client1.sendMsg("{\"playerID\": 1,\"actions\": [{\"actionType\": \"deploy\",\"to\": \"a1\",\"units\": [{\"level\": 1,\"value\": 3},{\"level\": 2,\"value\": 5}]}]}");
        client2.sendMsg("{\"playerID\": 2,\"actions\": [{\"actionType\": \"deploy\",\"to\": \"a1\",\"units\": [{\"level\": 1,\"value\": 3},{\"level\": 2,\"value\": 5}]}]}");

//        gc.initFutureList();
        Map map = new Map(2);
        gc.map = map;
        gc.runOneGameLoop();

        client1.sendMsg("{\"playerID\": 1,\"actions\": [{\"actionType\": \"attack\",\"from\": \"a1\",\"to\": \"a2\",\"units\": [{\"level\": 1,\"value\": 3},{\"level\": 2,\"value\": 5}]}]}");
        client2.sendMsg("{\"playerID\": 2,\"actions\": [{\"actionType\": \"deploy\",\"to\": \"a1\",\"units\": [{\"level\": 1,\"value\": 3},{\"level\": 2,\"value\": 5}]}]}");

        gc.runOneGameLoop();
    }

    @Test
    void initFutureList() {
    }

    @Test
    void runOneGameLoop() {
    }

    @Test
    void main() {
    }
}