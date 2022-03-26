package edu.duke.ece651.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "territory",
        "levelFrom",
        "levelTo",
        "numOfUnits"
})
@Generated("jsonschema2pojo")
public class Upgrade {

    @JsonProperty("territory")
    private String territory;
    @JsonProperty("levelFrom")
    private Integer levelFrom;
    @JsonProperty("levelTo")
    private Integer levelTo;
    @JsonProperty("numOfUnits")
    private Integer numOfUnits;

    @JsonProperty("territory")
    public String getTerritory() {
        return territory;
    }

    @JsonProperty("territory")
    public Upgrade setTerritory(String territory) {
        this.territory = territory;
        return this;
    }

    @JsonProperty("levelFrom")
    public Integer getLevelFrom() {
        return levelFrom;
    }

    @JsonProperty("levelFrom")
    public Upgrade setLevelFrom(Integer levelFrom) {
        this.levelFrom = levelFrom;
        return this;
    }

    @JsonProperty("levelTo")
    public Integer getLevelTo() {
        return levelTo;
    }

    @JsonProperty("levelTo")
    public Upgrade setLevelTo(Integer levelTo) {
        this.levelTo = levelTo;
        return this;
    }

    @JsonProperty("numOfUnits")
    public Integer getNumOfUnits() {
        return numOfUnits;
    }

    @JsonProperty("numOfUnits")
    public Upgrade setNumOfUnits(Integer numOfUnits) {
        this.numOfUnits = numOfUnits;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
//        if ((other instanceof Upgrade) == false) {
//            return false;
//        }
        Upgrade rhs = ((Upgrade) other);
        return (((((this.numOfUnits == rhs.numOfUnits)||((this.numOfUnits!= null)&&this.numOfUnits.equals(rhs.numOfUnits)))&&((this.territory == rhs.territory)||((this.territory!= null)&&this.territory.equals(rhs.territory))))&&((this.levelFrom == rhs.levelFrom)||((this.levelFrom!= null)&&this.levelFrom.equals(rhs.levelFrom))))&&((this.levelTo == rhs.levelTo)||((this.levelTo!= null)&&this.levelTo.equals(rhs.levelTo))));
    }

}