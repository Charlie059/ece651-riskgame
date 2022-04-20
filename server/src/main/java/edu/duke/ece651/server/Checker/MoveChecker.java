package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;

import java.util.ArrayList;

public class MoveChecker extends ActionChecker{
    protected Map map;
    protected ArrayList<ArrayList<Integer>> moveUnits;
    protected final String from_name;
    protected final  String to_name;
    protected int totalCost;
    protected int currFoodResource;
    public MoveChecker(AccountID accountID,
                       GameHashMap gameHashMap,
                       AccountHashMap accountHashMap,
                       ArrayList<ArrayList<Integer>> _moveUnits,
                       String from,
                       String to,
                       GameID gameID,
                       Integer totalCost,
                       Integer currFoodResource){
        super(gameHashMap, accountHashMap, accountID);
        this.moveUnits = _moveUnits;
        this.from_name = from;
        this.to_name = to;
        this.totalCost =totalCost;
        this.map = this.gameHashMap.get(gameID).getMap();
        this.currFoodResource = currFoodResource;
    }

    /**
     * check if ownerships are same and if path exists
     * @return
     */
    @Override
    public String doCheck() {
        boolean isValid;
        try {
            //check whether path exists
            isValid = map.isPathExist(accountID, from_name, to_name);
            //If path Exist, and 0 <= totalCost <= currFoodResource
            if (isValid && this.totalCost > 0 ){
                if (this.totalCost <= currFoodResource) {
                    //return true;
                    this.errMessage = null;
                    return this.errMessage;
                }
                else{
                    //return true;
                    this.errMessage = "Move Error: does not have enough Food Resource!";
                    return this.errMessage;
                }
            }
            this.errMessage = "Move Error: path to the territory To does not exist!";
            return this.errMessage;
        }
        catch(IllegalArgumentException illegalArg){
            //output = illegalArg.getMessage();
            this.errMessage = "Move Error: cannot move to enemy's territory!";
            return this.errMessage;
        }
    }

    public int getTotalCost(){return this.totalCost;}
}
