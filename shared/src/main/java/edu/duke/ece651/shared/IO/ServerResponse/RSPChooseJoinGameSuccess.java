package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPChooseJoinGameSuccess implements Response {
    ClientPlayerPacket clientPlayerPacket;

    public RSPChooseJoinGameSuccess() {}

    public RSPChooseJoinGameSuccess(ClientPlayerPacket clientPlayerPacket) {
        this.clientPlayerPacket = clientPlayerPacket;
    }

    public ClientPlayerPacket getClientPlayerPacket() {
        return clientPlayerPacket;
    }


}
