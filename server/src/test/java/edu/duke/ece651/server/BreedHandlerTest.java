package edu.duke.ece651.server;

import java.util.ArrayList;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.shared.Map;

public class BreedHandlerTest {
  @Test
  public void test_BreedHandlerTest() {
    ArrayList<Future<?>> futureList = new ArrayList<>();
    Map map = new Map(3);
    try {
      BreedHandler breedHandler = new BreedHandler(futureList, map);
      breedHandler.doAction();
    } catch (Exception e) {

    }
  }

}
