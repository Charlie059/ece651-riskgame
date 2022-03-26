package edu.duke.ece651.shared;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "actionType",
        "from",
        "to:",
        "units"
})
@Generated("jsonschema2pojo")
public class Action {

    @JsonProperty("actionType")
    private String actionType;
    @JsonProperty("from")
    private Object from;
    @JsonProperty("to:")
    private String to;
    @JsonProperty("units")
    private List<Unit> units = null;

    @JsonProperty("actionType")
    public String getActionType() {
        return actionType;
    }

    @JsonProperty("actionType")
    public Action setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    @JsonProperty("from")
    public Object getFrom() {
        return from;
    }

    @JsonProperty("from")
    public Action setFrom(Object from) {
        this.from = from;
        return this;
    }

    @JsonProperty("to:")
    public String getTo() {
        return to;
    }

    @JsonProperty("to:")
    public Action setTo(String to) {
        this.to = to;
        return this;
    }

    @JsonProperty("units")
    public List<Unit> getUnits() {
        return units;
    }

    @JsonProperty("units")
    public Action setUnits(List<Unit> units) {
        this.units = units;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
//        if ((other instanceof Action) == false) {
//            return false;
//        }
        Action rhs = ((Action) other);
        return (((((this.actionType == rhs.actionType)||((this.actionType!= null)&&this.actionType.equals(rhs.actionType)))&&((this.from == rhs.from)||((this.from!= null)&&this.from.equals(rhs.from))))&&((this.to == rhs.to)||((this.to!= null)&&this.to.equals(rhs.to))))&&((this.units == rhs.units)||((this.units!= null)&&this.units.equals(rhs.units))));
    }

}

