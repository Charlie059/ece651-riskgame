package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;

public class LoginChecker extends ActionChecker{
    private AccountID recvAccount;
    private String password;
    public LoginChecker(AccountID accountID, GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID recvAccount, String password) {
        super(gameHashMap, accountHashMap, accountID);
        this.recvAccount = recvAccount;
        this.password = password;
    }

    /**
     * Check the account exist and check if password match
     * @return true if pass
     */
    @Override
    public boolean doCheck() {
        // Check if input account exist
        if (this.accountHashMap.containsKey(this.recvAccount)) {
            // Check if password match
            if(this.accountHashMap.get(this.recvAccount).getPassword().equals(password)) return true;
            else return false;
        } else return false;
    }
}
