package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;

import java.util.ArrayList;
import java.util.Objects;

public class RSPOpenGameList implements Response{
    //TODO: Change to HashMao<GameID, (Integer)Numofplayer>
    private ArrayList <GameID> gameIDArrayList;

    public RSPOpenGameList(ArrayList<GameID> gameIDArrayList) {
        this.gameIDArrayList = gameIDArrayList;
    }

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
