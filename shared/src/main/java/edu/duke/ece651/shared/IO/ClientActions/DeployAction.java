package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;

public class DeployAction implements Action {
    private Integer gameID;
    private String to;
    private int deployUnits;
    private String playerID;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public DeployAction setTo(String _to){
        to = _to;
        return this;
    }

    public String getTo(){
        return to;
    }

    public DeployAction setGameID(Integer gameID) {
        this.gameID = gameID;
        return this;
    }

    public int getDeployUnits() {
        return deployUnits;
    }

    public DeployAction setDeployUnits(int deploy_units) {
        this.deployUnits = deploy_units;
        return this;
    }

    public String getPlayerID(){
        return playerID;
    }

    public DeployAction setPlayerID(String _playerID){
        playerID = _playerID;
        return this;
    }
}
