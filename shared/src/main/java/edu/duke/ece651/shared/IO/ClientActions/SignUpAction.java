package edu.duke.ece651.shared.IO.ClientActions;

import edu.duke.ece651.shared.Visitor.ActionVisitor;

public class SignUpAction implements Action {
    private String account;
    private String password;

    @Override
    public void accept(ActionVisitor actionVisitor) {
        actionVisitor.visit(this);
    }

    public String getAccount() {
        return account;
    }

    public Action setAccount(String account) {
        this.account = account;
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
