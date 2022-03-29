package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class QuitGameAction implements Action {
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }
}
