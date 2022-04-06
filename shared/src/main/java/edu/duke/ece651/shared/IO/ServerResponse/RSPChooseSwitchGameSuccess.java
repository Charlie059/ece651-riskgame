package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;


public class RSPChooseSwitchGameSuccess implements Response{

    private ClientPlayerPacket clientPlayerPacket;

    public RSPChooseSwitchGameSuccess(){}
    public RSPChooseSwitchGameSuccess(ClientPlayerPacket clientPlayerPacket) {
        this.clientPlayerPacket = clientPlayerPacket;
    }


    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }
}
