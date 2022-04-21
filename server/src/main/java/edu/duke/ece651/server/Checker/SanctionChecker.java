package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Territory;

public class SanctionChecker extends ActionChecker{
    private final AccountID enemyID;
    private final Player player;
    private final GameID gameID;
    public SanctionChecker(
            GameHashMap gameHashMap,
            AccountHashMap accountHashMap,
            AccountID accountID,
            AccountID enemyID,
            Player player,
            GameID gameID
        ) {
        super(gameHashMap, accountHashMap, accountID);
        this.enemyID = enemyID;
        this.player = player;
        this.gameID = gameID;
    }


    @Override
    public String doCheck() {
        //Check Player has the card
        if(!player.haveCard(new CardType().getSanction().get(0))){
            //return false;
            this.errMessage = "Sanction Error: player does have any Sanction Card!";
            return this.errMessage;
        }
        //check if enemy PLAYER exists and check if enemyPlayer is myself
        if (this.gameHashMap.get(this.gameID).getPlayerHashMap().containsKey(this.enemyID)
                && (!this.enemyID.equals(this.accountID))){
            //Player enemyPlayer = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.enemyID);
            this.errMessage = null;
            return this.errMessage;
        }
        else{
            //false case
            this.errMessage = "Sanction Error: invalid enemy accountID!";
        }
        return this.errMessage;
    }
}
