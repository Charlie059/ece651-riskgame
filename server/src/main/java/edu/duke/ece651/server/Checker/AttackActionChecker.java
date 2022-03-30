package edu.duke.ece651.server.Checker;

import edu.duke.ece651.shared.IO.ClientActions.Action;

public class AttackActionChecker implements  ActionChecker{

    @Override
    public boolean doCheck(Action action) {
        return false;
    }
}
