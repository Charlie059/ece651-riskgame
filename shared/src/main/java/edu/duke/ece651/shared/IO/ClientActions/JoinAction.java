package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;

public class JoinAction implements Action {

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }
}
