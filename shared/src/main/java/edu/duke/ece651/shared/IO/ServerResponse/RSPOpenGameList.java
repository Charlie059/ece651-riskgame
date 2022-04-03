package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;

public class RSPJoinSuccess implements Response{
    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RSPOpenGameList that = (RSPOpenGameList) o;
        return Objects.equals(gameIDArrayList, that.gameIDArrayList);
    }
}
