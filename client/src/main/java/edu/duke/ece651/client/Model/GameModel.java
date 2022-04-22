package edu.duke.ece651.client.Model;

import edu.duke.ece651.client.ClientSocket;
import edu.duke.ece651.shared.IO.ClientActions.*;
import edu.duke.ece651.shared.IO.ServerResponse.*;
import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Wrapper.CardType;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Spy;
import edu.duke.ece651.shared.map.Territory;
import edu.duke.ece651.shared.map.Unit;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.util.*;

/**
 * GameModel contains game info to be displayed into view(pass by controller)
 */
public class GameModel extends Model {
    private static GameModel gameModel;


    private ClientPlayerPacket clientPlayerPacket;
    private List<MyTool> cardRepository = new ArrayList<>();
    private int currPoint = 1000;


    private HashMap<String, ArrayList<Integer>> localEnemyTerrs = new HashMap<>(); // TerrName -> Units

    private GameModel() {
    }

    public synchronized static GameModel getInstance() {
        if (gameModel == null) {
            gameModel = new GameModel();
        }
        return gameModel;
    }


    /**
     * Get player id
     *
     * @return String
     */
    public String getPlayerID() {
        return this.clientPlayerPacket.getAccountID().getAccountID();
    }

    /**
     * Get food Res
     *
     * @return
     */
    public int getFoodRes() {
        return this.clientPlayerPacket.getFoodResource();
    }

    /**
     * Get food Res
     *
     * @return
     */
    public int getTechRes() {
        return this.clientPlayerPacket.getTechResource();
    }


    /**
     * Get tech level
     *
     * @return tech level
     */
    public int getTechlevel() {
        return this.clientPlayerPacket.getTechLevel();
    }


    /**
     * Get terr's units list from terrName
     *
     * @param terrName String
     * @param ans      Arr
     */
    public void getTerrUnits(String terrName, ArrayList<Integer> ans) {
        // Get Terr units form the model
        HashMap<String, Territory> myTerritories = GameModel.getInstance().getClientPlayerPacket().getMyTerritories();
        if (myTerritories.containsKey(terrName)) {
            ArrayList<Unit> units = myTerritories.get(terrName).getUnits();
            // Set unit to the view
            for (int i = 0; i < units.size(); i++) {
                ans.add(units.get(i).getValue());
            }
        } else {
            // TODO Change
            // We cannot get enmity's info
            for (int i = 0; i < 7; i++) {
                ans.add(0);
            }
        }
    }





    /**
     * Search Card type
     */
    private List<Integer> searchCardType(String cardType) {
        switch (cardType) {
            case "Bombardment":
                return List.of(11, 120);
            case "Sanction":
                return List.of(12, 300);
            case "The Great Leap Forward":
                return List.of(13, 300);
            case "Day breaks(spy)":
                return List.of(14, 600);
            case "God be with you":
                return List.of(15, 150);
            case "SpecialSpyUpgrade":
                return List.of(16, 120);
            case "UnitDeploy":
                return List.of(17, 0);
        }
        return List.of(-1, -1);
    }

    public List<MyTool> getCardRepository() {
        return cardRepository;
    }

    public int getCurrPoint() {
        return currPoint;
    }


