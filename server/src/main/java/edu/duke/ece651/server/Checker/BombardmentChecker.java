package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Territory;

public class BombardmentChecker extends ActionChecker{
    private final Map map;
    private final String enemyTerritory;
    private final Player player;
    public BombardmentChecker(
            GameHashMap gameHashMap,
            AccountHashMap accountHashMap,
            AccountID accountID,
            Map map,
            String enemyTerritory,
            Player player) {
        super(gameHashMap, accountHashMap, accountID);
        this.map = map;
        this.enemyTerritory = enemyTerritory;
        this.player = player;
    }


    @Override
    public String doCheck() {
        //Check Player has the card
        if(!player.haveCard(new CardType().getBombardment().get(0))){
            //return false;
            this.errMessage = "Bombardment Error: player does have any Bombardment Card!";
            return this.errMessage;
        }
        //check if territory exist

        if (this.map.getTerritoryList().containsKey(this.enemyTerritory)){
            //check if territory belongs to enemy
            Territory enemyTerr = this.map.getTerritoryList().get(this.enemyTerritory);
            if (!enemyTerr.getOwnerId().equals(this.accountID)){
                this.errMessage = null;
                return this.errMessage;
            }
            else{
                //false case
                this.errMessage = "Bombardment Error: cannot bomb your own territory!";
            }
        }
        else{
            //false case
            this.errMessage = "Bombardment Error: territory does not exist!";
        }
        return this.errMessage;
    }
}
