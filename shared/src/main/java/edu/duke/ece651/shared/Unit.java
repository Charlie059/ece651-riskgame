package edu.duke.ece651.shared;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "level",
        "value"
})
@Generated("jsonschema2pojo")
public class Unit {

    @JsonProperty("level")
    private Integer level;
    @JsonProperty("value")
    private Integer value;

    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    @JsonProperty("level")
    public Unit setLevel(Integer level) {
        this.level = level;
        return this;
    }

    @JsonProperty("value")
    public Integer getValue() {
        return value;
    }

    @JsonProperty("value")
    public Unit setValue(Integer value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
//        if ((other instanceof Unit) == false) {
//            return false;
//        }
        Unit rhs = ((Unit) other);
        return (((this.level == rhs.level)||((this.level!= null)&&this.level.equals(rhs.level)))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))));
    }

}