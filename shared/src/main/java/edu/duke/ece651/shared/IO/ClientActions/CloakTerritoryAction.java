package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class CloakTerritoryAction implements Action{

    private String from;

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public CloakTerritoryAction(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }
}
