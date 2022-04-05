package edu.duke.ece651.shared.map;

import java.io.Serializable;

public class Unit implements Serializable {
    private Integer level;
    private Integer value;

    public Integer getLevel() {
        return level;
    }

    public Unit setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getValue() {
        return value;
    }


    public Unit setValue(Integer value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        Unit rhs = ((Unit) other);
        return (((this.level == rhs.level)||((this.level!= null)&&this.level.equals(rhs.level)))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))));
    }

}
