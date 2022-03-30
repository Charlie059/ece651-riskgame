package edu.duke.ece651.shared;

public class Player {
    private String password=null;

    public String getPassword() {
        return password;
    }

    public Player setPassword(String password) {
        this.password = password;
        return this;
    }
}
