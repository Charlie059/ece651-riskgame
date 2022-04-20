package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class SpyDeployAction implements Action{
    private String to;
    private String from;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }
    public SpyDeployAction(String to, String from) {
        this.to = to;
        this.from = from;
    }
    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }
}
