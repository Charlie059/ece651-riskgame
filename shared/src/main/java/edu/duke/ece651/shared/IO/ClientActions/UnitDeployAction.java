package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class UnitDeployAction implements Action{
    private String to;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public UnitDeployAction(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }
}
