package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPSpyMoveFail extends RSPGeneralFail {
    public RSPSpyMoveFail() {
        super("errMessage");
    }
    public RSPSpyMoveFail(String errMessage) {
        super(errMessage);
    }
}
