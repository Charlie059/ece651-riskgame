package edu.duke.ece651.server;

import edu.duke.ece651.shared.map.Unit;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiceTest {
    @Test
    public void test_DiceDebugMode(){
        Dice dice = new Dice(6,0,true);
        assertTrue(dice.getResult().get(0)>dice.getResult().get(1));
    }
}
