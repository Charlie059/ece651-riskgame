package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

import java.util.ArrayList;

public class RSPMoveSuccess implements Response{
    private String from;
    private String to;
    private ArrayList<ArrayList<Integer>> units;
    private int totalCost;


    public RSPMoveSuccess(String from,
                          String to,
                          ArrayList<ArrayList<Integer>> units,
                          int totalCost) {
        this.from = from;
        this.to = to;
        this.units = units;
        this.totalCost = totalCost;
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

    public int getTotalCost(){return this.totalCost;}

    public void setTotalCost(int totalCost){this.totalCost = totalCost;}
}
