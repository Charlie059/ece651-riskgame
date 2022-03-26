package edu.duke.ece651.client;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "playerID",
        "playAgain",
        "playerNum",
        "techUpgrade",
        "upgradeList",
        "actionList"
})
@Generated("jsonschema2pojo")
public class ClientJSON {

    @JsonProperty("playerID")
    private Integer playerID;
    @JsonProperty("playAgain")
    private Boolean playAgain;
    @JsonProperty("playerNum")
    private Integer playerNum;
    @JsonProperty("techUpgrade")
    private Boolean techUpgrade;
    @JsonProperty("upgradeList")
    private List<Upgrade> upgradeList = null;
    @JsonProperty("actionList")
    private List<Action> actionList = null;

    @JsonProperty("playerID")
    public Integer getPlayerID() {
        return playerID;
    }

    @JsonProperty("playerID")
    public ClientJSON setPlayerID(Integer playerID) {
        this.playerID = playerID;
        return this;
    }

    @JsonProperty("playAgain")
    public Boolean getPlayAgain() {
        return playAgain;
    }

    @JsonProperty("playAgain")
    public ClientJSON setPlayAgain(Boolean playAgain) {
        this.playAgain = playAgain;
        return this;
    }

    @JsonProperty("playerNum")
    public Integer getPlayerNum() {
        return playerNum;
    }

    @JsonProperty("playerNum")
    public ClientJSON setPlayerNum(Integer playerNum) {
        this.playerNum = playerNum;
        return this;
    }

    @JsonProperty("techUpgrade")
    public Boolean getTechUpgrade() {
        return techUpgrade;
    }

    @JsonProperty("techUpgrade")
    public ClientJSON setTechUpgrade(Boolean techUpgrade) {
        this.techUpgrade = techUpgrade;
        return this;
    }

    @JsonProperty("upgradeList")
    public List<Upgrade> getUpgradeList() {
        return upgradeList;
    }

    @JsonProperty("upgradeList")
    public ClientJSON setUpgradeList(List<Upgrade> upgradeList) {
        this.upgradeList = upgradeList;
        return this;
    }

    @JsonProperty("actionList")
    public List<Action> getActionList() {
        return actionList;
    }

    @JsonProperty("actionList")
    public ClientJSON setActionList(List<Action> actionList) {
        this.actionList = actionList;
        return this;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
//        if ((other instanceof ClientJSON) == false) {
//            return false;
//        }
        ClientJSON rhs = ((ClientJSON) other);
        return (((((((this.upgradeList == rhs.upgradeList)||((this.upgradeList!= null)&&this.upgradeList.equals(rhs.upgradeList)))&&((this.playAgain == rhs.playAgain)||((this.playAgain!= null)&&this.playAgain.equals(rhs.playAgain))))&&((this.actionList == rhs.actionList)||((this.actionList!= null)&&this.actionList.equals(rhs.actionList))))&&((this.techUpgrade == rhs.techUpgrade)||((this.techUpgrade!= null)&&this.techUpgrade.equals(rhs.techUpgrade))))&&((this.playerID == rhs.playerID)||((this.playerID!= null)&&this.playerID.equals(rhs.playerID))))&&((this.playerNum == rhs.playerNum)||((this.playerNum!= null)&&this.playerNum.equals(rhs.playerNum))));
    }

}