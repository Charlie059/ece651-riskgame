package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

import java.util.ArrayList;

public class RSPMoveSuccess implements Response{
    private String from;
    private String to;
    private ArrayList<ArrayList<Integer>> units;

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    public RSPMoveSuccess(String from, String to, ArrayList<ArrayList<Integer>> units) {
        this.from = from;
        this.to = to;
        this.units = units;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ArrayList<ArrayList<Integer>> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<ArrayList<Integer>> units) {
        this.units = units;
    }
}
