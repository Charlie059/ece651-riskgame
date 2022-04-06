package edu.duke.ece651.server;

import edu.duke.ece651.server.Checker.*;
import edu.duke.ece651.server.Wrapper.*;
import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.map.Unit;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Check Action correctness
 * FeedBack Success or Failed
 */
public class ActionCheckDoFeedbackVisitor implements ActionVisitor {

    private volatile AccountID accountID;
    private volatile GameID gameID;
    private Socket clientSocket;
    //Global Database
    private volatile GameHashMap gameHashMap;
    private volatile AccountHashMap accountHashMap;
    //global tables for checking level-up cost
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
    public ActionCheckDoFeedbackVisitor(AccountID accountID, GameID gameID, Socket clientSocket, AccountHashMap accountHashMap, GameHashMap gameHashMap) {
        //Communicate Reference
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
        ArrayList<ArrayList<Integer>> attackUnits = attackAction.getUnits();
        //calculate
        int attackUnitsNum = 0;
        for(int i = 0; i < attackUnits.size(); i++){
            //attackUnits.get(i).get(0): level,
            //attackUnits.get(i).get(1): value
            attackUnitsNum += attackUnits.get(i).get(1);
        }
        Player currplayer = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        int to_cost = currplayer.getWholeMap().getTerritoryList().get(attackAction.getTo()).getCost();
        int totalCost = attackUnitsNum * to_cost;

        //Current From Value
        ArrayList<Unit> currFromUnits = this.gameHashMap.get(this.gameID).getMap().getTerritoryList().get(attackAction.getFrom()).getUnits();
        AttackChecker attackChecker = new AttackChecker(
                this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                attackAction.getUnits(),
                attackAction.getFrom(),
                attackAction.getTo(),
                this.gameID,
                totalCost,
                currFromUnits
        );
        if (attackChecker.doCheck()){
            //TODO:AttackActionArrayList field belongs to Player
            //put attack action into attachHashMap
            if (this.gameHashMap.get(this.gameID).getAttackHashMap().containsKey(this.accountID)){
                this.gameHashMap.get(this.gameID).getAttackHashMap().get(this.accountID).add(attackAction);
            }
            else{
                ArrayList<AttackAction> attackActionArrayList = new ArrayList<>();
                attackActionArrayList.add(attackAction);
                this.gameHashMap.get(this.gameID).getAttackHashMap().put(this.accountID, attackActionArrayList);
            }
            //doAttack(): server's current player reduces her units in to territory
            currplayer.doAttack(attackAction.getFrom(),
                    attackAction.getTo(),
                    attackAction.getUnits(),
                    totalCost);
            //send response to client
            RSPAttackSuccess rspAttackSuccess = new RSPAttackSuccess(
                    attackAction.getFrom(),
                    attackAction.getTo(),
                    attackAction.getUnits(),
                    totalCost);
            sendResponse(rspAttackSuccess);
        }
        else{
            RSPAttackFail rspAttackFail = new RSPAttackFail();
            sendResponse(rspAttackFail);
        }
    }

    @Override
    public void visit(CommitAction commitAction) {
        CommitChecker commitChecker = new CommitChecker(this.gameHashMap, this.accountHashMap, this.accountID, this.gameID);
        if (commitChecker.doCheck()) {
            this.gameHashMap.get(this.gameID).getCommittedHashMap().put(this.accountID, true);
//            while(!this.gameHashMap.get(this.gameID).getCombatFinished()){}
//            this.gameHashMap.get(this.gameID).setCombatFinished(false);
            RSPCommitSuccess rspCommitSuccess = new RSPCommitSuccess();
            sendResponse(rspCommitSuccess);
        } else {
            RSPCommitFail rspCommitFail = new RSPCommitFail();
            sendResponse(rspCommitFail);
        }
    }

    @Override
    public void visit(DeployAction deployAction) {
        DeployChecker deployChecker = new DeployChecker(this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                deployAction.getTo(),
                deployAction.getDeployUnits(),
                this.gameID);
        if (deployChecker.doCheck()) {
            //TODO: implement deploy to server map
            Player p = deployChecker.getPlayer();
            p.doDeploy(deployAction.getTo(), deployAction.getDeployUnits());
            //send respond
            RSPDeploySuccess rspDeploySuccess = new RSPDeploySuccess();
            sendResponse(rspDeploySuccess);
        } else {
            RSPDeployFail rspDeployFail = new RSPDeployFail();
            sendResponse(rspDeployFail);
        }
    }


