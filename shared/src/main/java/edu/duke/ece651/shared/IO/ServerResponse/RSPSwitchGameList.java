package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.ArrayList;
import java.util.Objects;

public class RSPSwitchGameList implements Response{
    ArrayList<GameID>gameIDArrayList;

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    public RSPSwitchGameList(ArrayList<GameID> gameIDArrayList) {
        this.gameIDArrayList = gameIDArrayList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RSPSwitchGameList that = (RSPSwitchGameList) o;
        return Objects.equals(gameIDArrayList, that.gameIDArrayList);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(gameIDArrayList);
//    }
}
