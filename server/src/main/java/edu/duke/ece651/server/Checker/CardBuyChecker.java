package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;

public class CardBuyChecker extends ActionChecker{

    private final Player player;
    private final Integer cardCost;
    public CardBuyChecker(
            GameHashMap gameHashMap,
            AccountHashMap accountHashMap,
            AccountID accountID,
            Player player,
            Integer cardCost
    ) {
        super(gameHashMap, accountHashMap, accountID);
        this.player = player;
        this.cardCost = cardCost;
    }

    @Override
    public String doCheck() {
        if (this.player.getPoints() < cardCost){
            this.errMessage = "CardBuy Error: player does not have enough points!";
        }
        return this.errMessage;
    }
}
