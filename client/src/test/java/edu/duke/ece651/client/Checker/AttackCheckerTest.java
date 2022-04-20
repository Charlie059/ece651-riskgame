package edu.duke.ece651.client.Checker;

import edu.duke.ece651.client.Model.GameModel;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackCheckerTest {

    @Test
    void doCheck() {
        GameModel.getInstance().mockData();
        Checker checker = new AttackChecker();
        String[] usrIn = {"b1","a1","0","1"};
        assertTrue(checker.doCheck(usrIn));
    }
}