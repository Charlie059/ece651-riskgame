package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPChooseSwitchGameFail extends RSPGeneralFail {

    public RSPChooseSwitchGameFail() {
        super("errMessage");
    }
    public RSPChooseSwitchGameFail(String errMessage) {
        super(errMessage);
    }
}
