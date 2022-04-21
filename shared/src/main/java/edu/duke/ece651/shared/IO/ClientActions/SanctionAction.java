package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;

public class SanctionAction implements Action{
    private AccountID enemyID;

    public SanctionAction(AccountID enemyID){
        this.enemyID = enemyID;
    }

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public AccountID getEnemyID() {
        return enemyID;
    }
}
