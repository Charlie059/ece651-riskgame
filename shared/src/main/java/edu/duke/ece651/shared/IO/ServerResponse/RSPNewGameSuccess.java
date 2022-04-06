package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;

import java.util.ArrayList;
import java.util.HashMap;

public class RSPNewGameSuccess implements Response {

    private ClientPlayerPacket clientPlayerPacket;
    public RSPNewGameSuccess() {}

    public ClientPlayerPacket getClientPlayerPacket() {
        return clientPlayerPacket;
    }

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    public RSPNewGameSuccess(ClientPlayerPacket clientPlayerPacket) {
        this.clientPlayerPacket = clientPlayerPacket;
    }

   }
