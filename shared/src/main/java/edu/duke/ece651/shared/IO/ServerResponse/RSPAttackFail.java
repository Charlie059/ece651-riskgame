package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPAttackFail extends RSPGeneralFail {

    public RSPAttackFail() {
        super("errMessage");
    }
    public RSPAttackFail(String errMessage) {
        super(errMessage);
    }

}