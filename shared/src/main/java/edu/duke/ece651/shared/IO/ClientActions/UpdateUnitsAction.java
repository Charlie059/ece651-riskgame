package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.map.Unit;

import java.util.ArrayList;

public class UpdateUnitsAction implements Action {
    private Integer gameID;
    private String playerID;
    private String where;//which territory to upgrade
    private ArrayList<ArrayList<Integer>> unitsToUpgrade;
    //{{oldlevel:0, leveltoupgrade: 1}, {1,2}, {0,1}}
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }
    public Integer getGameID() {
        return gameID;
    }

    public UpdateUnitsAction setGameID(Integer gameID) {
        this.gameID = gameID;
        return this;
    }

    public String getPlayerID(){
        return playerID;
    }

    public UpdateUnitsAction setPlayerID(String _playerID){
        playerID = _playerID;
        return this;
    }

    public ArrayList<ArrayList<Integer>> getUnitsToUpgrade(){
        return unitsToUpgrade;
    }

    public UpdateUnitsAction setUnitsToUpgrade(ArrayList<ArrayList<Integer>> _u){
        unitsToUpgrade = _u;
        return this;
    }

    public String getWhere(){return where;}

    public UpdateUnitsAction setWhere(String _where){
        where= _where;
        return this;
    }

}
