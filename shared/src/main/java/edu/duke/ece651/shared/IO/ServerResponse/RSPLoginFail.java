package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPLoginFail extends RSPGeneralFail {

    public RSPLoginFail() {
        super("errMessage");
    }
    public RSPLoginFail(String errMessage) {
        super(errMessage);
    }
}
