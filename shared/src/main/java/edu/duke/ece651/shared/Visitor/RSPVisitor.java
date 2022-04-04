package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.IO.ClientActions.AttackAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPLoginSuccess;
import edu.duke.ece651.shared.IO.ServerResponse.RSPMoveSuccess;

public class RSPVisitor implements ResponseVisitor{


    @Override
    public void visit(RSPMoveSuccess rspMoveSuccess) {

    }

    @Override
    public void visit(RSPLoginSuccess rspLoginSuccess) {

    }
}
