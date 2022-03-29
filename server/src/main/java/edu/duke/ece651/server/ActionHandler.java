package edu.duke.ece651.server;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import edu.duke.ece651.shared.Map;

public abstract class ActionHandler {
    protected abstract void doAction();

    // ArrayList<Future<?>> futureList;
  /*
  ArrayList<ClientJSONParser> clientJSONParserList;
  Map map;
  HashMap<String, Territory> territoryList;

  public ActionHandler(ArrayList<Future<?>> futureList, Map map) throws ExecutionException, InterruptedException {

  };

  protected abstract void doAction();
*/
}
