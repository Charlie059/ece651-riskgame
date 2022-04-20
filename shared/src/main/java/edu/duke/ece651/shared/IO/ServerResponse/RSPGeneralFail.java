package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPGeneralFail implements Response {

    protected final String errMessage;

    public RSPGeneralFail(String errMessage) {
        this.errMessage = errMessage;
    }


    @Override
    public void accept(ResponseVisitor responseVisitor) {
    }

    public String getErrMessage() {
        return errMessage;
    }
}
