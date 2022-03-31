package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class LoginAction implements Action {
    private String enterAccount;
    private String enterPassword;
    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public String getEnterAccount() {
        return enterAccount;
    }

    public void setEnterAccount(String enterAccount) {
        this.enterAccount = enterAccount;
    }

    public String getEnterPassword() {
        return enterPassword;
    }

    public void setEnterPassword(String enterPassword) {
        this.enterPassword = enterPassword;
    }
}
