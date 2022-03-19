package edu.duke.ece651.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.*;

public class AttackHandler extends ActionHandler {

  public AttackHandler(ArrayList<Future<?>> futureList, Map map) throws ExecutionException, InterruptedException {
    super(futureList, map);
  }

  private class CombatResult {
    int playerID;
    HashMap<Integer, Integer> numOfUnits;
  }

  // from's ID combat with to's ID
  public CombatResult combat(Action currentAction, int playerID) {
    Territory to = currentAction.getTo();
    Territory from = currentAction.getFrom();
    int attacker = from.getOwner();
    int defender = to.getOwner();

    HashMap<Integer, Integer> attackerUnits = currentAction.getUnitNumber();

    HashMap<Integer, Integer> defenderUnits = new HashMap<>();

    HashMap<Integer, ArrayList<Unit>> defenderUnits_UnitsArr = to.getUnits();

    // TODO the combat only do compare at this moment
    // TODO implement dice iteration

    // Convert <Integer, ArrayList<Units>> to <Integer, Integer>
    for (int i = 1; i <= 6; i++) {
      defenderUnits.put(i, defenderUnits_UnitsArr.get(i) == null ? 0 : defenderUnits_UnitsArr.get(i).size());
    }

    // TODO COMBAT RESOLUTION

    // current combat resolution
    Integer level = 1;
    CombatResult combatResult = new CombatResult();
    // only compare level's units
    // numOfUits(Action), to.numOfUnits
    // larger one win the territory
    Random random = new Random();
    //int attackerminNumUnits = 1;
    //int attackermaxNumUnits =
    //  int defenderminNumUnits =1;
    //  int defendermaxNumUnits = 
    if (attackerUnits.get(level) >= defenderUnits.get(level)) {
      combatResult.playerID = playerID;//from.getOwner();
      combatResult.numOfUnits = attackerUnits;
    } else {
      combatResult.playerID = to.getOwner();
      combatResult.numOfUnits = defenderUnits;
    }

    return combatResult;
  }

  @Override
  public void doAction() {
    Integer level = 1;

    // Update from
    for (int i = 0; i < clientJSONParserList.size(); i++) {
      ArrayList<Action> attackActionList = clientJSONParserList.get(i).getAttack();

      // Now defender is updated
      // Order units do not participate defending
      for (int k = 0; k < attackActionList.size(); k++) {
        Action currentAction = attackActionList.get(k);
        Territory from = currentAction.getFrom();

        // map update from
        map.getTerritoryList().get(from.getName()).removeNumUnit(currentAction.getUnitNumber());
      }

    }

    // Update to
    for (int i = 0; i < clientJSONParserList.size(); i++) {
      int playerID = i+1;
      ArrayList<Action> attackActionList = clientJSONParserList.get(i).getAttack();

      for (int k = 0; k < attackActionList.size(); k++) {
        Action currentAction = attackActionList.get(k);
        Territory to = currentAction.getTo();
        Territory from = currentAction.getFrom();

       
        // combat at to
        CombatResult combatResult = combat(currentAction, playerID);

        // TODO 20 side dice resolution

        // currently the more sides have the territory, and they all alive

        // Set the combat Result the combat places
        to.setOwner(combatResult.playerID);
        // to.getUnits() =
        to.setUnits(combatResult.numOfUnits);

      }
    }
  }
}
