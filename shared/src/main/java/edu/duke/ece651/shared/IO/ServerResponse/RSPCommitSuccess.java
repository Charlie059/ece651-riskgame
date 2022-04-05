package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPCommitSuccess implements Response{

    ClientPlayerPacket clientPlayerPacket;

    public RSPCommitSuccess() {}

    public RSPCommitSuccess(ClientPlayerPacket clientPlayerPacket) {
        this.clientPlayerPacket = clientPlayerPacket;
    }

    public ClientPlayerPacket getClientPlayerPacket() {
        return clientPlayerPacket;
    }

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }
}
