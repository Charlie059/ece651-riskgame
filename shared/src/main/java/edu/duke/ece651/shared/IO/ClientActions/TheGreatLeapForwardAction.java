package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class TheGreatLeapForwardAction implements Action{
    private String territoryName;

    public TheGreatLeapForwardAction(String territoryName){
        this.territoryName = territoryName;
    }

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public String getTerritoryName() {
        return this.territoryName;
    }
}
