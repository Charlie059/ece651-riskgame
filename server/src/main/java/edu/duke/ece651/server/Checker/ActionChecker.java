package edu.duke.ece651.server.Checker;

import edu.duke.ece651.shared.IO.ClientActions.Action;

public interface ActionChecker {

    boolean doCheck(Action action);


}
