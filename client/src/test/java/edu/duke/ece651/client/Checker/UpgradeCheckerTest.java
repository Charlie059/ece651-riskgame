package edu.duke.ece651.client.Checker;

import edu.duke.ece651.client.Model.GameModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeCheckerTest {

    @Test
    void doCheck() {
        GameModel.getInstance().mockData();
        Checker checker = new UpgradeChecker();
        String[] usrIn = {"b1","0","1","1"};
        checker.doCheck(usrIn);
    }
}