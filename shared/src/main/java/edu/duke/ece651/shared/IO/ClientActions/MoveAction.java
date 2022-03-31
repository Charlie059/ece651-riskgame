package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;

public class MoveAction implements Action {
    private Integer gameID;
    private String from;
    private String to;
    private ArrayList<ArrayList<Integer>> units;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public Integer getGameID() {
        return gameID;
    }

    public MoveAction setGameID(Integer gameID) {
        this.gameID = gameID;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public MoveAction setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public MoveAction setTo(String to) {
        this.to = to;
        return this;
    }

    public ArrayList<ArrayList<Integer>> getUnits() {
        return units;
    }

    public MoveAction setUnits(ArrayList<ArrayList<Integer>> units) {
        this.units = units;
        return this;
    }
}
