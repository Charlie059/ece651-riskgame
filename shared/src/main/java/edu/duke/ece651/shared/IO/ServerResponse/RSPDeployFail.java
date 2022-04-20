package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPDeployFail extends RSPGeneralFail {

    public RSPDeployFail() {
        super("errMessage");
    }
    public RSPDeployFail(String errMessage) {
        super(errMessage);
    }
}
