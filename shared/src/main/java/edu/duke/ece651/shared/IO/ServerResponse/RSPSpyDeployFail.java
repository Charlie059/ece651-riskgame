package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPSpyDeployFail extends RSPGeneralFail {
    public RSPSpyDeployFail() {
        super("errMessage");
    }
    public RSPSpyDeployFail(String errMessage) {
        super(errMessage);
    }
}
