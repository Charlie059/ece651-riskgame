package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.AccountID;

public class LoginAction implements Action {
    private AccountID enterAccount;
    private String enterPassword;

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public AccountID getEnterAccount() {
        return enterAccount;
    }

    public void setEnterAccount(AccountID enterAccount) {
        this.enterAccount = enterAccount;
    }

    public String getEnterPassword() {
        return enterPassword;
    }

    public void setEnterPassword(String enterPassword) {
        this.enterPassword = enterPassword;
    }
}
