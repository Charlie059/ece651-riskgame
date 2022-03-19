package edu.duke.ece651.client;

import edu.duke.ece651.shared.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {

  private int id; // player id
  private int numOfPlayers;
  private BufferedReader inputReader;
  private PrintStream out;
  private boolean isFirstRound; // deploy
  private boolean isLose; // commit
  private boolean isGameOver;
  private boolean isWon;
  private HashMap<String, Territory> myTerritories;// all territories of the player
  private Map wholeMap;
  private HashMap<Integer, Integer> totalDeployment; // totalUnits: key: level, value: num of units
  private ArrayList<Action> ActionList;// list of actions
  public MapView myMapTextView;
  private ServerJSONParser ServerJSON_parser;
  private String recvJSON = "{}";
  private boolean isLoserAsked;
  private boolean isNotDisplay;

  /**
   * Constructor
   * 
   * @param _id
   * @param _numOfPlayer
   * @param _in
   * @param _out
   */
  public Player(int _id, int _numOfPlayer, BufferedReader _in, PrintStream _out) {
    id = _id;
    numOfPlayers = _numOfPlayer;
    InputStreamReader myReader = new InputStreamReader(System.in);
    isFirstRound = true;
    isLose = false;
    isGameOver = false;
    isWon = false;
    myTerritories = new HashMap<>();
    wholeMap = new Map(numOfPlayers);
    setInitialTerritories(myTerritories, wholeMap);
    initializeDeployment();
    ActionList = new ArrayList<>();
    inputReader = _in;
    out = _out;
    this.myMapTextView = new MapTextView(numOfPlayers, out);
    this.isLoserAsked = false;
    this.isNotDisplay = false;
  }

  /* getters */
  public int getId() {
    return id;
  }

  public int getNumOfPlayers() {
    return numOfPlayers;
  }

  public BufferedReader getInputReader() {
    return inputReader;
  }

  public PrintStream getOut() {
    return out;
  }

  public HashMap<String, Territory> getMyTerritories() {
    return myTerritories;
  }

  public HashMap<Integer, Integer> getTotalDeployment() {
    return totalDeployment;
  }

  public Map getWholeMap() {
    return wholeMap;
  }

  public ArrayList<Action> getActionList() {
    return ActionList;
  }

  public boolean getIsLose() {
    return isLose;
  }

  public boolean getIsGameOver() {
    return isGameOver;
  }

  public boolean getIsWon() {
    return isWon;
  }

  public boolean getIsFirstRound() {
    return isFirstRound;
  }

  public ServerJSONParser getServerJSON_parser() {
    return ServerJSON_parser;
  }

  public String getRecvJSON() {
    return recvJSON;
  }

  /* setters */
  public void setIsLose(boolean b) {
    isLose = b;
  }

  public void setIsGameOver(boolean b) {
    isGameOver = b;
  }

  public void setIsWon(boolean b) {
    isWon = b;
  }

  public void setIsFirstRound(boolean b) {
    isFirstRound = b;
  }

  public void setServerJSON_parser(String src) {
    ServerJSON_parser = new ServerJSONParser(src, wholeMap, id, myTerritories);
  }

  public void setRecvJSON(String src) {
    recvJSON = src;
  }

  /**
   * set player's initial territory based on player's id and wholeMap's groups
   * 
   * @param _territories
   * @param _map
   */
  public void setInitialTerritories(HashMap<String, Territory> _territories, Map _map) {
    for (int i = 0; i < _map.getGroups().size(); i++) {
      if (i + 1 == id) {
        for (int j = 0; j < _map.getGroups().get(i).size(); j++) {
          String currTerrName = _map.getGroups().get(i).get(j);
          Territory curr_t = _map.getTerritoryList().get(currTerrName);
          curr_t.setOwner(id);
          _territories.put(currTerrName, curr_t);
        }
        break;
      }
    }
  }

  /**
   * initialize player's total units for deployment totalDeployment: 3 *
   * territory# level-1 units
   */
  public void initializeDeployment() {
    totalDeployment = new HashMap<>();
    totalDeployment.put(1, myTerritories.size() * 3);// add 3 * territory# units to level 1
  }

  /**
   * ask player to select an action for each round
   * 
   * @param inputReader
   * @param out
   * @return
   */
  public void playerMakeChoice(BufferedReader inputReader, PrintStream out) throws IOException {
    String choice;
    boolean isChoiceValid = false;
    while (true) {
      do {
        out.println("You are Player " + id + ", what would you like to do?");
        out.println("(M)ove\n(A)ttack\n(D)one\n");
        choice = inputReader.readLine();
        if (choice.equals("M") || choice.equals("m") || choice.equals("A") || choice.equals("a") || choice.equals("D")
            || choice.equals("d")) {
          isChoiceValid = true;
        } else {
          out.println("Input Error: please enter a valid choice\n(M)ove\n(A)ttack\n(D)one\n");
        }
      } while (!isChoiceValid);

      choice = choice.toUpperCase();
      if (choice.equals("M")) {
        // TODO: MOVE
        playerDoMove(inputReader, out);
        wholeMap.displayMap(myMapTextView);
      } else if (choice.equals("A")) {
        // TODO: Attack
        playerDoAttack(inputReader, out);
      } else {
        // TODO: Commit
        break;
      }
    }
    return;
  }

  /**
   * receive player's inputs for deploy action
   * 
   * @param inputReader
   * @param out
   * @throws IOException
   * @throws Exception
   */
  public void playerDoDeploy(BufferedReader inputReader, PrintStream out) throws IOException, Exception {
    // player set the number of units
    out.println("Player " + id + ": you have " + this.totalDeployment.get(1) + " level-1 units");
    out.println("You can deploy them in your " + this.myTerritories.size() + " territories:");
    out.print("\t");
    for (String t : myTerritories.keySet()) {
      out.print(t + "\t");
    }
    out.print("\n");
    Integer unitNum;
    Integer level = 1;
    boolean isNumUnitsValid = false;
    while (true) {
      if (totalDeployment.get(level) == 0) {
        break;
      }
      for (String terrName : this.myTerritories.keySet()) {
        if (totalDeployment.get(level) == 0) {
          break;
        }
        do {
          out.println("Player " + id + ", how many number of level-1 units do you want to deploy in territory "
              + terrName + "?");
          int num = 0;
          unitNum = num;
          try {
            num = Integer.parseInt(inputReader.readLine());
            unitNum = num;
            if (totalDeployment.get(level) >= unitNum && unitNum >= 0) {
              isNumUnitsValid = true;
              // totalDeployment.replace(level, totalDeployment.get(level) - unitNum);
              out.println(
                  "you have deployed " + unitNum + " level-" + level + " units to " + "Territory " + terrName + "!");
            } else {
              out.println("Deployment Error: Number of level-" + level
                  + " units cannot be less than o or exceed the total " + "number of deployment");
            }

          } catch (NumberFormatException e) {
            isNumUnitsValid = false;
            out.println("Deployment Error: " + e.getMessage());
          }
        } while (!isNumUnitsValid);
        deploy(unitNum, terrName);
      }
    }
  }

  /**
   * deploy units to one of the player's territory
   * 
   * @param numOfDeployedUnits
   * @param to_name
   * @return boolean: whether deployed successfully
   */
  public boolean deploy(int numOfDeployedUnits, String to_name) {

    if (numOfDeployedUnits <= totalDeployment.get(1)) { // get level 1
      Territory to = myTerritories.get(to_name);
      HashMap<Integer, Integer> unitNumber = new HashMap<>();
      unitNumber.put(1, numOfDeployedUnits);
      try {
        DeployAction deploy_action = new DeployAction(to, unitNumber);
        deploy_action.doAction();
        totalDeployment.replace(1, totalDeployment.get(1) - numOfDeployedUnits);
        ActionList.add(deploy_action);
      } catch (Exception excep) {
        out.println("Deployment Error: " + excep.getMessage());
        return false;
      }
      return true;
    } else {
      System.out.println("Deployment Error: deployed unit number cannot exceed initial deployment number");
      return false;
    }
  }

  /**
   * helper function: check if input territory name matches names in myTerritories
   * 
   * @param terrName
   * @param Territories
   * @return
   */
  public boolean isTerrNameMatch(String terrName, HashMap<String, Territory> Territories) {
    if (Territories.get(terrName) != null) {
      return true;
    }
    return false;
  }

  /**
   * helper function: check if input territory name matches names in myTerritories
   * and the whole map
   * 
   * @param terrName
   * @param selfTerritories
   * @param otherTerritories
   * @return
   */
  public boolean isTerrNameMatchForAttack(String terrName, HashMap<String, Territory> selfTerritories,
      HashMap<String, Territory> otherTerritories) {
    if (isTerrNameMatch(terrName, selfTerritories)) {
      return false;
    }
    if (otherTerritories.get(terrName) != null) {
      return true;
    }
    return false;
  }

  /**
   * player inputs parameters of move action
   * 
   * @param inputReader
   * @param out
   * @throws IOException
   */
  public void playerDoMove(BufferedReader inputReader, PrintStream out) throws IOException {
    String from_name, to_name;
    HashMap<Integer, Integer> moveUnits = new HashMap<>();
    boolean isFromValid = false;
    boolean isLevelValid = false;
    boolean isNumUnitsValid = false;
    boolean isToValid = true;
    boolean isUpdateValid = true;
    // player chooses from_name
    do {
      do {
        out.println("Player " + id + ", enter the name of the territory you want to move units from?");
        for (String terrName : myTerritories.keySet()) {
          out.println("\t" + terrName);
        }
        from_name = inputReader.readLine();
        // check if from_name exists
        isFromValid = isTerrNameMatch(from_name, myTerritories);
        if (isFromValid == false) {
          out.println("Move Error: Territory name " + from_name + " not found! Please enter a valid name!");
        }
      } while (!isFromValid);
      // player chooses moveUnits
      Integer level = 0;
      Integer unitNum;
      do {
        out.println("Player " + id + ", which level of unit do you want to move?");
        for (Integer i : myTerritories.get(from_name).getUnits().keySet()) {
          out.println(
              "\t(" + i + ") Level-" + i + " has " + myTerritories.get(from_name).getUnits().get(i).size() + " units.");
        }
        try {
          int l = Integer.parseInt(inputReader.readLine());
          level = l;
          // check if level exists in totalDeployment
          isLevelValid = myTerritories.get(from_name).getUnits().get(level) == null ? false : true;
          if (isLevelValid == false) {
            out.println("Move Error: Level-" + level + " not found! Please enter a valid level!");
          }
        } catch (NumberFormatException e) {
          isLevelValid = false;
          out.println("Move Error: " + e.getMessage());
        }
      } while (!isLevelValid);
      // player set the number of units
      do {
        out.println("Player " + id + ", how many number of level-" + level + " units do you want to move?");
        int num = 0;
        unitNum = num;
        try {
          num = Integer.parseInt(inputReader.readLine());
          unitNum = num;
          if (myTerritories.get(from_name).getUnits().get(level).size() >= unitNum && unitNum > 0) {
            isNumUnitsValid = true;
          } else {
            out.println("Move Error: Number of level-" + level + " units should be larger than 0 and less than the max "
                + "number of that units in Territory " + from_name + ", but got " + unitNum + "!");
          }
        } catch (NumberFormatException e) {
          isNumUnitsValid = false;
          out.println("Move Error: " + e.getMessage());
        }
      } while (!isNumUnitsValid);
      moveUnits.put(level, unitNum);
      // player set territory he wants to move to
      do {
        out.println("Player " + id + ", enter the name of the territory you want to move units to:");
        for (String terrName : myTerritories.keySet()) {
          if (terrName != from_name) {
            out.println("\t" + terrName);
          }
        }
        to_name = inputReader.readLine();
        // check if from_name exists
        isToValid = isTerrNameMatch(to_name, myTerritories);
        if (isToValid == false) {
          out.println("Move Error: Territory name " + to_name + " not found! Please enter a valid name!");
        }
      } while (!isToValid);

      isUpdateValid = updateMove(moveUnits, from_name, to_name, wholeMap, id);
    } while (!isUpdateValid);
    move(moveUnits, from_name, to_name);
  }

  /**
   * temporally update player's move action to player's own map
   * 
   * @param moveUnits
   * @param from_name
   * @param to_name
   * @param _map
   * @param playerID
   * @return
   */
  public boolean updateMove(HashMap<Integer, Integer> moveUnits, String from_name, String to_name, Map _map,
      int playerID) {
    // rule checker
    MoveChecker moveChecker = new MoveChecker(_map, moveUnits, from_name, to_name, id);
    if (moveChecker.doCheck(id, from_name, to_name) != null) {
      out.println(moveChecker.doCheck(id, from_name, to_name));
      return false;
    } else {
      _map.getTerritoryList().get(from_name).removeNumUnit(moveUnits);
      _map.getTerritoryList().get(to_name).addNumUnit(moveUnits);
      return true;
    }
  }

  /**
   * move units from one territory to another territory
   * 
   * @param moveUnits
   * @param from_name
   * @param to_name
   * @return
   */
  public boolean move(HashMap<Integer, Integer> moveUnits, String from_name, String to_name) {
    try {
      Territory from = myTerritories.get(from_name);
      Territory to = myTerritories.get(to_name);
      MoveAction move_action = new MoveAction(from, to, moveUnits);
      move_action.doAction();
      ActionList.add(move_action);
    } catch (Exception excep) {
      System.out.println("Move Error: " + excep.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Receive player's inputs for attack action
   * 
   * @param inputReader
   * @param out
   * @throws IOException
   */
  public void playerDoAttack(BufferedReader inputReader, PrintStream out) throws IOException {
    String from_name, to_name;
    HashMap<Integer, Integer> attackUnits = new HashMap<>();
    boolean isFromValid = false;
    boolean isLevelValid = false;
    boolean isNumUnitsValid = false;
    boolean isToValid = true;
    boolean isUpdateValid = true;
    // player chooses to_name
    do {
      do {
        out.println("Player " + id + ", enter the name of the territory you want to send units from?");
        for (String terrName : myTerritories.keySet()) {
          out.println("\t" + terrName);
        }
        from_name = inputReader.readLine();
        // check if from_name exists
        isFromValid = isTerrNameMatch(from_name, myTerritories);
        if (isFromValid == false) {
          out.println("Attack Error: Territory name " + from_name + " not found! Please enter a valid name!");
        }
      } while (!isFromValid);
      // player chooses level of attackUnits
      Integer level;
      Integer unitNum;
      do {
        out.println("Player " + id + ", which level of unit do you want to send?");
        out.println("Territory " + from_name + ":");
        for (Integer i : myTerritories.get(from_name).getUnits().keySet()) {
          out.println(
              "\t(" + i + ") Level-" + i + " has " + myTerritories.get(from_name).getUnits().get(i).size() + " units.");
          out.println();
        }
        int l = 0;
        level = l;
        try {
          l = Integer.parseInt(inputReader.readLine());
          level = l;
          // check if level exists in totalDeployment
          isLevelValid = myTerritories.get(from_name).getUnits().get(level) == null ? false : true;
          if (isLevelValid == false) {
            out.println("Attack Error: Level-" + level + " not found: please enter a valid level!");
          }
        } catch (NumberFormatException e) {
          isLevelValid = false;
          out.println("Attack Error: " + e.getMessage());
        }
      } while (!isLevelValid);
      // player set the number of attack units
      do {
        out.println("Player " + id + ", how many number of level-" + level + "units do you want to send?");
        int num = 0;
        unitNum = num;
        try {
          num = Integer.parseInt(inputReader.readLine());
          unitNum = num;
          if (myTerritories.get(from_name).getUnits().get(level).size() >= unitNum && unitNum > 0) {
            isNumUnitsValid = true;
          } else {
            out.println("Attack Error: Number of level-" + level + " units should be larger than 0 and less than the "
                + "max unit number in territory " + from_name + ", but got " + unitNum + "!");
          }
        } catch (NumberFormatException e) {
          out.println("Attack Error: " + e.getMessage());
          isNumUnitsValid = false;
        }
      } while (!isNumUnitsValid);
      attackUnits.put(level, unitNum);
      // player set territory he wants to attack
      do {
        out.println("Player " + id + ", enter the name of the territory you want to attack:");
        for (String terrName : wholeMap.getTerritoryList().keySet()) {
          // should not display player's own territories
          if (wholeMap.getTerritoryList().get(terrName).getOwner() != this.id) {
            out.println(wholeMap.getTerritoryList().get(terrName).getName() + ": ");
            out.println("\tOwner: player " + wholeMap.getTerritoryList().get(terrName).getOwner());
            out.println("\tUnits:");
            for (Integer i : wholeMap.getTerritoryList().get(terrName).getUnits().keySet()) {
              out.println(
                  "\t\tLevel-" + i + " Units: " + wholeMap.getTerritoryList().get(terrName).getUnits().get(i).size());
            }
            out.println();
          }
        }
        to_name = inputReader.readLine();
        // check if from_name exists
        isToValid = isTerrNameMatchForAttack(to_name, myTerritories, wholeMap.getTerritoryList());
        if (isToValid == false) {
          out.println(
              "Attack Error: Territory name " + "\"" + to_name + "\"" + " not found! Please enter a valid name!");
        }
      } while (!isToValid);

      isUpdateValid = checkAttack(attackUnits, from_name, to_name, wholeMap, id);
    } while (!isUpdateValid);
    attack(attackUnits, from_name, to_name);
  }

  /**
   * check if attack is valid: check if ownerships are correct and if two
   * territories are adjacent player should make a new attack command if the
   * current attack is invalid
   * 
   * @param attackUnits
   * @param from_name
   * @param to_name
   * @param wholeMap
   * @param id
   * @return
   */
  public boolean checkAttack(HashMap<Integer, Integer> attackUnits, String from_name, String to_name, Map wholeMap,
      int id) {
    // rule checker
    AttackChecker attackChecker = new AttackChecker(wholeMap, attackUnits, from_name, to_name, id);
    if (attackChecker.doCheck(id, from_name, to_name) != null) {
      out.println(attackChecker.doCheck(id, from_name, to_name));
      return false;
    } else {
      return true;
    }
  }

  /**
   * send player's units to an adjacent territory controlled by a different
   * player, in an attempt to gain control over that territory.
   * 
   * @param attackUnits
   * @param from_name
   * @param to_name
   * @return
   */
  public boolean attack(HashMap<Integer, Integer> attackUnits, String from_name, String to_name) {
    try {
      Territory from = wholeMap.getTerritoryList().get(from_name);
      Territory to = wholeMap.getTerritoryList().get(to_name);
      AttackAction attack_action = new AttackAction(from, to, attackUnits);
      attack_action.doAction();
      ActionList.add(attack_action);
    } catch (Exception excep) {
      System.out.println("Attack Error: " + excep.getMessage());
      return false;
    }
    return true;
  }

  /**
   * check if player loses all her territories if so, set isLose as true
   * 
   * @return
   */
  public boolean checkLose() {
    // if lose
    if (myTerritories.isEmpty()) {
      isLose = true;
      // check if gameover
      checkGameOver();
      return true;
    }
    return false;
  }

  /**
   * check if player wins A player wins when she controls all territories in the
   * game.
   * 
   * @return
   */
  public boolean checkWin() {
    // if win
    if (myTerritories.size() == wholeMap.getTerritoryList().size()) {
      isWon = true;
      isGameOver = true;
      return true;
    }
    return false;
  }

  /**
   * if all territories in map are controlled by one player, set isGameOver =
   * true;
   * 
   * @return
   */
  public boolean checkGameOver() {
    int testID = -1;
    int ind = 0;
    for (String t : wholeMap.getTerritoryList().keySet()) {
      if (ind == 0) {
        testID = wholeMap.getTerritoryList().get(t).getOwner();
        ind++;
        continue;
      }
      if (testID != wholeMap.getTerritoryList().get(t).getOwner()) {
        return false;
      }
    }
    isGameOver = true;
    return true;
  }

  public void updateWinOrLose() {
    if (!checkLose()) {
      // if player does not lose, check if she wins
      checkWin();
    }
  }

  /**
   * player plays one round
   * 
   * @throws Exception
   */
  // TODO why play one round is using while
  public void playOneRound() throws Exception {
    ActionList.clear();
    // receive serverJSON from client
    setServerJSON_parser(recvJSON);
    // update map based on recv serverJSON
    ServerJSON_parser.updateJSON(recvJSON);

    if (!isNotDisplay) {
      // display map
      wholeMap.displayMap(myMapTextView);
    }
    // check Win or Lose
    updateWinOrLose();
    // Deploy Round
    if (this.isFirstRound) {
      // TODO: DEPLOY
      playerDoDeploy(inputReader, out);
      this.isFirstRound = false;
      return;
    }
    // if not in the first round (deploy round)
    if (!this.isGameOver) {
      if (!this.isLose) {
        playerMakeChoice(inputReader, out);
        return;
      } else { // if player lose, auto commit the action and send to server

        if (this.isLoserAsked == false) {
          out.println("Continue to Watch (press C) or Quit (press anything else!)");
          String C = inputReader.readLine();
          if (!C.equals("C")) {
            this.isNotDisplay = true;
          }
          this.isLoserAsked = true;
        }
        return;
      }
    }
    // if gameOver
    if (this.isWon) {
      // TODO: let player know she wins
      out.println("Player " + id + ", You WIN!");
    } else {
      // TODO: let player know she loses
      out.println("Player " + id + ", You LOSE!");
    }
  }
}
