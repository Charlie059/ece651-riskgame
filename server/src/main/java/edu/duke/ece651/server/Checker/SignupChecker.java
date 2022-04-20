package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;

/**
 * Check Signup Account should not exist
 * Exist: false
 * Not Exist: true
 */
public class SignupChecker extends ActionChecker{
    private AccountID recvAccount;
    public SignupChecker(AccountID accountID, GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID recvAccount) {
        super(gameHashMap, accountHashMap,accountID);
        this.recvAccount = recvAccount;
    }

    @Override
    public String doCheck() {
        if (!this.accountHashMap.containsKey(this.recvAccount)) {
            //return true;
            this.errMessage = null;
            return this.errMessage;
        }
        else{
            //return false;
            this.errMessage = "Signup Error: Account does not exist!";
            return errMessage;
        }
    }
}
