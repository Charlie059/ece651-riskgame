package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Player;
import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;

public class GodBeWithUChecker extends ActionChecker{

    private final Player player;

    public GodBeWithUChecker(
            GameHashMap gameHashMap,
                             AccountHashMap accountHashMap,
                             AccountID accountID,
                             Player player
                            ) {
        super(gameHashMap, accountHashMap, accountID);
        this.player = player;
    }

    @Override
    public String doCheck() {
        //Check Player has the card
        if(!player.haveCard(new CardType().getGodBeWithYou().get(0))){
            //false case;
            this.errMessage = "GodBeWithYou Error: player does have any GodBeWithYou Card!";
        }
        else{
            //true case
            this.errMessage = null;
        }
        return this.errMessage;
    }
}
