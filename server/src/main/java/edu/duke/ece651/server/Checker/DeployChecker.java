package edu.duke.ece651.server.Checker;

import edu.duke.ece651.server.Wrapper.AccountHashMap;
import edu.duke.ece651.server.Wrapper.GameHashMap;
import edu.duke.ece651.server.Game;
import edu.duke.ece651.server.Player;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Map;

public class DeployChecker extends ActionChecker{
    protected Map map;
    protected int deploy_units;
    protected Player player;
    protected final  String to_name;
    protected AccountID accountID;
    protected GameID gameID;

    public DeployChecker(GameHashMap gameHashMap,
                         AccountHashMap accountHashMap,
                         AccountID accountID,
                         String to,
                         int deployUnits,
                         GameID gameID) {
        super(gameHashMap, accountHashMap, accountID);
        this.deploy_units = deployUnits;
        this.to_name = to;
        this.accountID =  accountID;
        this.gameID = gameID;
        Game game = this.gameHashMap.get(this.gameID);
        this.player = game.getPlayerHashMap().get(this.accountID);
    }

    @Override
    public String doCheck() {
        //TODO: check if deployUnits <= totalDeploy
        //TODO: check Territory "to" belongs to this player
        if (this.player.getTotalDeployment() >= this.deploy_units){
            if (this.player.getMyTerritories().containsKey(this.to_name)){
                //return true;
                this.errMessage = null;
                return this.errMessage;
            }
            else{
                //false case
                this.errMessage = "Deploy Error: does not have enough total deployment!";
                return this.errMessage;
            }
        }
        //false case
        else {
            this.errMessage = "Deploy Error: cannot deploy to enemy's territory!";
            return this.errMessage;
        }
    }

    public Player getPlayer(){return this.player;}
}
