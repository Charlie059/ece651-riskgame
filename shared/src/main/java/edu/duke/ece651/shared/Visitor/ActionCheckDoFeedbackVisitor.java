package edu.duke.ece651.shared.Visitor;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.Checker.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.CurrGameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.map.Map;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
    private HashMap<String, Account> accountHashMap;//AccountID Account

    private ArrayList<AttackAction> attackActionArrayList;
    private ArrayList<Integer> TechLevelUpgradeList;
    private ArrayList<Integer> UnitLevelUpgradeList;

    /**
     * Construct Checker, all by Communicator Reference
     *
     * @param accountID      PlayerID Object reference
     * @param currGameID     CurrGameID Object reference
     * @param clientSocket   ClientSocket Object referece
     * @param accountHashMap PlayerHashMap Object reference
     * @param gameHashMap    GameHashMap Object reference
     */
    public ActionCheckDoFeedbackVisitor(AccountID accountID, CurrGameID currGameID, Socket clientSocket, HashMap<String, Account> accountHashMap, HashMap<Integer, Game> gameHashMap) {
        //Comunicator Reference
        this.accountID = accountID;
        this.currGameID = currGameID;
        this.clientSocket = clientSocket;
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        setTechLevelUpgradeList();
        setUnitLevelUpgradeList();
    }

    /**
     * setup Tech levelup table
     */
    private void setTechLevelUpgradeList() {
        TechLevelUpgradeList = new ArrayList<>();
        TechLevelUpgradeList.add(0); //level 0: cost 0
        TechLevelUpgradeList.add(50);//level 1->2: cost 50
        TechLevelUpgradeList.add(75);//level 2->3: cost 75
        TechLevelUpgradeList.add(125);//level 3->4: cost 125
        TechLevelUpgradeList.add(200);//level 4->5: cost 125
        TechLevelUpgradeList.add(300);//level 5->6: cost 125
    }

    /**
     * setup unit levelup table
     */
    private void setUnitLevelUpgradeList() {
        UnitLevelUpgradeList = new ArrayList<>();
        UnitLevelUpgradeList.add(0); //level 0: cost 0
        UnitLevelUpgradeList.add(3); //level 0->1: cost 3
        UnitLevelUpgradeList.add(8);//level 1->2: cost 8
        UnitLevelUpgradeList.add(19);//level 2->3: cost 19
        UnitLevelUpgradeList.add(25);//level 3->4: cost 25
        UnitLevelUpgradeList.add(35);//level 4->5: cost 35
        UnitLevelUpgradeList.add(50);//level 5->6: cost 50
    }

    /**
     * Send Response to Client
     *
     * @param response The <Response> object that should send back
     */
    private void sendResponse(Response response) {
        try {
            ObjectStream objectStream = new ObjectStream(this.clientSocket);
            objectStream.sendObject(response);
        } catch (IOException e) {
            System.out.println("Socket Disconnected, Send Failed!");
        }
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
    JoinChecker joinChecker = new JoinChecker(this.gameHashMap,this.accountHashMap, this.accountID,joinAction.getGameID());
    if(joinChecker.doCheck()){
        RSPJoinSuccess rspJoinSuccess = new RSPJoinSuccess();
        sendResponse(rspJoinSuccess);
    }else{
        RSPJoinFail rspJoinFail = new RSPJoinFail();
        sendResponse(rspJoinFail);
    }

    }

    @Override
    public void visit(LoginAction loginAction) {
        //CHECK
        LoginChecker loginChecker = new LoginChecker(this.accountID,this.gameHashMap,this.accountHashMap,loginAction.getEnterAccount(),loginAction.getEnterPassword());
        //IF SUCCESS
        if (loginChecker.doCheck()){
            //DO change current accountID warpper class
            this.accountID.setaccountID(loginAction.getEnterAccount());
            RSPLoginSuccess rspLoginSuccess = new RSPLoginSuccess();
            sendResponse(rspLoginSuccess);
        }else{
           RSPLoginFail rspLoginFail = new RSPLoginFail();
            sendResponse(rspLoginFail);
        }

    }

    @Override
    public void visit(LogoutAction logoutAction) {
        this.accountID.setaccountID(null);
        RSPLogoutSuccess rspLogoutSuccess = new RSPLogoutSuccess();
        sendResponse(rspLogoutSuccess);
    }

    @Override
    public void visit(MoveAction moveAction) {
        MoveChecker moveChecker = new MoveChecker(this.accountID, this.gameHashMap, this.accountHashMap, moveAction.getUnits(), moveAction.getFrom(), moveAction.getTo(), moveAction.getGameID());
        if (moveChecker.doCheck()) {
            //Update Server Map
            Map map = moveChecker.getMap();
            //TODO:map.moveAction(from, to, ArrayList<ArrayList<Integer>> Units);
            RSPMoveSuccess rspMoveSuccess = new RSPMoveSuccess(moveAction.getFrom(), moveAction.getTo(), moveAction.getUnits());
            sendResponse(rspMoveSuccess);


        }
    }

    @Override
    public void visit(NewGameAction newGameAction) {
        NewGameChecker newGameChecker = new NewGameChecker(this.gameHashMap,this.accountHashMap,this.accountID, newGameAction.getGameID());
        if(newGameChecker.doCheck()){

            Integer gameID = GameIDCounter.getInstance().getCurrent_id();
            Game game = new Game(newGameAction.getNumOfPlayer());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            PrintStream printStream = new PrintStream(System.out);
            Player player = new Player(this.accountID.getaccountID(),newGameAction.getNumOfPlayer(),bufferedReader,printStream);
            game.getPlayerHashMap().put(this.accountID.getaccountID(),player);
            this.gameHashMap.put(gameID,game);
            this.currGameID.setCurrGameID(gameID);

            GameRunnable gameRunnable = new GameRunnable();
            Thread thread = new Thread(gameRunnable);
            thread.start();

            RSPNewGameSuccess rspNewGameSuccess = new RSPNewGameSuccess();
            sendResponse(rspNewGameSuccess);
        }
        else{
            RSPNewGameFail rspNewGameFail = new RSPNewGameFail();
            sendResponse(rspNewGameFail);
        }

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
        SignupChecker signupChecker = new SignupChecker(this.accountID, this.gameHashMap, this.accountHashMap, signUpAction.getAccount());
        // If account not exist
        if (signupChecker.doCheck()) {
            //DO Create New Account
            Account account = new Account();
            account.setPassword(signUpAction.getPassword());
            this.accountHashMap.put(signUpAction.getAccount(), account);
            //Successful Response
            RSPSignupSuccess rspSignupSuccess = new RSPSignupSuccess();
            sendResponse(rspSignupSuccess);
        } else {
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
        UpgradeTechChecker updateTechChecker = new UpgradeTechChecker(
                this.accountID,
                gameHashMap,
                accountHashMap,
                updateTechAction.getNextLevel(),
                updateTechAction.getCurrTechResource(),
                TechLevelUpgradeList,
                UnitLevelUpgradeList
        );
        if (updateTechChecker.doCheck()) {
            //TODO: do update Technology level
            //This Player(me) in the currGame
            Player p = gameHashMap.get(updateTechAction.getGameID()).getPlayerHashMap().get(this.accountID);
            //Player updateTech Level, decrease Tech resource
            RSPUpdateTechSuccess rspUpdateTechSuccess = new RSPUpdateTechSuccess();
            sendResponse(rspUpdateTechSuccess);
        } else {
            RSPUpdateTechFail rspUpdateTechFail = new RSPUpdateTechFail();
            sendResponse(rspUpdateTechFail);
        }
    }

    @Override
    public void visit(UpdateUnitsAction updateUnitsAction) {

    }
}
