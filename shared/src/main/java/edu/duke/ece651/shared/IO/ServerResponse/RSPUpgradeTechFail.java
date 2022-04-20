package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPUpgradeTechFail extends RSPGeneralFail {
    public RSPUpgradeTechFail() {
        super("errMessage");
    }
    public RSPUpgradeTechFail(String errMessage) {
        super(errMessage);
    }
}