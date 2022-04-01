package edu.duke.ece651.shared.Wrapper;

import java.io.Serializable;
import java.util.Objects;

public class GameID implements Serializable {
    private Integer GameID = null;

    public GameID(Integer gameID) {
        GameID = gameID;
    }

    public Integer getCurrGameID() {
        return GameID;
    }

    public void setCurrGameID(Integer currGameID) {
        this.GameID = currGameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameID gameID = (GameID) o;
        return Objects.equals(GameID, gameID.GameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(GameID);
    }
}
