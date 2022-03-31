package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class NewGameAction implements Action {
    private Integer numOfPlayer;
    private Integer gameID;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public Integer getNumOfPlayer() {
        return numOfPlayer;
    }

    public NewGameAction setNumOfPlayer(Integer numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
        return this;
    }


    public Integer getGameID() {
        return gameID;
    }

    public NewGameAction setGameID(Integer gameID) {
        this.gameID = gameID;
        return this;
    }
}
