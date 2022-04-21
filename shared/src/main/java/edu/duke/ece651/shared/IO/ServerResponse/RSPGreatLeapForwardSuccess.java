package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;

public class RSPGreatLeapForwardSuccess implements Response{
    private final String terrName;
    private final ArrayList<Unit> units;

    public RSPGreatLeapForwardSuccess(String terrName, ArrayList<Unit> units) {
        this.terrName = terrName;
        this.units = units;
    }

    @Override
    public void accept(ResponseVisitor responseVisitor) {
    }

    public String getTerrName() {
        return terrName;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
}
