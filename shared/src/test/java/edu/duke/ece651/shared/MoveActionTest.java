package edu.duke.ece651.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MoveActionTest {

    @Test
    void doAction() {
        HashMap<Integer, Integer> unitHashMap = new HashMap<>();
        unitHashMap.put(1, 2);
        unitHashMap.put(2, 4);
        unitHashMap.put(3, 1);

        MoveAction action = new MoveAction(new Territory("a1"),new Territory("b1"),unitHashMap);
        action.doAction();
    }
}