    @Override
    public void visit(JoinAction joinAction) {
        //IF JOIN Action
        //Find All Open Game List
        ArrayList<GameID> gameIDArrayList = this.gameHashMap.findOpenGameList();
        //Send back to Game LIST
        RSPOpenGameList rspOpenGameList = new RSPOpenGameList(gameIDArrayList);
        sendResponse(rspOpenGameList);
    }

    @Override
    public void visit(LoginAction loginAction) {
        //CHECK
        LoginChecker loginChecker = new LoginChecker(this.accountID, this.gameHashMap, this.accountHashMap, loginAction.getEnterAccount(), loginAction.getEnterPassword());
        //IF SUCCESS
        if (loginChecker.doCheck()) {
            //DO change current accountID warpper class
            this.accountID = loginAction.getEnterAccount();
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
        this.accountID.setAccountID(null);
        RSPLogoutSuccess rspLogoutSuccess = new RSPLogoutSuccess();
        sendResponse(rspLogoutSuccess);
    }


    /////////
    //TODO:
    @Override
    public void visit(MoveAction moveAction) {
        //currPlayer
        Player currplayer = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        //calcualte Total cost
        Integer totalCost = 0;
        //Calcualte shortest path
        //path_cost<0 or infinity if shortest path does not exist
        //Example: fly land
        int path_cost = currplayer.getWholeMap().shortestPathFrom(this.accountID, moveAction.getFrom(), moveAction.getTo());
        int totalMoveUnitNum = 0;
        for (int i = 0; i < moveAction.getUnits().size(); i++) {
            totalMoveUnitNum += moveAction.getUnits().get(i).get(1);
        }
        totalCost = totalMoveUnitNum * path_cost;

        //Move checker check
        MoveChecker moveChecker = new MoveChecker(this.accountID,
                this.gameHashMap,
                this.accountHashMap,
                moveAction.getUnits(),
                moveAction.getFrom(),
                moveAction.getTo(),
                this.gameID,
                totalCost);
        if (moveChecker.doCheck()) {
            //Update Server this Game Map
            Player player = currplayer;
            //server player update map
            player.doMove(moveAction.getFrom(), moveAction.getTo(), moveAction.getUnits(), moveChecker.getTotalCost());
            //send response
            RSPMoveSuccess rspMoveSuccess = new RSPMoveSuccess(
                    moveAction.getFrom(),
                    moveAction.getTo(),
                    moveAction.getUnits(),
                    totalCost);
            sendResponse(rspMoveSuccess);
        } else {
            RSPMoveFail rspMoveFail = new RSPMoveFail();
            sendResponse(rspMoveFail);
        }
    }

    //TODO: Player with map field
    @Override
    public void visit(NewGameAction newGameAction) {

        // Note: This DO NOT need Checker, because we assign a Unique GameID Once NewGame action
        //New GameID
        GameID newGameID = GameIDCounter.getInstance().getCurrent_id();
        //New Game from numPlayer(Null Owner Territory)
        Game game = new Game(newGameAction.getNumOfPlayer());
        //New Player
        Player player = new Player(this.accountID, newGameID, game.getMap());
        //Add New Player to New Game
        game.getPlayerHashMap().put(this.accountID, player);
        game.setOwnership(this.accountID);
        //Announce Player to have Owned Territory
        player.assignMyTerritories();
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
        //Block until All Player Joined the Game and GameRunnable's isbegin is true
        while (!game.getBegin()) {
        }
        //If All player joined
        //Construct All Info Client need to showNewGameView
        ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(
                this.gameID,
                this.accountID,
                game.getNumOfPlayer(),
                player.getFoodResource(),
                player.getTechResource(),
                player.getCurrTechLevel(),
                player.getTotalDeployment(),
                player.getMyTerritories(),
                game.getPlayerHashMap().getEnemyTerritories(this.accountID),
                player.isLose(),
                player.isWon());
        RSPNewGameSuccess rspNewGameSuccess = new RSPNewGameSuccess(clientPlayerPacket);
       //TODO: Set Client player contructing method in new game response
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

        ArrayList<GameID> gameIDArrayList = this.gameHashMap.findGameContainsKey(this.accountID);
        RSPSwitchGameList rspSwitchGameList = new RSPSwitchGameList(gameIDArrayList);
        sendResponse(rspSwitchGameList);
    }

    //TODO:Player add Map field
    @Override
    public void visit(UpgradeTechAction upgradeTechAction) {
        UpgradeTechChecker updateTechChecker = new UpgradeTechChecker(this.accountID,
                gameHashMap,
                accountHashMap,
                gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).isTechLevelUpgrade(),
                this.gameID,
                this.TechLevelUpgradeList.get(gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getCurrTechLevel()));
        if (updateTechChecker.doCheck()) {
            //TODO: do update Technology level
            //This Player(me) in the currGame
            Player p = gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
            //Player temperately set update level, and mark as updated
            //Checker help Calculated the cost
            p.setUpgradeTech(updateTechChecker.getCost());
            //send response
            RSPUpgradeTechSuccess rspUpgradeTechSuccess = new RSPUpgradeTechSuccess();
            sendResponse(rspUpgradeTechSuccess);
        } else {
            RSPUpgradeTechFail rspUpgradeTechFail = new RSPUpgradeTechFail();
            sendResponse(rspUpgradeTechFail);
        }
    }

