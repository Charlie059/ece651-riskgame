package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPSpyUpgradeFail extends RSPGeneralFail {
    public RSPSpyUpgradeFail() {
        super("errMessage");
    }
    public RSPSpyUpgradeFail(String errMessage) {
        super(errMessage);
    }
}
