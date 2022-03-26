
package edu.duke.ece651.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "isDeploy",
    "numOfPlayer",
    "territory"
})
@Generated("jsonschema2pojo")
public class JSONMap {

    @JsonProperty("isDeploy")
    private Boolean isDeploy;
    @JsonProperty("numOfPlayer")
    private Integer numOfPlayer;
    @JsonProperty("territory")
    private List<Territory> territory = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("isDeploy")
    public Boolean getIsDeploy() {
        return isDeploy;
    }

    @JsonProperty("isDeploy")
    public JSONMap setIsDeploy(Boolean isDeploy) {
        this.isDeploy = isDeploy;
        return this;
    }

    @JsonProperty("numOfPlayer")
    public Integer getNumOfPlayer() {
        return numOfPlayer;
    }

    @JsonProperty("numOfPlayer")
    public JSONMap setNumOfPlayer(Integer numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
        return this;
    }

    @JsonProperty("territory")
    public List<Territory> getTerritory() {
        return territory;
    }

    @JsonProperty("territory")
    public JSONMap setTerritory(List<Territory> territory) {
        this.territory = territory;
        return this;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        JSONMap rhs = ((JSONMap) other);
        return (((((this.numOfPlayer == rhs.numOfPlayer)||((this.numOfPlayer!= null)&&this.numOfPlayer.equals(rhs.numOfPlayer)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.isDeploy == rhs.isDeploy)||((this.isDeploy!= null)&&this.isDeploy.equals(rhs.isDeploy))))&&((this.territory == rhs.territory)||((this.territory!= null)&&this.territory.equals(rhs.territory))));
    }

}
