package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPChooseJoinGameFail extends RSPGeneralFail {

    public RSPChooseJoinGameFail() {
        super("errMessage");
    }
    public RSPChooseJoinGameFail(String errMessage) {
        super(errMessage);
    }
}
