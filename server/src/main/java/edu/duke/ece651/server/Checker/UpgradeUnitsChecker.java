package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.map.Territory;

public class UpgradeUnitsChecker extends ActionChecker{
    private Integer newLevel;
    private Integer oldLevel;
    private Integer currentTechResource;
    private int techCost;
    private Territory where;
    private Integer currTechLevel;
    public UpgradeUnitsChecker(AccountID accountID,
                              GameHashMap gameHashMap,
                              AccountHashMap accountHashMap,
                              Territory where,
                              Integer _newLevel,
                              Integer _oldLevel,
                               Integer techCost,
                               Integer techResource,
                               Integer currTechLevel
    ){
        super( gameHashMap,accountHashMap ,accountID);
        this.newLevel = _newLevel;
        this.oldLevel = _oldLevel;
        this.where = where;
        this.techCost = techCost;
        this.currentTechResource = techResource;
        this.currTechLevel = currTechLevel;
    }

    @Override
    public String doCheck(){
        //max level: 6, cannot upgrade level-6 units
        boolean isEnoughTechResource = this.techCost <= this.currentTechResource;
        boolean isEnoughUnits = this.where.getUnits().get(this.oldLevel).getValue() >= 1;
        boolean isNewLevelValid = this.newLevel > this.oldLevel && this.newLevel<= this.currTechLevel;
        if (isEnoughTechResource && isEnoughUnits && isNewLevelValid){
            this.errMessage = null;
            return this.errMessage;
        }
        if (!isEnoughTechResource) {
            this.errMessage = "UpgradeUnit Error: does not have enough Tech Resource!";
        }
        if (!isEnoughUnits){
            this.errMessage = "UpgradeUnit Error: unit to upgrade does not exist!";
        }
        if (!isNewLevelValid){
            this.errMessage = "UpgradeUnit Error: new level is invalid!";
        }
        return this.errMessage;
    }
}
