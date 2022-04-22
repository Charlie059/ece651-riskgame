package edu.duke.ece651.server;

import edu.duke.ece651.server.Checker.*;
import edu.duke.ece651.server.Wrapper.*;
import edu.duke.ece651.shared.*;
import edu.duke.ece651.shared.Cards.Card;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Visitor.ActionVisitor;
import edu.duke.ece651.shared.Wrapper.CardType;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ObjectStream;
import edu.duke.ece651.shared.Wrapper.SpyType;
import edu.duke.ece651.shared.map.Map;
import edu.duke.ece651.shared.map.Spy;
import edu.duke.ece651.shared.map.Territory;
import edu.duke.ece651.shared.map.Unit;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

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
    private volatile GameRunnableHashMap gameRunnableHashMap;
    private final Integer CloakingCost = 100;

    /**
     * Construct Checker, all by Communicator Reference
     * Note: if the value is reference, it can only be set rather than new
     * Note: if the hashmap is putting new key, this key can only be new rather than use reference
     * the pickup from reference key in Hashmap should be done by overwrite HashMethod
     *
     * @param accountID      PlayerID Object reference
     * @param gameID         CurrGameID Object reference
     * @param clientSocket   ClientSocket Object referece
     * @param accountHashMap PlayerHashMap Object reference
     * @param gameHashMap    GameHashMap Object reference
     */
    public ActionCheckDoFeedbackVisitor(AccountID accountID, GameID gameID, Socket clientSocket, AccountHashMap accountHashMap, GameHashMap gameHashMap, GameRunnableHashMap gameRunnableHashMap) {
        //Communicate Reference
        this.accountID = accountID;
        this.gameID = gameID;
        this.clientSocket = clientSocket;
        this.gameHashMap = gameHashMap;
        this.accountHashMap = accountHashMap;
        this.gameRunnableHashMap = gameRunnableHashMap;
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
        for (int i = 0; i < attackUnits.size(); i++) {
            //attackUnits.get(i).get(0): level,
            //attackUnits.get(i).get(1): value
            attackUnitsNum += attackUnits.get(i).get(1);
        }
        Player currplayer = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        int to_cost = currplayer.getWholeMap().getTerritoryList().get(attackAction.getTo()).getCost();
        int totalCost = attackUnitsNum * to_cost;
        int currFoodResource = currplayer.getFoodResource();
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
                currFromUnits,
                currFoodResource
        );
        if (attackChecker.doCheck() == null) {
            //TODO:AttackActionArrayList field belongs to Player
            //put attack action into attachHashMap
            if (this.gameHashMap.get(this.gameID).getAttackHashMap().containsKey(this.accountID)) {
                this.gameHashMap.get(this.gameID).getAttackHashMap().get(this.accountID).add(attackAction);
            } else {
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
        } else {
            RSPAttackFail rspAttackFail = new RSPAttackFail(attackChecker.getErrMessage());
            sendResponse(rspAttackFail);
        }
    }

    @Override
    public void visit(CommitAction commitAction) {
        CommitChecker commitChecker = new CommitChecker(this.gameHashMap, this.accountHashMap, this.accountID, this.gameID);
        if (commitChecker.doCheck() == null) {

            //Change my commit status to true
            synchronized (this.gameRunnableHashMap.get(this.gameID)) {
                this.gameHashMap.get(this.gameID).getCommittedHashMap().put(this.accountID, true);
                this.gameRunnableHashMap.get(this.gameID).notify();
            }


            //Check if Game's Combat Resolution is finished
            try {
                this.gameHashMap.get(this.gameID).getCountDownLatch().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (this.gameHashMap.get(this.gameID).getCountDownLatch().getCount() == 0) {
                this.gameHashMap.get(this.gameID).setCountDownLatch(new CountDownLatch(1));
            }


            this.gameHashMap.get(this.gameID).setCombatFinished(false);

            //Create New EnemyTerritoryInfo for this Player
            ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(
                    this.gameID,
                    this.accountID,
                    this.gameHashMap.get(this.gameID).getNumOfPlayer(),
                    this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getFoodResource(),
                    this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getTechResource(),
                    this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getCurrTechLevel(),
                    this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getTotalDeployment(),
                    this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getMyTerritories(),
                    this.gameHashMap.get(this.gameID).getPlayerHashMap().getEnemyTerritories(this.accountID),
                    this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).isLose(),
                    this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).isWon(),
                    this.getEnemyTerritoryInfo(),this.getspyInfo());
            RSPCommitSuccess rspCommitSuccess = new RSPCommitSuccess(clientPlayerPacket);
            sendResponse(rspCommitSuccess);
        } else {
            RSPCommitFail rspCommitFail = new RSPCommitFail();
            sendResponse(rspCommitFail);
        }
    }
    public HashMap<String, ArrayList<Spy>> getspyInfo(){
        HashMap<String, ArrayList<Spy>> spyInfo = new HashMap<>();
        //For all territory
        for(Territory territory: this.gameHashMap.get(this.gameID).getMap().getTerritoryList().values()){
            //Get info of my spy
            ArrayList<Spy> singleSpyInfo = territory.getSpyInfo(this.accountID);
            spyInfo.put(territory.getName(), singleSpyInfo);
        }
        return spyInfo;
    }
    //Get All of My Visiable info
    public HashMap<AccountID, HashMap<String, ArrayList<Integer>>> getEnemyTerritoryInfo() {
        Game game = this.gameHashMap.get(this.gameID);
        Player currPlayer = game.getPlayerHashMap().get(this.accountID);
        HashMap<AccountID, HashMap<String, ArrayList<Integer>>> enemyTerritoriesV2 = new HashMap<>();//Each enemy player's
        //Add Enemy's territory Info into Hashmap
        for (AccountID enemyAccountID : game.getPlayerHashMap().getPlayerHashMap().keySet()) {
            //Each Enemy
            if (!enemyAccountID.getAccountID().equals(this.accountID.getAccountID())) {
                HashMap<String, ArrayList<Integer>> territoryInfo = new HashMap<>();
                //Each Enemy's Territory
                for (String territoryName : game.getPlayerHashMap().getPlayerHashMap().get(enemyAccountID).getMyTerritories().keySet()) {
                    //For each Territory in my Territory, that is adjacent to this enemy's territory, add the territory info into
                    for (Territory territory : currPlayer.getMyTerritories().values()) {
                        //Territory that isadjcent (MeAccount, one of my territory, one of other's territory)
                        if (game.getMap().isAdjacent(this.accountID, territory.getName(), territoryName)|| game.getPlayerHashMap().getPlayerHashMap().get(enemyAccountID).getMyTerritories().get(territoryName).isHaveMySpy(this.accountID)) {
                            //And that Territory is not cloaked, or cloaked but have my spy
                            if (!game.getPlayerHashMap().getPlayerHashMap().get(enemyAccountID).getMyTerritories().get(territoryName).isCloaked() || game.getPlayerHashMap().getPlayerHashMap().get(enemyAccountID).getMyTerritories().get(territoryName).isHaveMySpy(this.accountID)) {
                                //Get Unit Info to be [num,num,num,num,.....,num]
                                ArrayList<Integer> unitsInfo = new ArrayList<>();
                                unitsInfo.add(0);
                                unitsInfo.add(0);
                                unitsInfo.add(0);
                                unitsInfo.add(0);
                                unitsInfo.add(0);
                                unitsInfo.add(0);
                                unitsInfo.add(0);
                                for (Unit unit : game.getPlayerHashMap().get(enemyAccountID).getMyTerritories().get(territoryName).getUnits()) {
                                    unitsInfo.set(unit.getLevel(), unit.getValue());
                                }
                                territoryInfo.put(territoryName, unitsInfo);
                            }
                        }
                    }
                }
                enemyTerritoriesV2.put(enemyAccountID, territoryInfo);
            }
        }
        return enemyTerritoriesV2;
    }

    @Override
    public void visit(DeployAction deployAction) {
        DeployChecker deployChecker = new DeployChecker(this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                deployAction.getTo(),
                deployAction.getDeployUnits(),
                this.gameID);
        if (deployChecker.doCheck() == null) {
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
        if (loginChecker.doCheck() == null) {
            //DO change current accountID wrapper class
            this.accountID.setAccountID(loginAction.getEnterAccount().getAccountID());
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
        int currFoodResource = currplayer.getFoodResource();
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
                totalCost,
                currFoodResource);
        if (moveChecker.doCheck() == null) {
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
        GameID newGameID = new GameID(GameIDCounter.getInstance().getCurrent_id().getCurrGameID());
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
        this.gameID.setCurrGameID(newGameID.getCurrGameID());
        //Throw New Game thread
        GameRunnable gameRunnable = new GameRunnable(this.gameHashMap, this.accountHashMap, this.gameID);
        this.gameRunnableHashMap.put(newGameID, gameRunnable);
        game.setCountDownLatch(new CountDownLatch(1));
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
                player.isWon(), this.getEnemyTerritoryInfo(), this.getspyInfo());
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
        if (signupChecker.doCheck() == null) {
            //DO Create New Account
            Account account = new Account();
            account.setPassword("12345");
            account.setPassword(signUpAction.getPassword());
            this.accountHashMap.put(signUpAction.getAccount(), account);
            this.accountID.setAccountID(signUpAction.getAccount().getAccountID());
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
        if (updateTechChecker.doCheck() == null) {
            //TODO: do update Technology level
            //This Player(me) in the currGame
            Player p = gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
            //Player temperately set update level, and mark as updated
            //Checker help Calculated the cost
            p.setUpgradeTech(updateTechChecker.getCost());
            //send response
            RSPUpgradeTechSuccess rspUpgradeTechSuccess = new RSPUpgradeTechSuccess();
            rspUpgradeTechSuccess.setTechCost(updateTechChecker.getCost());
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
        if (upgradeUnitsChecker.doCheck() == null) {
            //Do UpgradeUnit
            currplayer.DoUpgradeUnit(updateUnitsAction.getWhere(),
                    updateUnitsAction.getOldLevel(),
                    updateUnitsAction.getNewLevel(),
                    techCost);
            RSPUpgradeUnitsSuccess rspUpdateUnitsSuccess = new RSPUpgradeUnitsSuccess();
            rspUpdateUnitsSuccess.setNewLevel(updateUnitsAction.getNewLevel());
            rspUpdateUnitsSuccess.setOldLevel(updateUnitsAction.getOldLevel());
            rspUpdateUnitsSuccess.setWhere(updateUnitsAction.getWhere());
            rspUpdateUnitsSuccess.setTechCost(techCost);
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
        if (chooseGameChecker.doCheck() == null) {
            this.gameID.setCurrGameID(chooseJoinGameAction.getGameID().getCurrGameID());
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
            ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(this.gameID, this.accountID, currGame.getNumOfPlayer(), player.getFoodResource(), player.getTechResource(), player.getCurrTechLevel(), player.getTotalDeployment(), player.getMyTerritories(), currGame.getPlayerHashMap().getEnemyTerritories(this.accountID), player.isLose(), player.isWon(), this.getEnemyTerritoryInfo(), this.getspyInfo());
            RSPChooseJoinGameSuccess rspChooseJoinGameSuccess = new RSPChooseJoinGameSuccess(clientPlayerPacket);
            sendResponse(rspChooseJoinGameSuccess);
        } else {
            RSPChooseJoinGameFail rspChooseJoinGameFail = new RSPChooseJoinGameFail();
            sendResponse(rspChooseJoinGameFail);
        }

    }


    @Override
    public void visit(ChooseSwitchGameAction chooseSwitchGameAction) {
        ChooseSwitchGameChecker chooseSwitchGameChecker = new ChooseSwitchGameChecker(this.gameHashMap, this.accountHashMap, this.accountID, chooseSwitchGameAction.getGameID());
        if (chooseSwitchGameChecker.doCheck() == null) {
            // Change the game
            this.gameID.setCurrGameID(chooseSwitchGameAction.getGameID().getCurrGameID());
            // Send message
            ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(this.gameID, this.accountID, this.gameHashMap.get(this.gameID).getNumOfPlayer(), this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getFoodResource(), this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getTechResource(), this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getCurrTechLevel(), this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getTotalDeployment(), this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).getMyTerritories(), this.gameHashMap.get(this.gameID).getPlayerHashMap().getEnemyTerritories(this.accountID), this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).isLose(), this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID).isWon(), this.getEnemyTerritoryInfo(), this.getspyInfo());
            RSPChooseSwitchGameSuccess rspChooseSwitchGameSuccess = new RSPChooseSwitchGameSuccess(clientPlayerPacket);
            sendResponse(rspChooseSwitchGameSuccess);
        } else {
            RSPChooseSwitchGameFail rspChooseSwitchGameFail = new RSPChooseSwitchGameFail(
                    chooseSwitchGameChecker.getErrMessage()
            );
            sendResponse(rspChooseSwitchGameFail);
        }
    }

    @Override
    public void visit(SpyDeployAction spyDeployAction) {
        SpyDeployChecker spyDeployChecker = new SpyDeployChecker(this.gameHashMap, this.accountHashMap, this.accountID, this.gameID, spyDeployAction);
        if (spyDeployChecker.doCheck() == null) {

            Player player = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
            Map map = this.gameHashMap.get(this.gameID).getMap();
            //Add Spy to this Territory
            Spy spy = new Spy(this.accountID);
            this.gameHashMap.get(this.gameID).getMap().getTerritoryList().get(spyDeployAction.getTo()).addSpy(spy);
            //use 20 Tech Resource of this Player
            player.setTechResource(player.getTechResource() - 20);
            //Delete One Unit Level 1 from the From Territory
            map.getTerritoryList().get(spyDeployAction.getFrom()).removeUnitLevel(1, 1, map.getTerritoryList().get(spyDeployAction.getFrom()).getUnits());
            RSPSpyDeploySuccess rspSpyDeploySuccess = new RSPSpyDeploySuccess(spy);
            sendResponse(rspSpyDeploySuccess);
            System.out.println("[GameID]: " + this.gameID.getCurrGameID() + " [Player]: " + this.accountID.getAccountID() + " [RSPSpyDeploySuccess]: {UUID: " + spy.getSpyUUID() + " TYPE: " + spy.getSpyType() + "}");
        } else {
            RSPSpyDeployFail spyDeployFail = new RSPSpyDeployFail();
            sendResponse(spyDeployFail);
            System.out.println("[GameID]: " + this.gameID.getCurrGameID() + " [Player]: " + this.accountID.getAccountID() + " [RSPSpyDeployFail]");
        }
    }

    @Override
    public void visit(SpyMoveAction spyMoveAction) {
        //currPlayer
        Player currplayer = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        Map map = this.gameHashMap.get(this.gameID).getMap();

        int currFoodResource = currplayer.getFoodResource();
        //Move Spy Cost
        Integer cost = 20;
        SpyMoveChecker spyMoveChecker = new SpyMoveChecker(this.gameHashMap, accountHashMap, accountID, map, spyMoveAction, cost, currFoodResource);
        if (spyMoveChecker.doCheck() == null) {
            //Move spy to new places
            Spy spy = map.getTerritoryList().get(spyMoveAction.getFrom()).getSpy(spyMoveAction.getSpyUUID());
            map.getTerritoryList().get(spyMoveAction.getFrom()).removeSpy(spy);
            map.getTerritoryList().get(spyMoveAction.getTo()).addSpy(spy);
            //Use Food Resource
            currplayer.setFoodResource(currplayer.getFoodResource() - cost);
            RSPSpyMoveSuccess rspSpyMoveSuccess = new RSPSpyMoveSuccess();
            sendResponse(rspSpyMoveSuccess);
            //System.out.println("[GameID]: "+this.gameID.getCurrGameID()+" [Player]: "+this.accountID.getAccountID()+" [RSPSpyMoveSuccess]: {UUID: "+spyMoveAction.getSpyUUID()+" TYPE: "+spy.getSpyType()+" LOC: "+spyMoveAction.getTo()+"}");
        } else {
            RSPSpyMoveFail rspSpyMoveFail = new RSPSpyMoveFail();
            sendResponse(rspSpyMoveFail);
            //System.out.println("[GameID]: "+this.gameID.getCurrGameID()+" [Player]: "+this.accountID.getAccountID()+" [RSPSpyMoveFail]: {UUID: "+spyMoveAction.getSpyUUID()+"}");
        }
    }

    @Override
    public void visit(SpyUpgradeAction spyUpgradeAction) {
        //currPlayer
        Player currplayer = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        Map map = this.gameHashMap.get(this.gameID).getMap();
        SpyUpgradeChecker spyUpgradeChecker = new SpyUpgradeChecker(this.gameHashMap, this.accountHashMap, this.accountID, map, currplayer,spyUpgradeAction);
        if (spyUpgradeChecker.doCheck() == null) {
            Spy spy = this.gameHashMap.get(this.gameID).getMap().getTerritoryList().get(spyUpgradeAction.getFrom()).getSpy(spyUpgradeAction.getSpyUUID());
            Integer requestType = spy.getSpyType();
            SpyType spyType = new SpyType();
            if (requestType.equals(spyType.DefaultType())) {
                spy.setDefaultType();
            } else if (requestType.equals(spyType.HarrietTubman())) {
                spy.setHarrisTubman();
            } else if (requestType.equals(spyType.Rosenbergs())) {
                spy.setRosenbergs();
            }
            //Delete This player's upgrade Card
            currplayer.deleteCard(new CardType().getSpecialSpyUpgrade().get(0));
            RSPSpyUpgradeSuccess rspSpyUpgradeSuccess = new RSPSpyUpgradeSuccess();
            sendResponse(rspSpyUpgradeSuccess);
        } else {
            RSPSpyUpgradeFail rspSpyUpgradeFail = new RSPSpyUpgradeFail();
            sendResponse(rspSpyUpgradeFail);
        }

    }

    @Override
    public void visit(CloakTerritoryAction cloakTerritoryAction) {
        Player currplayer = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        Map map = this.gameHashMap.get(this.gameID).getMap();
        CloakingTerritoryActionChecker cloakingTerritoryActionChecker = new CloakingTerritoryActionChecker(
                this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                this.CloakingCost,
                map,currplayer,cloakTerritoryAction);
        if(cloakingTerritoryActionChecker.doCheck() == null){
            map.getTerritoryList().get(cloakTerritoryAction.getFrom()).setCloak();
            RSPCloakTerritorySuccess rspCloakTerritorySuccess = new RSPCloakTerritorySuccess();
            sendResponse(rspCloakTerritorySuccess);
        }else {
            RSPCloakTerritoryActionFail rspCloakTerritoryActionFail = new RSPCloakTerritoryActionFail(
                    cloakingTerritoryActionChecker.getErrMessage()
            );
            sendResponse(rspCloakTerritoryActionFail);
        }
    }

    @Override
    public void visit(CardBuyAction cardBuyAction) {

        Player player = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        Integer cardCost = cardBuyAction.getCardType().get(1);
        CardBuyChecker cardBuyChecker = new CardBuyChecker(
                this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                player,
                cardCost);
        //checker
        if (cardBuyChecker.doCheck() == null){
            //do action
            Integer cardType = cardBuyAction.getCardType().get(0);
            player.addCard(cardType);
            player.setPoints(player.getPoints() - cardBuyAction.getCardType().get(1));
            //TODO: send RSPCardBuySuccess
            RSPCardBuySuccess rspCardBuySuccess = new RSPCardBuySuccess(cardType,cardCost);
            sendResponse(rspCardBuySuccess);
        }
        else{
            //TODO: send RSPCardBuyFail
            RSPCardBuyFail rspCardBuyFail = new RSPCardBuyFail(cardBuyChecker.getErrMessage());
            sendResponse(rspCardBuyFail);
        }
    }

    @Override
    public void visit(BombardmentAction bombardmentAction) {
        Map map = this.gameHashMap.get(this.gameID).getMap();
        Player player = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        BombardmentChecker bombardmentChecker = new BombardmentChecker(
                this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                map,
                bombardmentAction.getEnemyTerritory(),
                player
        );
        if (bombardmentChecker.doCheck() == null){
            //reduce units in enemyTerr by half (rounded up)
            Territory enemyTerr = map.getTerritoryList().
                    get(bombardmentAction.getEnemyTerritory());
            enemyTerr.removeUnitsByHalf();
            //Delete This player's upgrade Card
            player.deleteCard(new CardType().getBombardment().get(0));
            //send rsp
            RSPBombardmentSuccess rspBombardmentSuccess = new RSPBombardmentSuccess(
                    bombardmentAction.getEnemyTerritory(),
                    enemyTerr.getUnits()
                    );
            sendResponse(rspBombardmentSuccess);
        }
        else{
            RSPBombardmentFail rspBombardmentFail = new RSPBombardmentFail(bombardmentChecker.getErrMessage());
            sendResponse(rspBombardmentFail);
        }
    }

    @Override
    public void visit(SanctionAction sanctionAction) {
        Player player = this.gameHashMap.get(this.gameID).getPlayerHashMap().get(this.accountID);
        SanctionChecker sanctionChecker = new SanctionChecker(
                this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                sanctionAction.getEnemyID(),
                player,
                this.gameID
        );
        //docheck
        if (sanctionChecker.doCheck() == null) {
            Player enemyPlayer = this.gameHashMap.
                    get(this.gameID).getPlayerHashMap().
                    get(sanctionAction.getEnemyID());
            //Delete This player's upgrade Card
            player.deleteCard(new CardType().getBombardment().get(0));
            enemyPlayer.setSanctionCounter(enemyPlayer.getSanctionCounter() + 2);
            //send rsp
            RSPSanctionSuccess rspSanctionSuccess = new RSPSanctionSuccess();
            sendResponse(rspSanctionSuccess);
        }
        else{
            RSPSanctionFail rspSanctionFail = new RSPSanctionFail(sanctionChecker.getErrMessage());
            sendResponse(rspSanctionFail);
        }
    }

    @Override
    public void visit(TheGreatLeapForwardAction theGreatLeapForwardAction) {
        Player player = this.gameHashMap.get(this.gameID).
                getPlayerHashMap().get(this.accountID);
        TheGreatLeapForwardChecker greatLeapForwardChecker = new TheGreatLeapForwardChecker(
                this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                player,
                theGreatLeapForwardAction.getTerritoryName()
        );
        if (greatLeapForwardChecker.doCheck() == null){
            //Delete This player's upgrade Card
            player.deleteCard(new CardType().getGreatLeapForward().get(0));
            //Do greatLeapForward
            Territory territoryToLeap = player.getMyTerritories().
                    get(theGreatLeapForwardAction.getTerritoryName());
            territoryToLeap.unitGreatLeapForward();
            //send rsp
            RSPGreatLeapForwardSuccess rspGreatLeapForwardSuccess =
                    new RSPGreatLeapForwardSuccess(
                            theGreatLeapForwardAction.getTerritoryName(),
                            territoryToLeap.getUnits()
                            );
            sendResponse(rspGreatLeapForwardSuccess);
        }
        else{
            RSPGreatLeapForwardFail rspGreatLeapForwardFail =
                    new RSPGreatLeapForwardFail(greatLeapForwardChecker.getErrMessage());
            sendResponse(rspGreatLeapForwardFail);
        }
    }

    @Override
    public void visit(GodBeWithUAction godBeWithUAction) {
        Player player = this.gameHashMap.get(this.gameID).
                getPlayerHashMap().get(this.accountID);
        GodBeWithUChecker godBeWithUChecker = new GodBeWithUChecker(
                this.gameHashMap,
                this.accountHashMap,
                this.accountID,
                player
        );
        if (godBeWithUChecker.doCheck() == null){
            player.setGodWithU(true);
            //Delete This player's upgrade Card
            player.deleteCard(new CardType().getGodBeWithYou().get(0));
            //send rsp

            RSPGodBeWithUSuccess rspGodBeWithUSuccess =
                    new RSPGodBeWithUSuccess();
            sendResponse(rspGodBeWithUSuccess);
        }
        else{
            RSPGodBeWithUFail rspGodBeWithUFail =
                    new RSPGodBeWithUFail(godBeWithUChecker.getErrMessage());
            sendResponse(rspGodBeWithUFail);
        }
    }

}
