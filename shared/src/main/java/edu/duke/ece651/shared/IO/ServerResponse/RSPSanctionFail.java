package edu.duke.ece651.shared.IO.ServerResponse;

public class RSPSanctionFail extends RSPGeneralFail {

    public RSPSanctionFail() {
        super("errMessage");
    }
    public RSPSanctionFail(String errMessage) {
        super(errMessage);
    }

}
