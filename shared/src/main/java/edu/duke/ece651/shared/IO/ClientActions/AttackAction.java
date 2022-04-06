package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;

public class AttackAction implements Action {
    private String from;
    private String to;
    private ArrayList<ArrayList<Integer>> units;
    private Integer gameID;
    private String playerID;

    public AttackAction(){}
    public AttackAction(Integer gameID, String playerID, String from, String to, ArrayList<ArrayList<Integer>> units) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.from = from;
        this.to = to;
        this.units = units;
    }
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }


    public String getFrom() {
        return from;
    }

    public AttackAction setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public AttackAction setTo(String to) {
        this.to = to;
        return this;
    }

    public ArrayList<ArrayList<Integer>> getUnits() {
        return units;
    }

    public AttackAction setUnits(ArrayList<ArrayList<Integer>> units) {
        this.units = units;
        return this;
    }

}
