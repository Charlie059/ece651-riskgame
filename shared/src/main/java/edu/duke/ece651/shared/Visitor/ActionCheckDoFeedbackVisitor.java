package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.Checker.MoveChecker;
import edu.duke.ece651.shared.Checker.SignupChecker;
import edu.duke.ece651.shared.Checker.UpgradeTechChecker;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Player;
import edu.duke.ece651.shared.Wrapper.CurrGameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Game;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.Account;
import edu.duke.ece651.shared.map.Map;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * Check Action correctness
 * FeedBack Success or Failed
 */
public class ActionCheckDoFeedbackVisitor implements ActionVisitor {

    private AccountID accountID;
    private CurrGameID currGameID;
    private Socket clientSocket;
    //Global Database
    private HashMap<Integer, Game> gameHashMap;//GameID, Game
    private HashMap<String,Account>accountHashMap;//AccountID Account

    private ArrayList<AttackAction> attackActionArrayList;
    private ArrayList<Integer> TechLevelUpgradeList;
    private ArrayList<Integer> UnitLevelUpgradeList;

    /**
     * Construct Checker, all by Communicator Reference
     * @param accountID PlayerID Object reference
     * @param currGameID CurrGameID Object reference
     * @param clientSocket ClientSocket Object referece
     * @param accountHashMap PlayerHashMap Object reference
     * @param gameHashMap GameHashMap Object reference
     */
    public ActionCheckDoFeedbackVisitor(AccountID accountID, CurrGameID currGameID, Socket clientSocket, HashMap<String, Account> accountHashMap, HashMap<Integer, Game> gameHashMap) {
        //Comunicator Reference
        this.accountID = accountID;
        this.currGameID = currGameID;
        this.clientSocket = clientSocket;
        this.gameHashMap = gameHashMap;
    }

    @Override
    public void visit(AttackAction attackAction) {
        //Check
        //GameAvaliableChecker gameAvaliableChecker = new GameAvaliableChecker(this.gameHashMap,this.playerHashMap);
        //Do

        //Feedback
        Game currGame = this.gameHashMap.get(attackAction.getGameID());

        this.attackActionArrayList.add(attackAction);
        //TODO map.attackUpdate(ArrayList<AttackAction> attackActions)

    }

    @Override
    public void visit(CommitAction commitAction) {

    }

    @Override
    public void visit(DeployAction deployAction) {


    }

    @Override
    public void visit(JoinAction joinAction) {

    }

    @Override
    public void visit(LoginAction loginAction) {

    }

    @Override
    public void visit(LogoutAction logoutAction) {

    }

    @Override
    public void visit(MoveAction moveAction) {
        MoveChecker moveChecker = new MoveChecker(this.accountID,this.gameHashMap,this.accountHashMap,moveAction.getUnits(),moveAction.getFrom(),moveAction.getTo(),moveAction.getGameID());
        if(moveChecker.doCheck()){
            //Update Server Map
            Map map = moveChecker.getMap();
            //TODO:map.moveAction(from, to, ArrayList<ArrayList<Integer>> Units);
            RSPMoveSuccess rspMoveSuccess = new RSPMoveSuccess(moveAction.getFrom(),moveAction.getTo(),moveAction.getUnits());
            sendResponse(rspMoveSuccess);


        }
    }

    @Override
    public void visit(NewGameAction newGameAction) {
        //Checker
        //Do
//        String gameID = "3" ;//Random number
//        Game game = new Game();
//        Player player = new Player();
//        game.getPlayerHashMap().put(this.accountID,player);
//        this.gameHashMap.put(gameID, Game);
        //thread(this.gameHashMap, this.accountHashMap)
    }

    @Override
    public void visit(PlayAgainAction playAgainAction) {

    }

    @Override
    public void visit(QuitGameAction quitGameAction) {

    }

    @Override
    public void visit(SignUpAction signUpAction) {
        // Check Exist
        SignupChecker signupChecker = new SignupChecker(this.accountID,this.gameHashMap, this.accountHashMap, signUpAction.getAccount());
        // If account not exist
        if(signupChecker.doCheck()){
            //DO Create New Account
            Account account = new Account();
            account.setPassword(signUpAction.getPassword());
            this.accountHashMap.put(signUpAction.getAccount(), account);
            //Successful Response
            RSPSignupSuccess rspSignupSuccess = new RSPSignupSuccess();
            sendResponse(rspSignupSuccess);
        }
        else {
            //Failed Response
            RSPSignupFail rspSignupFail = new RSPSignupFail();
            sendResponse(rspSignupFail);
        }

        }


    @Override
    public void visit(SwitchGameAction switchGameAction) {

    }

    @Override
    public void visit(UpdateTechAction updateTechAction) {

    }

    @Override
    public void visit(UpdateUnitsAction updateUnitsAction) {

    }
}
