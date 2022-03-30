package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;

public class AttackAction implements Action {
    private Integer gameID;
    private String from;
    private String to;
    private ArrayList<Unit> units;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public Integer getGameID() {
        return gameID;
    }

    public AttackAction setGameID(Integer gameID) {
        this.gameID = gameID;
        return this;
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

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public AttackAction setUnits(ArrayList<Unit> units) {
        this.units = units;
        return this;
    }
}
