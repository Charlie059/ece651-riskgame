package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class SpyDeployAction implements Action{
    private String to;

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }
    public SpyDeployAction(String to) {
        this.to = to;
    }



    public String getTo() {
        return to;
    }
}
