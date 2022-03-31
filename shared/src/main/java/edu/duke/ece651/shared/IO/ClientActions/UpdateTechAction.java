package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

import java.util.ArrayList;

public class UpdateTechAction implements Action {
    private Integer gameID;
    private Integer nextLevel;//upgrade tech level to nextLevel
    private Integer currTechResource; //tech resource of the player before upgrade

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }


    public Integer getGameID(){return gameID;}
    public Integer getNextLevel(){return nextLevel;}
    public Integer getCurrTechResource(){return currTechResource;}


    public UpdateTechAction setGameID(Integer _gameID){
        gameID= _gameID;
        return this;
    }

    public UpdateTechAction setNextLevel(Integer _nextLevel){
        nextLevel= _nextLevel;
        return this;
    }

    public UpdateTechAction setCurrTechResource(Integer _CurrTechResource){
        currTechResource= _CurrTechResource;
        return this;
    }

}
