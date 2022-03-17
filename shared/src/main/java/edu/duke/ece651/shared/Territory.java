package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class Territory {
    private String name;
    private int ownerId;
    private HashMap<Integer, ArrayList<Unit>> Units;//level, num of units of this level
    final ArrayList<Territory> neighbours; 

    // create an isolated territory
    public Territory(String name) {
        this.name = name;
        this.name = name;
        this.ownerId = -1;  // ID[1,2...]
        this.neighbours = new ArrayList<Territory>();
        this.Units = new HashMap<>();
    }

    void addNeighbour(Territory t){
        neighbours.add(t);
    }

    public String getName() {
        return name;
    }

    public void setOwner(int playerId) {
        ownerId = playerId;
    }

    public int getOwner() {
        return ownerId;
    }

    public HashMap<Integer, ArrayList<Unit>> getUnits(){
        return Units;
    }

    /**
     * add one unit to the territory
     * @param u
     */
    public void addUnit(Unit u){
        int currUnitLevel = u.getLevel();
        if (Units.get(currUnitLevel) == null) {
            Units.put(currUnitLevel, new ArrayList<Unit>());
        }
        Units.get(currUnitLevel).add(u);
        
    }
    /**
     * remove one unit (head) from the territory
     * @param level
     */
    public Unit removeUnit(int level){
        Unit u;
        if ( !Units.get(level).isEmpty()) {
            u = Units.get(level).remove(0);
        }
        else{
            u = new Unit();
        }
        return u;
    }

    public void changeOwner(int player_id){
        if (player_id != ownerId){
            ownerId = player_id;
        }
    }
}