package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.IO.ServerResponse.RSPMoveSuccess;

public interface ResponseVisitor {
    void visit(RSPMoveSuccess rspMoveSuccess);
}
