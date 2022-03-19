package edu.duke.ece651.server;

import java.util.ArrayList;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.Map;

public class DeployHandlerTest {
  @Test
  public void test_DeployHandlerTest() {
    ArrayList<Future<?>> futureList = new ArrayList<>();
    Map map = new Map(3);
    try {
      DeployHandler deployHandler= new DeployHandler(futureList, map);
      deployHandler.doAction();
      
    } catch (Exception e) {

    }
  }

}
