package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPCloakTerritoryActionFail extends RSPGeneralFail {

    public RSPCloakTerritoryActionFail() {
        super("errMessage");
    }
    public RSPCloakTerritoryActionFail(String errMessage) {
        super(errMessage);
    }
}