    /**
     * USe God be with you action
     * @param debugMode
     * @return
     */
    public String useGodBeWithU(boolean debugMode){
        if (debugMode) {
            return null;
        }
        try {
            GodBeWithUAction godBeWithUAction = new GodBeWithUAction();
            ClientSocket.getInstance().sendObject(godBeWithUAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPGodBeWithUSuccess
            if (response.getClass() != RSPGodBeWithUSuccess.class) {
                RSPGodBeWithUFail rspGodBeWithUFail = (RSPGodBeWithUFail) response;
                return rspGodBeWithUFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPGodBeWithUSuccess rspGodBeWithUSuccess = (RSPGodBeWithUSuccess) response;
            // TODO do affect

            return null;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server Find Error";
    }



    /**
     * useGreatLeapForward
     * @param terrName
     * @param debugMode
     * @return
     */
    public String useGreatLeapForward(String terrName, boolean debugMode){
        if (debugMode) {
            return null;
        }
        try {

            TheGreatLeapForwardAction theGreatLeapForwardAction = new TheGreatLeapForwardAction(terrName);
            ClientSocket.getInstance().sendObject(theGreatLeapForwardAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPGreatLeapForwardSuccess
            if (response.getClass() != RSPGreatLeapForwardSuccess.class) {
                RSPGreatLeapForwardFail rspGreatLeapForwardFail = (RSPGreatLeapForwardFail) response;
                return rspGreatLeapForwardFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPGreatLeapForwardSuccess rspGreatLeapForwardSuccess = (RSPGreatLeapForwardSuccess) response;

            // TODO do affect

            return null;


        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server Find Error";
    }


    /**
     * Use Bombard
     * @param enemyTerr
     * @param debugMode
     * @return
     */
    public String useBombard(String enemyTerr, boolean debugMode) {
        // For Debug only
        if (debugMode) {
            return null;
        }

        try {

            BombardmentAction bombardmentAction = new BombardmentAction(enemyTerr);
            ClientSocket.getInstance().sendObject(bombardmentAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPBombardmentSuccess
            if (response.getClass() != RSPBombardmentSuccess.class) {
                RSPBombardmentFail rspBombardmentFail = (RSPBombardmentFail) response;
                return rspBombardmentFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPBombardmentSuccess rspBombardmentSuccess = (RSPBombardmentSuccess) response;


            // TODO do affect

            return null;


        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server Find Error";
    }

    /**
     * Try to buy card
     * @param cardType
     * @param debugMode
     * @return
     */
    public String try2BuyCard(String cardType, boolean debugMode) {
        // For Debug only
        if (debugMode) {
            cardRepository.add(new MyTool("Bombardment"));
            cardRepository.add(new MyTool("Sanction"));
            return null;
        }

        try {

            CardBuyAction cardBuyAction = new CardBuyAction(searchCardType(cardType));
            ClientSocket.getInstance().sendObject(cardBuyAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPCardBuySuccess
            if (response.getClass() != RSPCardBuySuccess.class) {
                RSPCardBuyFail rspCardBuyFail = (RSPCardBuyFail) response;
                return rspCardBuyFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPCardBuySuccess rspCardBuySuccess = (RSPCardBuySuccess) response;

            // reduce the point
            this.currPoint -= searchCardType(cardType).get(1);
            System.out.println(this.currPoint);

            // Add the item into repo
            this.cardRepository.add(new MyTool(cardType));

            return null;


        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server Find Error";
    }

    /**
     * do move spy in the map
     */
    private void moveSpy(String from, String to) {
        HashMap<String, ArrayList<Spy>> spyInfo = this.clientPlayerPacket.getSpyInfo();
        ArrayList<Spy> spyArrayListFrom = spyInfo.get(from);
        // get the first spy
        Spy spy = spyArrayListFrom.get(0);
        // rm form the spyArrayList
        spyArrayListFrom.remove(0);

        // Add to the to terr
        if (spyInfo.containsKey(to)) {
            ArrayList<Spy> spyArrayListTo = spyInfo.get(to);
            spyArrayListTo.add(spy);
        } else {
            ArrayList<Spy> spyArrayListTo = new ArrayList<>();
            spyArrayListTo.add(spy);
            spyInfo.put(to, spyArrayListTo);
        }
    }

    /**
     * DO move Spy
     *
     * @param dePloyInfo
     * @param debugMode
     * @return
     */
    public String doMoveSpy(String[] dePloyInfo, boolean debugMode) {
        // For Debug only
        if (debugMode) {
            moveSpy(dePloyInfo[0], dePloyInfo[1]);
            return null;
        }

        try {
            String from = dePloyInfo[0];
            String to = dePloyInfo[1];

            // if we have spy in that terr
            if (this.getClientPlayerPacket().getSpyInfo().containsKey(from)) {

                // get the first spy in the arr list

                if (this.getClientPlayerPacket().getSpyInfo().get(from).size() > 0) {

                    // get the first spy in the arr list
                    Spy spy = this.getClientPlayerPacket().getSpyInfo().get(from).get(0);

                    // Send a attackAction to server
                    SpyMoveAction spyMoveAction = new SpyMoveAction(spy.getSpyUUID(), from, to);
                    ClientSocket.getInstance().sendObject(spyMoveAction);

                    // Recv server response
                    Response response = (Response) ClientSocket.getInstance().recvObject();

                    // If response is not RSPAttackSuccess
                    if (response.getClass() != RSPSpyMoveSuccess.class) {
                        RSPSpyMoveFail rspSpyMoveFail = (RSPSpyMoveFail) response;
                        return rspSpyMoveFail.getErrMessage();
                    }

                    // Cast and Get the response filed
                    RSPSpyMoveSuccess rspSpyMoveSuccess = (RSPSpyMoveSuccess) response;

                    // Change the model
                    moveSpy(from, to);

                    return null;
                }
            }


        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server Find Error";
    }


    /**
     * Do Clock action
     *
     * @param dePloyInfo
     * @param debugMode
     * @return
     */
    public String doCloak(String[] dePloyInfo, boolean debugMode) {
        // For Debug only
        if (debugMode) {
            return null;
        }

        try {
            String from = dePloyInfo[0];

            // Send a attackAction to server
            CloakTerritoryAction cloakTerritoryAction = new CloakTerritoryAction(from);
            ClientSocket.getInstance().sendObject(cloakTerritoryAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPAttackSuccess
            if (response.getClass() != RSPCloakTerritorySuccess.class) {
                RSPCloakTerritoryActionFail rspCloakTerritoryActionFail = (RSPCloakTerritoryActionFail) response;
                return rspCloakTerritoryActionFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPCloakTerritorySuccess rspCloakTerritorySuccess = (RSPCloakTerritorySuccess) response;
            return null;


        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server Find Error";
    }

    /**
     * doDeploySpy
     *
     * @param dePloyInfo
     * @param debugMode
     * @return
     */
    public String doDeploySpy(String[] dePloyInfo, boolean debugMode) {
        // For Debug only
        if (debugMode) {

            HashMap<String, ArrayList<Spy>> spyInfo = this.clientPlayerPacket.getSpyInfo();
            if (spyInfo.containsKey(dePloyInfo[0])) {
                spyInfo.get(dePloyInfo[0]).add(new Spy((this.clientPlayerPacket.getAccountID())));
            } else {
                ArrayList<Spy> spyArrayList = new ArrayList<>();
                spyArrayList.add(new Spy((this.clientPlayerPacket.getAccountID())));
                spyInfo.put(dePloyInfo[0], spyArrayList);
            }
            return null;
        }

        try {
            String from = dePloyInfo[0];


            // Send a attackAction to server
            SpyDeployAction spyDeployAction = new SpyDeployAction(from, from);
            ClientSocket.getInstance().sendObject(spyDeployAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPAttackSuccess
            if (response.getClass() != RSPSpyDeploySuccess.class) {
                RSPSpyDeployFail rspSpyDeployFail = (RSPSpyDeployFail) response;
                return rspSpyDeployFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPSpyDeploySuccess rspSpyDeploySuccess = (RSPSpyDeploySuccess) response;
            Spy spy = rspSpyDeploySuccess.getSpy();


            // Change the model
            // add spy
            HashMap<String, ArrayList<Spy>> spyInfo = this.clientPlayerPacket.getSpyInfo();
            if (spyInfo.containsKey(dePloyInfo[0])) {
                spyInfo.get(dePloyInfo[0]).add(spy);
            } else {
                ArrayList<Spy> spyArrayList = new ArrayList<>();
                spyArrayList.add(spy);
                spyInfo.put(dePloyInfo[0], spyArrayList);
            }

            // rm l1 unit
            HashMap<String, Territory> myterr = this.clientPlayerPacket.getMyTerritories();
            ArrayList<Unit> units = myterr.get(from).getUnits();
            units.get(1).setValue(units.get(1).getValue() - 1);

            return null;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server find error";
    }


    public boolean doUpgradeTech(boolean debugMode) {
        if (debugMode) {
            this.clientPlayerPacket.doUpgradeTech(1, 10);
            return true;
        }

        try {
            // Send upgradeTech action to server
            UpgradeTechAction upgradeTechAction = new UpgradeTechAction();
            ClientSocket.getInstance().sendObject(upgradeTechAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPUpgradeTechSuccess
            if (response.getClass() != RSPUpgradeTechSuccess.class) return false;

            // Cast the response
            RSPUpgradeTechSuccess rspUpgradeTechSuccess = (RSPUpgradeTechSuccess) response;

            //Update model
            this.clientPlayerPacket.doUpgradeTech(this.clientPlayerPacket.getTechLevel(), rspUpgradeTechSuccess.getTechCost());
            return true;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return false;
    }

    /**
     * doAtack action, send req to server and get response then change view
     *
     * @param attackInfo
     * @param debugMode
     * @return String
     */
    public String doAttack(String[] attackInfo, boolean debugMode) {
        // For Debug only
        if (debugMode) {
            ArrayList<ArrayList<Integer>> units = new ArrayList<>();
            ArrayList<Integer> unit = new ArrayList<>();
            unit.add(0);
            unit.add(1);
            units.add(unit);
            this.clientPlayerPacket.doAttack(attackInfo[0], attackInfo[1], units, 10);
            return null;
        }

        try {
            String from = attackInfo[0];
            String to = attackInfo[1];
            String level = attackInfo[2];
            String num = attackInfo[3];

            // Add attack unit to array
            ArrayList<ArrayList<Integer>> units = new ArrayList<>();
            ArrayList<Integer> levelAndNum = new ArrayList<>();
            levelAndNum.add(Integer.parseInt(level));
            levelAndNum.add(Integer.parseInt(num));
            units.add(levelAndNum);

            // Send a attackAction to server
            AttackAction attackAction = new AttackAction(from, to, units);
            ClientSocket.getInstance().sendObject(attackAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPAttackSuccess
            if (response.getClass() != RSPAttackSuccess.class) {
                RSPAttackFail rspAttackFail = (RSPAttackFail) response;
                return rspAttackFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPAttackSuccess rspAttackSuccess = (RSPAttackSuccess) response;

            // Change the model
            this.clientPlayerPacket.doAttack(rspAttackSuccess.getFrom(), rspAttackSuccess.getTo(), rspAttackSuccess.getUnits(), rspAttackSuccess.getTotalCost());
            return null;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server Find Error";
    }

    /**
     * Upgrade one unit
     *
     * @return true for success
     */
    public String doUpgradeUnit(String[] upgradeInfo, boolean debugMode) {
        if (debugMode) {
            this.clientPlayerPacket.doUpgradeUnit("b1", 0, 1, 10);
            return null;
        }

        try {
            String from = upgradeInfo[0];
            String selectCurLevel = upgradeInfo[1];
            String selectNum = upgradeInfo[2];
            String selectUpgradeLevel = upgradeInfo[3];

            // Send a join action to server
            UpgradeUnitsAction upgradeUnitsAction = new UpgradeUnitsAction(from, Integer.parseInt(selectCurLevel), Integer.parseInt(selectUpgradeLevel));
            ClientSocket.getInstance().sendObject(upgradeUnitsAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPUpgradeUnitsSuccess
            if (response.getClass() != RSPUpgradeUnitsSuccess.class) {
                RSPUpgradeUnitsFail rspUpgradeUnitsFail = (RSPUpgradeUnitsFail) response;
                return rspUpgradeUnitsFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPUpgradeUnitsSuccess rspUpgradeUnitsSuccess = (RSPUpgradeUnitsSuccess) response;

            // Change the model
            this.clientPlayerPacket.doUpgradeUnit(rspUpgradeUnitsSuccess.getWhere(), rspUpgradeUnitsSuccess.getOldLevel(), rspUpgradeUnitsSuccess.getNewLevel(), rspUpgradeUnitsSuccess.getTechCost());
            return null;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server find error";
    }


    /**
     * Player do move action, send server to check
     *
     * @param moveInfo
     * @param debugMode
     * @return true for success
     */
    public String doMove(String[] moveInfo, boolean debugMode) {
        // For Debug only
        if (debugMode) {
            ArrayList<ArrayList<Integer>> units = new ArrayList<>();
            ArrayList<Integer> unit = new ArrayList<>();
            unit.add(0);
            unit.add(1);
            units.add(unit);
            this.clientPlayerPacket.doMove(moveInfo[0], moveInfo[1], units, 10);
            return null;
        }

        try {
            String from = moveInfo[0];
            String to = moveInfo[1];
            String level = moveInfo[2];
            String num = moveInfo[3];

            // Add move unit to array
            ArrayList<ArrayList<Integer>> units = new ArrayList<>();
            ArrayList<Integer> levelAndNum = new ArrayList<>();
            levelAndNum.add(Integer.parseInt(level));
            levelAndNum.add(Integer.parseInt(num));
            units.add(levelAndNum);

            // Send a join action to server
            MoveAction moveAction = new MoveAction(from, to, units);
            ClientSocket.getInstance().sendObject(moveAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is not RSPMoveSuccess
            if (response.getClass() != RSPMoveSuccess.class) {
                RSPMoveFail rspMoveFail = (RSPMoveFail) response;
                return rspMoveFail.getErrMessage();
            }

            // Cast and Get the response filed
            RSPMoveSuccess rspMoveSuccess = (RSPMoveSuccess) response;

            // Change the model
            this.clientPlayerPacket.doMove(rspMoveSuccess.getFrom(), rspMoveSuccess.getTo(), rspMoveSuccess.getUnits(), rspMoveSuccess.getTotalCost());
            return null;
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return "Server Find Error";
    }


    /**
     * Try to send commit request to server
     *
     * @return true for commit successful
     */
    public boolean doCommit(Boolean debugMode) {
        // For debug only
        if (debugMode) return true;
        // func
        try {
            // Send DeployAction to server
            CommitAction commitAction = new CommitAction();
            ClientSocket.getInstance().sendObject(commitAction);

            // Recv Response form server
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSP
            if (response.getClass() == RSPCommitSuccess.class) {
                // Get the player obj from response
                ClientPlayerPacket clientPlayerPacket = ((RSPCommitSuccess) response).getClientPlayerPacket();

                // If clientPlayerPacket is null return false
                if (clientPlayerPacket == null) return false;

                // Update the GameModel
                this.clientPlayerPacket = clientPlayerPacket;
                // Return true
                return true;
            }

        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return false;
    }

    /**
     * Send deployAction to server, if success, change the model's terr info
     *
     * @return true for deployment success, else print error message
     */
    public boolean doDeploy(String to, int deployUnits, boolean debugMode) {
        // Debug use only
        if (debugMode) {
            this.clientPlayerPacket.doDeploy(to, deployUnits);
            return true;
        }
        // func
        try {
            // Send DeployAction to server
            DeployAction deployAction = new DeployAction(to, deployUnits, getClientPlayerPacket().getAccountID().getAccountID());
            ClientSocket.getInstance().sendObject(deployAction);

            // Recv Response form server
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPDeploySuccess
            if (response.getClass() == RSPDeploySuccess.class) {
                // Update the GameModel
                this.clientPlayerPacket.doDeploy(to, deployUnits);
                // Return true
                return true;
            }
        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return false;
    }


    /**
     * User choose to sitch game with specific gameID
     *
     * @param gameID int
     * @return ture for join game success
     */
    public Boolean switchGame(int gameID, boolean debugMode) {
        // Debug use only
        if (debugMode) {
            mockData();
            return true;
        }

        // func
        try {
            // Send a chooseSwitchGameAction  to server
            ChooseSwitchGameAction chooseSwitchGameAction = new ChooseSwitchGameAction(new GameID(gameID));
            ClientSocket.getInstance().sendObject(chooseSwitchGameAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPChooseJoinGameSuccess
            if (response.getClass() == RSPChooseSwitchGameSuccess.class) {
                // Get the player obj from response
                ClientPlayerPacket clientPlayerPacket = ((RSPChooseSwitchGameSuccess) response).getClientPlayerPacket();

                // If clientPlayerPacket is null return false
                if (clientPlayerPacket == null) return false;

                // Update the GameModel
                this.clientPlayerPacket = clientPlayerPacket;
                // Return true
                return true;
            }

        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return false;
    }


    /**
     * User choose to join game with specific gameID
     *
     * @param gameID int
     * @return ture for join game success
     */
    public Boolean joinGame(int gameID, boolean debugMode) {
        // Debug use only
        if (debugMode) {
            mockData();
            return true;
        }

        // func
        try {
            // Send a join action to server
            ChooseJoinGameAction chooseJoinGameAction = new ChooseJoinGameAction(new GameID(gameID));
            ClientSocket.getInstance().sendObject(chooseJoinGameAction);

            // Recv server response
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPChooseJoinGameSuccess
            if (response.getClass() == RSPChooseJoinGameSuccess.class) {
                // Get the player obj from response
                ClientPlayerPacket clientPlayerPacket = ((RSPChooseJoinGameSuccess) response).getClientPlayerPacket();

                // If clientPlayerPacket is null return false
                if (clientPlayerPacket == null) return false;

                // Update the GameModel
                this.clientPlayerPacket = clientPlayerPacket;
                // Return true
                return true;
            }

        } catch (IOException | ClassNotFoundException | ClassCastException ignored) {
        }
        return false;
    }


    /**
     * Start a new game action
     *
     * @param numOfPlayerUsrInput usr input Num of Player
     * @return true for start new game success
     */
    public Boolean startNewGame(String numOfPlayerUsrInput, boolean debugMode) {
        // Debug use only
        if (debugMode) {
            mockData();
            return true;
        }
        // func
        try {
            // Send NewGameAction to server
            NewGameAction newGameAction = new NewGameAction(Integer.parseInt(numOfPlayerUsrInput));
            ClientSocket.getInstance().sendObject(newGameAction);

            // Recv object from Server
            Response response = (Response) ClientSocket.getInstance().recvObject();

            // If response is RSPNewGameSuccess
            if (response.getClass() == RSPNewGameSuccess.class) {
                // Get the player obj from response
                ClientPlayerPacket clientPlayerPacket = ((RSPNewGameSuccess) response).getClientPlayerPacket();

                // If clientPlayerPacket is null return false
                if (clientPlayerPacket == null) return false;

                // Update the GameModel
                this.clientPlayerPacket = clientPlayerPacket;
                // Return true
                return true;
            } else return false;

        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return false;
        }

    }


    public ClientPlayerPacket getClientPlayerPacket() {
        return clientPlayerPacket;
    }


    /**
     * Mock clientPlayerPacket FOR TESTING ONLY
     */
    public ClientPlayerPacket mockData() {
        // Create mock data
        HashMap<String, Territory> myTerr = new HashMap<>();
        Territory territory1 = new Territory("b1");
        Territory territory2 = new Territory("b2");
        Territory territory3 = new Territory("b3");
        myTerr.put("b1", territory1);
        myTerr.put("b2", territory2);
        myTerr.put("b3", territory3);

        HashMap<AccountID, HashMap<String, ArrayList<Integer>>> enemyTerritoriesV2 = new HashMap<>();

        HashMap<String, ArrayList<Integer>> terr = new HashMap<>();

        ArrayList<Integer> enemyTerrUnits1 = new ArrayList<>();
        enemyTerrUnits1.add(7);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);
        enemyTerrUnits1.add(0);

        ArrayList<Integer> enemyTerrUnits2 = new ArrayList<>();
        enemyTerrUnits2.add(1);
        enemyTerrUnits2.add(4);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);
        enemyTerrUnits2.add(0);

        terr.put("a1", enemyTerrUnits1);
        terr.put("a2", enemyTerrUnits2);
        enemyTerritoriesV2.put(new AccountID("p1"), terr);

        HashMap<String, ArrayList<Spy>> spyInfo = new HashMap<>();
        ArrayList<Spy> spyArrayList = new ArrayList<>();
        spyArrayList.add(new Spy(new AccountID("p2")));
        spyInfo.put("a1", spyArrayList);

        ClientPlayerPacket clientPlayerPacket = new ClientPlayerPacket(new GameID(1), new AccountID("abc"), 2, 100, 100, 2, 9, myTerr, new HashMap<String, ArrayList<String>>(), false, false, enemyTerritoriesV2, spyInfo);
        this.clientPlayerPacket = clientPlayerPacket;
        return clientPlayerPacket;
    }


    /**
     * Get my TerrList
     *
     * @return MyTerrList
     */
    public HashSet<String> getMyTerrList() {
        HashSet<String> ans = new HashSet<>();
        HashMap<String, Territory> myTerritories = this.clientPlayerPacket.getMyTerritories();

        for (String myTerr : myTerritories.keySet()) {
            ans.add(myTerr);
        }
        return ans;
    }


    /**
     * Get the terr color
     *
     * @return self: green; enemy: in different color; invisible: black; invisible but in history: grey
     */
    public String getTerrColor(String terrName) {
        ArrayList<String> enemyTerrList = getEnemyTerrList_();

        // if it is my terr
        if (this.getMyTerrList().contains(terrName)) {
            return "#19ae52"; // green
        }
        // if is not my terr, not in enemyTerritoriesV2, not in local -> black
        else if (!this.getMyTerrList().contains(terrName) && !enemyTerrList.contains(terrName) && !localEnemyTerrs.containsKey(terrName)) {
            return "#000000"; // black
        }
        // if is not my terr, not in enemyTerritoriesV2, but in local -> grey
        else if (!this.getMyTerrList().contains(terrName) && !enemyTerrList.contains(terrName) && localEnemyTerrs.containsKey(terrName)) {
            return "#524d4d"; // grey
        }
        // if in the enemyTerritoriesV2, then set red color
        else if (enemyTerrList.contains(terrName)) {
            return "#ff0000"; // red
        }

        // Should not happen
        return "#b3a6a6";
    }

    /**
     * Get EnemyTerrList from enemyTerritoriesV2
     */
    private ArrayList<String> getEnemyTerrList_() {
        // EnemyTerrList
        ArrayList<String> enemyTerrList = new ArrayList<>();

        // Get enemyTerritoriesV2
        HashMap<AccountID, HashMap<String, ArrayList<Integer>>> enemyTerritoriesV2 = this.clientPlayerPacket.getEnemyTerritoriesV2();
        // <EnemyAccountID, <Territory, [UnitList]>>

        for (Map.Entry<AccountID, HashMap<String, ArrayList<Integer>>> entry : enemyTerritoriesV2.entrySet()) {

            AccountID accountID = entry.getKey();
            HashMap<String, ArrayList<Integer>> value = entry.getValue();

            for (Map.Entry<String, ArrayList<Integer>> inner : value.entrySet()) {
                String terr = inner.getKey();
                ArrayList<Integer> units = inner.getValue();
                enemyTerrList.add(terr);

                // add terr and units to the local
                this.localEnemyTerrs.put(terr, units);
            }
        }

        return enemyTerrList;
    }


    /**
     * Get all terr list
     *
     * @return
     */
    public ArrayList<String> getAllTerrName() {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> ans = new ArrayList<>();

        temp.add("a1");
        temp.add("a2");
        temp.add("a3");
        temp.add("b1");
        temp.add("b2");
        temp.add("b3");
        temp.add("c1");
        temp.add("c2");
        temp.add("c3");
        temp.add("d1");
        temp.add("d2");
        temp.add("d3");
        temp.add("e1");
        temp.add("e2");
        temp.add("e3");


        // Get suitable size of terr name by numOfPlayers
        for (int i = 0; i < this.clientPlayerPacket.getNumOfPlayers() * 3; i++) {
            ans.add(temp.get(i));
        }

        return ans;
    }


    /**
     * Get all terr Name
     *
     * @return all terr
     */
    public ArrayList<String> getAttackTerrName() {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> ans = new ArrayList<>();

        temp.add("a1");
        temp.add("a2");
        temp.add("a3");
        temp.add("b1");
        temp.add("b2");
        temp.add("b3");
        temp.add("c1");
        temp.add("c2");
        temp.add("c3");
        temp.add("d1");
        temp.add("d2");
        temp.add("d3");
        temp.add("e1");
        temp.add("e2");
        temp.add("e3");


        // Get suitable size of terr name by numOfPlayers
        for (int i = 0; i < this.clientPlayerPacket.getNumOfPlayers() * 3; i++) {
            ans.add(temp.get(i));
        }

        // Get my terr list and rm from ans
        HashSet<String> myTerrList = this.getMyTerrList();
        for (String s : myTerrList) {
            ans.remove(s);
        }

        return ans;
    }

    /**
     * Get local Enemy Terrs
     *
     * @return HashMap<String, ArrayList < Integer>>
     */
    public HashMap<String, ArrayList<Integer>> getLocalEnemyTerrs() {
        return localEnemyTerrs;
    }


}
