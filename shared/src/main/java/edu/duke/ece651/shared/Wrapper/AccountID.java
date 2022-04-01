package edu.duke.ece651.shared.Wrapper;

import java.io.Serializable;
import java.util.Objects;

public class AccountID implements Serializable {
    private String accountID= null;

    public AccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountID accountID1 = (AccountID) o;
        return Objects.equals(accountID, accountID1.accountID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID);
    }
}
