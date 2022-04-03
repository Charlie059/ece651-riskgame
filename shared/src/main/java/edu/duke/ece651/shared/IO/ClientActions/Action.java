package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

import java.io.Serializable;

public interface Action extends Serializable {
    void accept(ActionVisitor actionVisitor);
}
