package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class TestAction implements Action{
    private Boolean mode;

    public TestAction(Boolean mode) {
        this.mode = mode;
    }

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public Boolean getMode() {
        return mode;
    }
}
