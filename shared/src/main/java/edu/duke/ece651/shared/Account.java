package edu.duke.ece651.shared;

import java.util.Objects;

public class Account {
    private String password=null;

    public String getPassword() {
        return password;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

}
