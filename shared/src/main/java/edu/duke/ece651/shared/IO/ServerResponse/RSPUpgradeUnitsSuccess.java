package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPUpgradeUnitsSuccess implements Response{
    private String where;
    private Integer oldLevel;
    private Integer newLevel;
    private Integer techCost;
    @Override
    public void accept(ResponseVisitor responseVisitor) {
    }


    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public Integer getOldLevel() {
        return oldLevel;
    }

    public void setOldLevel(Integer oldLevel) {
        this.oldLevel = oldLevel;
    }

    public Integer getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(Integer newLevel) {
        this.newLevel = newLevel;
    }

    public Integer getTechCost() {
        return techCost;
    }

    public void setTechCost(Integer techCost) {
        this.techCost = techCost;
    }
}
