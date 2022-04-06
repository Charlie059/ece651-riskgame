package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPCommitSuccess implements Response{

    ClientPlayerPacket clientPlayerPacket;

    boolean isGameOver;
    boolean isWin;
    boolean isLose;

    public RSPCommitSuccess() {}

    public RSPCommitSuccess(ClientPlayerPacket clientPlayerPacket) {
        this.clientPlayerPacket = clientPlayerPacket;
        this.isGameOver = false;
        this.isWin = false;
        this.isLose = false;
    }

    public RSPCommitSuccess(ClientPlayerPacket clientPlayerPacket, boolean isGameOver, boolean isWin, boolean isLose) {
        this.clientPlayerPacket = clientPlayerPacket;
        this.isGameOver = isGameOver;
        this.isWin = isWin;
        this.isLose = isLose;
    }


    public ClientPlayerPacket getClientPlayerPacket() {
        return clientPlayerPacket;
    }

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isLose() {
        return isLose;
    }
}
