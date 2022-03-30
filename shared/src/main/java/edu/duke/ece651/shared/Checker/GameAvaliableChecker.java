package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;

import java.util.HashMap;

public class GameAvaliableChecker extends ActionChecker {


    public GameAvaliableChecker(HashMap<Integer, Game> gameHashMap, HashMap<String, Player> playerHashMap) {
        super(gameHashMap, playerHashMap);
    }

    @Override
    public boolean doCheck() {
        return false;
    }

}
