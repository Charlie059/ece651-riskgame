package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;

public abstract class ActionChecker {

    protected volatile GameHashMap gameHashMap;
    protected volatile AccountHashMap accountHashMap;
    protected volatile AccountID accountID;
    protected String errMessage;

    public ActionChecker(GameHashMap gameHashMap, AccountHashMap accountHashMap, AccountID accountID) {
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        this.accountID = accountID;
        this.errMessage = null;
    }

    public abstract String doCheck();

    public String getErrMessage(){
        return this.errMessage;
    }

}
