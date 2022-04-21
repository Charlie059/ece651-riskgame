package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class BombardmentAction implements Action{
    private String enemyTerritory;

    public BombardmentAction(String enemyTerritory){
        this.enemyTerritory = enemyTerritory;
    }
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public String getEnemyTerritory() {
        return enemyTerritory;
    }
}
