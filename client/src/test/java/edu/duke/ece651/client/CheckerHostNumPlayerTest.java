package edu.duke.ece651.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CheckerHostNumPlayerTest {
  @Test
  public void test_CheckerHostNumPlayer() {
    CheckerHostNumPlayer mychecker = new CheckerHostNumPlayer();
    String userInput = "abc";
    // assertThrows(NumberFormatException.class,()->mychecker.doCheck(userInput));
    assertEquals("Invalid Input! Please Entering a number\n", mychecker.doCheck(userInput));
    userInput = "-1";
    assertEquals("Invalid Input! Please Entering number between (1, 16)\n", mychecker.doCheck(userInput));
    userInput = "17";
    assertEquals("Invalid Input! Please Entering number between (1, 16)\n", mychecker.doCheck(userInput));
    userInput = "5";
    assertEquals("", mychecker.doCheck(userInput));

  }

}
