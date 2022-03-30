package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;

import java.util.HashMap;

public abstract class ActionChecker {

    protected HashMap<Integer, Game> gameHashMap;
    protected HashMap<String, Player> playerHashMap;

    public ActionChecker(HashMap<Integer, Game> gameHashMap, HashMap<String, Player> playerHashMap) {
        this.gameHashMap = gameHashMap;
        this.playerHashMap = playerHashMap;
    }

    abstract boolean doCheck();


}
