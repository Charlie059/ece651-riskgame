package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPUpgradeTechSuccess implements Response{
    private Integer techCost;

    public Integer getTechCost() {
        return techCost;
    }

    public void setTechCost(Integer techCost) {
        this.techCost = techCost;
    }



}
