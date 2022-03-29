package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public interface Action {
    public void accept(ActionVisitor actionVisitor);
}
