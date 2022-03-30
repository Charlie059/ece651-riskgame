package edu.duke.ece651.shared.Checker;

import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.Player;

import java.util.HashMap;

public class AuthentificationChecker extends ActionChecker {

    private String recvAccount;
    private String recvPassword;

    public AuthentificationChecker(HashMap<Integer, Game> gameHashMap, HashMap<String, Player> playerHashMap, String recvAccount, String recvPassword) {
        super(gameHashMap, playerHashMap);
        this.recvAccount = recvAccount;
        this.recvPassword = recvPassword;
    }

    @Override
    public boolean doCheck() {
        //Firstly check account exist in Database
        if (this.playerHashMap.containsKey(this.recvAccount)) {
            Player player = this.playerHashMap.get(this.recvAccount);
            //Secondly check account password is correct
            if (!player.getPassword().equals(this.recvPassword)) return false;
            else return true;
        } else return false;
    }
}
