package edu.duke.ece651.client.Checker;

import edu.duke.ece651.client.Model.GameModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewGameCheckerTest {

    @Test
    void doCheck() {
        GameModel.getInstance().mockData();
        Checker checker = new NewGameChecker();
        String[] usrIn = {"2"};
        assertTrue(checker.doCheck(usrIn));
        String[] usrIn1 = {"0"};
        assertFalse(checker.doCheck(usrIn1));
    }
}