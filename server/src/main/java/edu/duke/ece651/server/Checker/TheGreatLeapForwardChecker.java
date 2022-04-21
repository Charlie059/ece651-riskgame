package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;

public class TheGreatLeapForwardChecker extends ActionChecker{
    private final Player player;
    private final String territoryName;

    public TheGreatLeapForwardChecker(
            GameHashMap gameHashMap,
            AccountHashMap accountHashMap,
              AccountID accountID,
              Player player,
              String territoryName
    ) {
        super(gameHashMap, accountHashMap, accountID);
        this.player = player;
        this.territoryName = territoryName;
    }

    @Override
    public String doCheck() {
        //Check Player has the card
        if(!player.haveCard(new CardType().getGreatLeapForward().get(0))){
            //return false;
            this.errMessage = "GreatLeapForward Error: player does have any GreatLeapForward Card!";
            return this.errMessage;
        }
        //check if player has the territory
        if (this.player.getMyTerritories().containsKey(this.territoryName)){
            this.errMessage = null;
        }
        else{
            this.errMessage = "GreatLeapForward Error: player does not have the territory!";
        }
        return this.errMessage;
    }
}
