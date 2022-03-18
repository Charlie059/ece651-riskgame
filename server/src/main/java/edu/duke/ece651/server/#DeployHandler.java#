package edu.duke.ece651.server;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Unit;

public class DeployHandler extends ActionHandler {

  public DeployHandler(ArrayList<Future<?>> futureList, Map map) throws ExecutionException, InterruptedException {
    super(futureList, map);
  }

  @Override
  public void doAction() {
    Integer level = 1;
    // For each player
    for (int i = 0; i < clientJSONParserList.size(); i++) {
      ArrayList<Action> deployActionList = clientJSONParserList.get(i).getDeploy();
      // For each deploy action
      for (int k = 0; k < deployActionList.size(); k++) {

        Action currentAction = deployActionList.get(k);

        // For the terriotory of this deployAction's working on territory, we trust
        // client should only send the player's working on it's own territory, thus we
        // set the owner to be itself to it's working on territory.
        territoryList.get(currentAction.getTo().getName()).changeOwner(clientJSONParserList.get(i).getPlayerID());

        // For the territory of this deployAction's to's TerritoryName, add number of
        // this deployAction's unitnumber of level 1 Unit()
        territoryList.get(deployActionList.get(k).getTo().getName()).addNumUnit(currentAction.getUnitNumber());

      }
    }
  }
}
