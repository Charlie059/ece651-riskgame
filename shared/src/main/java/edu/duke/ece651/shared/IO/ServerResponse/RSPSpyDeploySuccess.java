package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

import java.util.UUID;

public class RSPSpyDeploySuccess implements Response{
    UUID spyUUID;
    Integer spyType;

    public RSPSpyDeploySuccess(UUID spyUUID, Integer spyType) {
        this.spyUUID = spyUUID;
        this.spyType = spyType;
    }

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    public UUID getSpyUUID() {
        return spyUUID;
    }

    public Integer getSpyType() {
        return spyType;
    }
}
