package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class MoveHandlerTest {

    @Test
    void doAction() throws ExecutionException, InterruptedException {
        // generate clientJSON
        ArrayList<Action> actionList = new ArrayList<>();
        HashMap<Integer, Integer> unitHashMap = new HashMap<>();
        unitHashMap.put(1, 2);
        unitHashMap.put(2, 4);
        unitHashMap.put(3, 1);

        actionList.add(new MoveAction(new Territory("a1"),new Territory("a2"), unitHashMap));

        ClientJSON converter = new ClientJSON(1, actionList);
        String clientJSON = converter.convertTo().toString();

        Map map = new Map(3);
        ClientJSONParser clientJSONParser = new ClientJSONParser(clientJSON, map);

        ArrayList<Future<?>> arrayList = new ArrayList<>();
        Future<?> future  = new Future<Object>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Object get() throws InterruptedException, ExecutionException {
                return clientJSONParser;
            }

            @Override
            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        arrayList.add(future);
        MoveHandler moveHandler = new MoveHandler(arrayList,map);
        moveHandler.doAction();
    }
}
