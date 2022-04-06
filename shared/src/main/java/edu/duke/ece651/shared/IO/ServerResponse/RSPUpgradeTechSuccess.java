package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPUpgradeTechSuccess implements Response{
    private Integer techCost;
    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

}
