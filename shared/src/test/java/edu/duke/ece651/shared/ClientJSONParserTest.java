package edu.duke.ece651.shared;

import edu.duke.ece651.shared.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ClientJSONParserTest {

    @Test
    void doParseTEST() {
        //generate clientJSON
        ArrayList<Action> actionList = new ArrayList<>();
        HashMap<Integer, Integer> unitHashMap = new HashMap<>();
        unitHashMap.put(1,2);
        unitHashMap.put(2,4);
        unitHashMap.put(3,1);

        actionList.add(new DeployAction(new Territory("a1"),unitHashMap));
        actionList.add(new AttackAction(new Territory("a2"), new Territory("b1"),unitHashMap));
        ClientJSON converter = new ClientJSON(1, actionList);
        String clientJSON = converter.convertTo().toString();

        Map map = new Map(3);
        ClientJSONParser clientJSONParser = new ClientJSONParser(clientJSON, map);
        clientJSONParser.doParse();
        ArrayList<Action> actionArrayList = clientJSONParser.getActionArrayList();
        ClientJSON expected = new ClientJSON(1, actionArrayList);

        Assertions.assertEquals(clientJSON, expected.convertTo().toString());
    }
}