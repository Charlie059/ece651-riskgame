package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPUpgradeUnitsFail extends RSPGeneralFail {
    public RSPUpgradeUnitsFail() {
        super("errMessage");
    }
    public RSPUpgradeUnitsFail(String errMessage) {
        super(errMessage);
    }
}
