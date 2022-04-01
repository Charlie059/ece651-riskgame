package edu.duke.ece651.server;

import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.Checker.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.map.Map;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Check Action correctness
 * FeedBack Success or Failed
 */
public class ActionCheckDoFeedbackVisitor implements ActionVisitor {

    private volatile AccountID accountID;
    private volatile GameID gameID;
    private Socket clientSocket;
    //Global Database
    private volatile HashMap<GameID, Game> gameHashMap;//GameID, Game
    private volatile HashMap<AccountID, Account> accountHashMap;//AccountID Account

    //private ArrayList<AttackAction> attackActionArrayList;
    private ArrayList<Integer> TechLevelUpgradeList;
    private ArrayList<Integer> UnitLevelUpgradeList;

    /**
     * Construct Checker, all by Communicator Reference
     *
     * @param accountID      PlayerID Object reference
     * @param gameID         CurrGameID Object reference
     * @param clientSocket   ClientSocket Object referece
     * @param accountHashMap PlayerHashMap Object reference
     * @param gameHashMap    GameHashMap Object reference
     */
    public ActionCheckDoFeedbackVisitor(AccountID accountID, GameID gameID, Socket clientSocket, HashMap<AccountID, Account> accountHashMap, HashMap<GameID, Game> gameHashMap) {
        //Comunicator Reference
        this.accountID = accountID;
        this.gameID = gameID;
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

        //TODO:AttackActionArrayList field belongs to Player
        //this.attackActionArrayList.add(attackAction);
        //TODO map.attackUpdate(ArrayList<AttackAction> attackActions)

    }

    @Override
    public void visit(CommitAction commitAction) {

        this.gameHashMap.get(this.accountID).getCommittedHashMap().put(this.accountID,true);
        RSPCommitSuccess rspCommitSuccess = new RSPCommitSuccess();
        sendResponse(rspCommitSuccess);
    }

    @Override
    public void visit(DeployAction deployAction) {


    }

    @Override
    public void visit(JoinAction joinAction) {
        JoinChecker joinChecker = new JoinChecker(this.gameHashMap, this.accountHashMap, this.accountID, joinAction.getGameID());
        if (joinChecker.doCheck()) {
            RSPJoinSuccess rspJoinSuccess = new RSPJoinSuccess();
            sendResponse(rspJoinSuccess);
            //TODO: Send Map back
        } else {
            RSPJoinFail rspJoinFail = new RSPJoinFail();
            sendResponse(rspJoinFail);
        }

    }

    @Override
    public void visit(LoginAction loginAction) {
        //CHECK
        LoginChecker loginChecker = new LoginChecker(this.accountID, this.gameHashMap, this.accountHashMap, loginAction.getEnterAccount(), loginAction.getEnterPassword());
        //IF SUCCESS
        if (loginChecker.doCheck()) {
            //DO change current accountID warpper class
            this.accountID.setaccountID(loginAction.getEnterAccount());
            RSPLoginSuccess rspLoginSuccess = new RSPLoginSuccess();
            sendResponse(rspLoginSuccess);
        } else {
            RSPLoginFail rspLoginFail = new RSPLoginFail();
            sendResponse(rspLoginFail);
        }

    }

    /**
     * When server receive an logout request
     *
     * @param logoutAction
     */
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

        // Note: This DO NOT need Checker, because we assign a Unique GameID Once NewGame action
        //New GameID
        GameID newGameID = GameIDCounter.getInstance().getCurrent_id();
        //New Game from numPlayer
        Game game = new Game(newGameAction.getNumOfPlayer());
        //New Player
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        PrintStream printStream = new PrintStream(System.out);
        Player player = new Player(this.accountID.getAccountID(), newGameAction.getNumOfPlayer(), bufferedReader, printStream);
        //Add New Player to New Game
        game.getPlayerHashMap().put(this.accountID, player);
        //Add New Player Commit Track to New Game
        game.getCommittedHashMap().put(this.accountID, false);
        //Add Game to Database
        this.gameHashMap.put(newGameID, game);
        //Change this Client's currGameID to NewGame ID
        this.gameID = newGameID;

        //Throw New Game thread
        GameRunnable gameRunnable = new GameRunnable(this.gameHashMap, this.accountHashMap, this.gameID);
        Thread thread = new Thread(gameRunnable);
        thread.start();

        RSPNewGameSuccess rspNewGameSuccess = new RSPNewGameSuccess();
        sendResponse(rspNewGameSuccess);
        //Wait Game thread to return
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
