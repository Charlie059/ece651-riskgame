package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPCardBuyFail extends RSPGeneralFail {

    public RSPCardBuyFail() {
        super("errMessage");
    }

    public RSPCardBuyFail(String errMessage) {
        super(errMessage);
    }

}