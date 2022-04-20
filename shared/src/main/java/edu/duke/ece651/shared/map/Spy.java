package edu.duke.ece651.shared.map;


import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.SpyType;

import java.io.Serializable;
import java.util.UUID;

public class Spy implements Serializable {
    //The Spy that Belongs to

    private AccountID accountID;
    private Integer type;
    private final UUID spyUUID = UUID.randomUUID();
    public Spy(AccountID accountID) {
        this.accountID = accountID;
        this.type = new SpyType().DefaultType();
    }

    public void setDefaultType(){
        this.type = new SpyType().DefaultType();
    }
    public void setRosenbergs(){
        this.type = new SpyType().Rosenbergs();
    }
    public void setHarrisTubman(){
        this.type = new SpyType().HarrietTubman();
    }
    public Integer getSpyType(){
        return this.type;
    }
    public AccountID getSpyOwnerAccountID(){
        return this.accountID;
    }
    public UUID getSpyUUID(){
        return this.spyUUID;
    }
}
