package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class Territory {
  private String name;
  private int ownerId;
  private HashMap<Integer, ArrayList<Unit>> Units;// 6 level, num of units of 6 level's
  final ArrayList<Territory> neighbours;

  public Territory(String name, ArrayList<Territory> neighbourList) {
    this.name = name;
    this.ownerId = -1; // ID[0,1,2...]
    this.neighbours = neighbourList;
    this.Units = new HashMap<>();
  }

  // create an isolated territory
  public Territory(String name) {
    this.name = name;
    this.name = name;
    this.ownerId = -1;
    this.neighbours = new ArrayList<Territory>();
    this.Units = new HashMap<>();
  }

  void addNeighbour(Territory t) {
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

  public HashMap<Integer, ArrayList<Unit>> getUnits() {
    return Units;
  }

  /**
   * add one unit to the territory
   * 
   * @param u
   */
  public void addUnit(Unit u) {
    int currUnitLevel = u.getLevel();
    // if current level unit in Units does not exist
    // create this level unit and put it in Units
    if (Units.get(currUnitLevel) == null) {
      Units.put(currUnitLevel, new ArrayList<Unit>());
    }
    Units.get(currUnitLevel).add(u);

  }

  /**
   * add more than one units
   * 
   * @param numOfUnits: hashmap key: level, value: units# to add
   */
  public void addNumUnit(HashMap<Integer, Integer> numOfUnits) {

    // numOfUnits required initialize all levels requirement
    // some levels may have 0 adding requirments
    // i is level
    for (Integer i = 1; i <= numOfUnits.size(); i++) {
      Unit levelUnit = new Unit(i);
      // k is how many unit of level i
      for (Integer k = 0; k < numOfUnits.get(i); k++) {
        addUnit(levelUnit);
      }
    }
  }

  /**
   * remove one unit (head) from the territory
   * 
   * @param level
   */
  public Unit removeUnit(int level) {
    Unit u = null;
    if (!Units.get(level).isEmpty() && (!Units.get(level).isEmpty())) {
      u = Units.get(level).remove(0);
    }
    return u;
  }

  /**
   * remove more than one units
   * 
   * @param numOfUnits hashmap, key: level, value: units# to remove
   */
  public void removeNumUnit(HashMap<Integer, Integer> numOfUnits) {
    // i: curr level
    for (Integer i : numOfUnits.keySet()) {
      // k is how many unit of level i
      for (Integer k = 0; k < numOfUnits.get(i); k++) {
        removeUnit(i);
      }
    }
  }

  public void changeOwner(int player_id) {
    if (player_id != ownerId) {
      ownerId = player_id;
    }
  }

  // Convert <Integer, Integer> to <Integer, ArrayList<Unit>>
  public void setUnits(HashMap<Integer, Integer> units) {
    HashMap<Integer, ArrayList<Unit>> newUnits = new HashMap<>();
    for (int i = 1; i <= 6; i++) {
      ArrayList<Unit> unitsArr = new ArrayList<>();
      for (int j = 0; j < units.get(i); j++) {
        unitsArr.add(new Unit(i));
      }
      newUnits.put(i, unitsArr);
    }
    this.Units = newUnits;
  }
  
}
