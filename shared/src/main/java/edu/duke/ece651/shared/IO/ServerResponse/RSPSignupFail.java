package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPSignupFail extends RSPGeneralFail {
    public RSPSignupFail() {
        super("errMessage");
    }
    public RSPSignupFail(String errMessage) {
        super(errMessage);
    }
}
