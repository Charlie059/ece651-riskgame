package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;

public class JoinAction implements Action {
    private GameID gameID;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public void setGameID(GameID gameID) {
        this.gameID = gameID;
    }

    public GameID getGameID() {
        return gameID;
    }
}
