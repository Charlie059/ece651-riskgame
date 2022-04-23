package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.map.Spy;

import java.util.UUID;

public class RSPSpyDeploySuccess implements Response{
    Spy spy;

    public RSPSpyDeploySuccess(Spy spy) {
        this.spy = spy;
    }



    public Spy getSpy() {
        return spy;
    }
}
