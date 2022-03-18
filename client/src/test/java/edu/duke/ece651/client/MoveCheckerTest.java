package edu.duke.ece651.client;

import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Unit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MoveCheckerTest {

    public MoveChecker createMoveChecker(String to){
        int id = 1;
        Map myMap = new Map(3);
        myMap.getTerritoryList().get("a1").setOwner(1);
        myMap.getTerritoryList().get("a1").getUnits().put(1, new ArrayList<>());
        //add 3 level-1 units to a1
        myMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit(1));
        myMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit(1));
        myMap.getTerritoryList().get("a1").getUnits().get(1).add(new Unit(1));

        myMap.getTerritoryList().get("a2").setOwner(1);
        myMap.getTerritoryList().get("a2").getUnits().put(1, new ArrayList<>());
        myMap.getTerritoryList().get("b1").setOwner(2);
        myMap.getTerritoryList().get("b1").getUnits().put(1, new ArrayList<>());

        myMap.getTerritoryList().get("b3").setOwner(1);
        myMap.getTerritoryList().get("b3").getUnits().put(1, new ArrayList<>());

        HashMap<Integer, Integer> moveUnits = new HashMap<>();

        moveUnits.put(1, 3); // move 3 level-1 units
        String from = "a1";
        MoveChecker myChecker = new MoveChecker(myMap, moveUnits, from, to, 1);
        return myChecker;
    }

    @Test
    void doCheck() {
        String output;
        //case 1: correct case
        MoveChecker myChecker = createMoveChecker("a2");
        output = myChecker.doCheck(1, myChecker.getFrom_name(), myChecker.getTo_name());
        assertEquals(output, null);

        //case 2: different owners
        MoveChecker myChecker2 = createMoveChecker("b1");
        output = myChecker2.doCheck(1, myChecker2.getFrom_name(), myChecker2.getTo_name());
        System.out.println(output);
        assertEquals(output, "territory (from) and territory (to) belong to different(wrong) player.");

        //case 3: path does not exist
        MoveChecker myChecker3 = createMoveChecker("b3");
        output = myChecker3.doCheck(1, myChecker3.getFrom_name(), myChecker3.getTo_name());
        System.out.println(output);
        assertEquals(output, "Move Error: there is no path b/w \"a1\" Territory and \"b3\" Territory!");
    }
}