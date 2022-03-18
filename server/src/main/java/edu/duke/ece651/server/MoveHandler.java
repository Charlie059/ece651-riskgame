package edu.duke.ece651.server;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import edu.duke.ece651.shared.Action;
import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Territory;

public class MoveHandler extends ActionHandler {
  public MoveHandler(ArrayList<Future<?>> futureList, Map map) throws ExecutionException, InterruptedException {
    super(futureList, map);
  }

  @Override
  public void doAction() {
    Integer level = 1;
    for (int i = 0; i < clientJSONParserList.size(); i++) {
      ArrayList<Action> moveActionList = clientJSONParserList.get(i).getMove();
      for (int k = 0; k < moveActionList.size(); k++) {
        Action currentAction = moveActionList.get(k);
        Territory to = currentAction.getTo();
        Territory from = currentAction.getFrom();

        //map update from
        map.getTerritoryList().get(from.getName()).removeNumUnit(currentAction.getUnitNumber());

        //map update to
        map.getTerritoryList().get(to.getName()).addNumUnit(currentAction.getUnitNumber());
      }
    }
  }

}
