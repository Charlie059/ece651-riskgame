package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;

public class CommitChecker extends ActionChecker{
    public CommitChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID, GameID gameID) {
        super(gameHashMap, accountHashMap,accountID);
    }

    @Override
    public boolean doCheck() {
        return true;
    }
}
