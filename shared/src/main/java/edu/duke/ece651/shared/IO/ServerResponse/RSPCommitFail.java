package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPCommitFail extends RSPGeneralFail {

    public RSPCommitFail() {
        super("errMessage");
    }
    public RSPCommitFail(String errMessage) {
        super(errMessage);
    }


}