    @Override
    public void visit(UpgradeUnitsAction updateUnitsAction) {
        //Get Player
        Player currplayer = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        Integer oldLevel = updateUnitsAction.getOldLevel();
        Integer newLevel = updateUnitsAction.getNewLevel();

        //Calculate Cost
        int techCost = 0;
        int count = oldLevel + 1;
        if (newLevel > oldLevel && newLevel <= 6) {
            while (count <= newLevel) {
                techCost += this.UnitLevelUpgradeList.get(count);
                count++;
            }
        }
        //Find player's current techreousrce and curr Tech Level
        Integer techResource = currplayer.getTechResource();
        Integer currTechLevel = currplayer.getCurrTechLevel();
        UpgradeUnitsChecker upgradeUnitsChecker = new UpgradeUnitsChecker(this.accountID,
                gameHashMap,
                accountHashMap,
                this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getMyTerritories().get(updateUnitsAction.getWhere()),
                updateUnitsAction.getNewLevel(),
                updateUnitsAction.getOldLevel(),
                techCost,
                techResource,
                currTechLevel
        );
        if (upgradeUnitsChecker.doCheck()) {
            //Do UpgradeUnit
            currplayer.DoUpgradeUnit(updateUnitsAction.getWhere(),
                    updateUnitsAction.getOldLevel(),
                    updateUnitsAction.getNewLevel(),
                    techCost);
            RSPUpgradeUnitsSuccess rspUpdateUnitsSuccess = new RSPUpgradeUnitsSuccess();
            rspUpdateUnitsSuccess.setNewLevel(updateUnitsAction.getNewLevel());
            rspUpdateUnitsSuccess.setOldLevel(updateUnitsAction.getOldLevel());
            rspUpdateUnitsSuccess.setWhere(updateUnitsAction.getWhere());
            sendResponse(rspUpdateUnitsSuccess);
        } else {
            RSPUpgradeUnitsFail rspUpdateUnitsFail = new RSPUpgradeUnitsFail();
            sendResponse(rspUpdateUnitsFail);
        }
    }

    //TODO:Player add map field
    @Override
    public void visit(ChooseJoinGameAction chooseJoinGameAction) {
        ChooseJoinGameChecker chooseGameChecker = new ChooseJoinGameChecker(this.gameHashMap, this.accountHashMap, this.accountID, chooseJoinGameAction.getGameID());
        if (chooseGameChecker.doCheck()) {
            this.gameID = chooseJoinGameAction.getGameID();
            //New Player add to current Game
            Game currGame = this.gameHashMap.get(this.gameID);
            Player player = new Player(this.accountID, this.gameID, currGame.getMap());
            currGame.getPlayerHashMap().put(this.accountID, player);
            //Set Territory Ownership to joined player
            currGame.setOwnership(this.accountID);
            //Announce Player to have Owned Territory
            player.assignMyTerritories();
            //Add New Player Commit Track to New Game
            currGame.getCommittedHashMap().put(this.accountID, false);

            //Block until All Player Joined the Game and GameRunnable's isbegin is true
            while (!currGame.getBegin()) {
            }
            //If All player joined
            // Create response
            RSPChooseJoinGameSuccess rspChooseJoinGameSuccess = new RSPChooseJoinGameSuccess();

            sendResponse(rspChooseJoinGameSuccess);
        } else {
            RSPChooseJoinGameFail rspChooseJoinGameFail = new RSPChooseJoinGameFail();
            sendResponse(rspChooseJoinGameFail);
        }

    }


    @Override
    public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {
        ChooseSwitchGameChecker chooseSwitchGameChecker = new ChooseSwitchGameChecker(this.gameHashMap, this.accountHashMap, this.accountID, this.gameID);
        if (chooseSwitchGameChecker.doCheck()) {
            // Change the game
            this.gameID = chooseSwitchGameAction.getGameID();
            // Send message
            RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess = new RSPChooseSwitchGameSuccess();
            sendResponse(rspChooseSwitchGameSuccess);
        } else {
            RSPChooseSwitchGameFail rspChooseSwitchGameFail = new RSPChooseSwitchGameFail();
            sendResponse(rspChooseSwitchGameFail);
        }
    }


}
