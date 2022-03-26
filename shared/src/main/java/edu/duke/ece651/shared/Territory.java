
package edu.duke.ece651.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "size",
    "name",
    "ownerID",
    "units",
    "neighbour"
})
@Generated("jsonschema2pojo")
public class Territory {

    @JsonProperty("size")
    private Integer size;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ownerID")
    private Integer ownerID;
    @JsonProperty("units")
    private List<Unit> units = null;
    @JsonProperty("neighbour")
    private List<String> neighbour = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    @JsonProperty("size")
    public Territory setSize(Integer size) {
        this.size = size;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public Territory setName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("ownerID")
    public Integer getOwnerID() {
        return ownerID;
    }

    @JsonProperty("ownerID")
    public Territory setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
        return this;
    }

    @JsonProperty("units")
    public List<Unit> getUnits() {
        return units;
    }

    @JsonProperty("units")
    public Territory setUnits(List<Unit> units) {
        this.units = units;
        return this;
    }

    @JsonProperty("neighbour")
    public List<String> getNeighbour() {
        return neighbour;
    }

    @JsonProperty("neighbour")
    public Territory setNeighbour(List<String> neighbour) {
        this.neighbour = neighbour;
        return this;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        Territory rhs = ((Territory) other);
        return (((((((this.size == rhs.size)||((this.size!= null)&&this.size.equals(rhs.size)))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.neighbour == rhs.neighbour)||((this.neighbour!= null)&&this.neighbour.equals(rhs.neighbour))))&&((this.units == rhs.units)||((this.units!= null)&&this.units.equals(rhs.units))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.ownerID == rhs.ownerID)||((this.ownerID!= null)&&this.ownerID.equals(rhs.ownerID))));
    }

}
