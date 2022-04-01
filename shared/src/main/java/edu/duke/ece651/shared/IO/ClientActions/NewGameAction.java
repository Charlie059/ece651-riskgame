package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;

public class NewGameAction implements Action {
    private Integer numOfPlayer;
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
}
