package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

public class UpgradeTechChecker extends ActionChecker{
    private Integer nextLevel;
    private Integer currTechResource;
    private Boolean isTechUpgraded;
    private int cost;
    private Player player;
    private GameID gameID;
    public UpgradeTechChecker(AccountID accountID,
                              GameHashMap gameHashMap,
                              AccountHashMap accountHashMap,
                              Boolean isTechUpgraded,
                              GameID gameID,
                              Integer cost
                              ){
        super( gameHashMap,accountHashMap ,accountID);
        this.isTechUpgraded = isTechUpgraded;
        this.gameID = gameID;
        Game game = this.gameHashMap.get(this.gameID);
        this.player = game.getPlayerHashMap().get(this.accountID);
        this.nextLevel = this.player.getCurrTechLevel()+1;
        this.currTechResource = this.player.getTechResource();
        this.cost = cost;
    }


    @Override
    public boolean doCheck(){
        //valid iff nextLevel <= 6 and player has enough tech resource
        if (!this.isTechUpgraded && this.nextLevel <= 6 && this.cost <= this.currTechResource) {
            return true;
        }
        return false;
    }

    public int getCost(){return cost;}

}
