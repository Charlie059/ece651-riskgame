package edu.duke.ece651.client;

public class CheckerHostNumPlayer implements ClientRuleChecker {
  @Override
  public String doCheck(String userInput) {
    int num_player;
    try {
      num_player = Integer.parseInt(userInput);
    } catch (NumberFormatException e) {
      return "Invalid Input! Please Entering a number\n";
    }
    if (num_player <= 1 || num_player > 16) {
      return "Invalid Input! Please Entering number between (1, 16)\n";
    }
    return "";
  }

}
