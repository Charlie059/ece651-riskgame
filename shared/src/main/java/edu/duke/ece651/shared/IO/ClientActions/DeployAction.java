package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;

public class DeployAction implements Action {
    private String to;
    private int deployUnits;
    private String playerID;

    public DeployAction(){}
    public DeployAction(String to, int deployUnits, String playerID) {
        this.to = to;
        this.deployUnits = deployUnits;
        this.playerID = playerID;
    }

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

    public int getDeployUnits() {
        return deployUnits;
    }

    public DeployAction setDeployUnits(int deploy_units) {
        this.deployUnits = deploy_units;
        return this;
    }
}
