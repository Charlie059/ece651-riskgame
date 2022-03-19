package edu.duke.ece651.server;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import edu.duke.ece651.shared.ClientJSONParser;
import edu.duke.ece651.shared.Map;
import edu.duke.ece651.shared.Territory;

public abstract class ActionHandler {

  // ArrayList<Future<?>> futureList;
  ArrayList<ClientJSONParser> clientJSONParserList;
  Map map;
  HashMap<String, Territory> territoryList;

  public ActionHandler(ArrayList<Future<?>> futureList, Map map) throws ExecutionException, InterruptedException {
    this.map = map;
    this.clientJSONParserList = new ArrayList<ClientJSONParser>();
    for (int i = 0; i < futureList.size(); i++) {
      this.clientJSONParserList.add((ClientJSONParser) futureList.get(i).get());
    }
    this.territoryList = map.getTerritoryList();
  };

  protected abstract void doAction();

}
