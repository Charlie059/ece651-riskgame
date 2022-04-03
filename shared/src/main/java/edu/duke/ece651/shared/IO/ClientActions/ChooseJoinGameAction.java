package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;

public class ChooseJoinGameAction implements Action{
    private GameID gameID;

    public ChooseJoinGameAction(GameID gameID){
        this.gameID = gameID;
    }

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }


    public GameID getGameID() {
        return gameID;
    }

    public void setGameID(GameID gameID) {
        this.gameID = gameID;
    }
}
