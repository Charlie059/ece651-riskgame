package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.IO.ClientActions.AttackAction;
import edu.duke.ece651.shared.IO.ServerResponse.RSPMoveSuccess;

public class RSPVisitor implements ResponseVisitor{


    @Override
    public void visit(RSPMoveSuccess rspMoveSuccess) {
        rspMoveSuccess.getFrom();
                rspMoveSuccess.getTo();
                rspMoveSuccess.getUnits();
                //TODO:clientMap.moveUpdate(from, to, units);
    }
}
