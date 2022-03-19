package edu.duke.ece651.server;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Unit;

public class BreedHandler extends ActionHandler {

  public BreedHandler(ArrayList<Future<?>> futureList, Map map) throws ExecutionException, InterruptedException {
    super(futureList, map);
  }

  @Override
  public void doAction() {
    for (String territoryName : territoryList.keySet()) {
      territoryList.get(territoryName).addUnit(new Unit(1));
    }
  }

}
