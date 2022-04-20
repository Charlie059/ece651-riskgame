package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPMoveFail extends RSPGeneralFail {
    public RSPMoveFail() {
        super("errMessage");
    }
    public RSPMoveFail(String errMessage) {
        super(errMessage);
    }
}
