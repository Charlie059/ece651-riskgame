package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;

import java.util.HashMap;

public class RSPNewGameSuccess implements Response{
    private GameID gameID;
    private Integer numOfPlayer;
    private HashMap<String, Territory> myTerritories;

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }
}
