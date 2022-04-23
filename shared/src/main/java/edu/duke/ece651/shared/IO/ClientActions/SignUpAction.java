package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;

public class SignUpAction implements Action {
    private AccountID accountID;
    private String password;

    /**
     * Constructor of SignUpAction
     * @param accountID
     * @param password
     */
    public SignUpAction(AccountID accountID, String password){
        this.accountID = accountID;
        this.password = password;
    }

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public AccountID getAccount() {
        return this.accountID;
    }

    public Action setAccount(AccountID accountID) {
        this.accountID = accountID;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Action setPassword(String password) {
        this.password = password;
        return this;
    }
}
