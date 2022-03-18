package edu.duke.ece651.shared;

import edu.duke.ece651.shared.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ClientJSONParserTest {

  @Test
  void test_doDeploy() {
    // generate clientJSON
    ArrayList<Action> actionList = new ArrayList<>();
    HashMap<Integer, Integer> unitHashMap = new HashMap<>();
    unitHashMap.put(1, 2);
    unitHashMap.put(2, 4);
    unitHashMap.put(3, 1);

    actionList.add(new DeployAction(new Territory("a1"), unitHashMap));
    actionList.add(new DeployAction(new Territory("a2"), unitHashMap));
    actionList.add(new DeployAction(new Territory("a3"), unitHashMap));
    actionList.add(new DeployAction(new Territory("b1"), unitHashMap));
    ClientJSON converter = new ClientJSON(1, actionList);
    String clientJSON = converter.convertTo().toString();

    Map map = new Map(3);
    ClientJSONParser clientJSONParser = new ClientJSONParser(clientJSON, map);

    ArrayList<Action> actionArrayList = clientJSONParser.getActionArrayList();
    ClientJSON expected = new ClientJSON(1, actionArrayList);

    //Assertions.assertEquals(clientJSON, expected.convertTo().toString());
    //Assertions.assertEquals(clientJSONParser.getActionArrayList(), clientJSONParser.getDeploy());
    //Assertions.assertEquals(1, clientJSONParser.getPlayerID());
  }

  @Test
  void test_doOrders() {
    // generate clientJSON
    ArrayList<Action> actionList = new ArrayList<>();
    ArrayList<Action> attackList = new ArrayList<>();
    ArrayList<Action> moveList = new ArrayList<>();
    HashMap<Integer, Integer> unitHashMap = new HashMap<>();
    unitHashMap.put(1, 2);
    unitHashMap.put(2, 4);
    unitHashMap.put(3, 1);

    actionList.add(new AttackAction(new Territory("a2"), new Territory("b1"), unitHashMap));
    actionList.add(new MoveAction(new Territory("a3"), new Territory("a2"), unitHashMap));
    actionList.add(new AttackAction(new Territory("a2"), new Territory("b1"), unitHashMap));

    attackList.add(actionList.get(0));
    attackList.add(actionList.get(2));
    moveList.add(actionList.get(1));

    ClientJSON converter = new ClientJSON(1, actionList);
    String clientJSON = converter.convertTo().toString();

    Map map = new Map(3);
    ClientJSONParser clientJSONParser = new ClientJSONParser(clientJSON, map);

    ArrayList<Action> actionArrayList = clientJSONParser.getActionArrayList();
    ClientJSON expected = new ClientJSON(1, actionArrayList);

    /*
    Assertions.assertEquals(clientJSON, expected.convertTo().toString());

    Assertions.assertEquals(attackList.get(0).getActionName(), clientJSONParser.getAttack().get(0).getActionName());
    Assertions.assertEquals(attackList.get(1).getActionName(), clientJSONParser.getAttack().get(1).getActionName());
    Assertions.assertEquals(attackList.get(0).getFrom().getName(),
        clientJSONParser.getAttack().get(0).getFrom().getName());

    Assertions.assertEquals(attackList.get(0).getUnitNumber().size(),
        clientJSONParser.getAttack().get(0).getUnitNumber().size());
    
    Assertions.assertEquals(moveList.get(0).getFrom().getName(), clientJSONParser.getMove().get(0).getFrom().getName());
    */
  }

}
