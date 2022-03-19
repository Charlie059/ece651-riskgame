package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class AttackHandlerTest {

    @Test
    void combat() {
    }

    @Test
    void doAction() throws ExecutionException, InterruptedException {
        // generate clientJSON
        ArrayList<Action> actionList = new ArrayList<>();
        HashMap<Integer, Integer> unitHashMap = new HashMap<>();
        unitHashMap.put(1, 2);
        unitHashMap.put(2, 4);
        unitHashMap.put(3, 1);

        actionList.add(new AttackAction(new Territory("a1"),new Territory("b1"), unitHashMap));

        ClientJSON converter = new ClientJSON(1, actionList);
        String clientJSON = converter.convertTo().toString();

        Map map = new Map(3);
        Territory a1 =  map.getTerritoryList().get("a1");
        Territory b1 =  map.getTerritoryList().get("b1");
        a1.addUnit(new Unit());
        b1.addUnit(new Unit());


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
        AttackHandler moveHandler = new AttackHandler(arrayList,map);
        moveHandler.doAction();
    }
}