package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;

public class RSPBombardmentSuccess  implements Response{
    private final String enemyTerritory;
    private final ArrayList<Unit> units;

    public RSPBombardmentSuccess(String enemyTerritory, ArrayList<Unit> units) {
        this.enemyTerritory = enemyTerritory;
        this.units = units;
    }

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    public String getEnemyTerritory() {
        return enemyTerritory;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
}
