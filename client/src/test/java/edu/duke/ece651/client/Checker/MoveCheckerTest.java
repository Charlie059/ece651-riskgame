package edu.duke.ece651.client.Checker;

import edu.duke.ece651.client.Model.GameModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveCheckerTest {

    @Test
    void doCheck() {
        GameModel.getInstance().mockData();
        Checker checker = new MoveChecker();
        String[] usrIn = {"b1","b2","0","1"};
        assertTrue(checker.doCheck(usrIn));
    }
}