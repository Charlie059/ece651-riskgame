package edu.duke.ece651.server;

import edu.duke.ece651.shared.Wrapper.AccountID;
import edu.duke.ece651.shared.Cards.Card;
import edu.duke.ece651.shared.Wrapper.CardType;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Player {
    private AccountID accountID; // player id
    private volatile int foodResource;
    private volatile int techResource;
    private int currTechLevel;
    private int nextTechLevel;
    private boolean isTechLevelUpgrade;
    private boolean isFirstRound; // deploy
    private boolean isLose; // commit
    private boolean isGameOver;
    private boolean isWon;
    private HashMap<String, Territory> myTerritories;// all territories of the player
    private Map wholeMap;
    private int totalDeployment; // num of units
    private boolean isLoserAsked;
    private boolean isNotDisplay;
    private GameID currentGameID;
    private HashSet<Card> myCards;
    private int points;
    private int sanctionCounter;
    private boolean isGodWithU;

    public Player(AccountID _id, GameID currentGameID, Map _map) {
        this.accountID = _id;
        isFirstRound = true;
        isLose = false;
        isGameOver = false;
        isWon = false;
        this.isLoserAsked = false;
        this.isNotDisplay = false;
        this.foodResource = 100;
        this.techResource = 100;
        this.currTechLevel = 1;
        this.isTechLevelUpgrade = false;
        this.currentGameID = currentGameID;
        this.wholeMap = _map;
        this.myTerritories = new HashMap<>();
        this.totalDeployment = this.wholeMap.numOfPlayers * 3;
        // At the beginning everyone have one chance to specialUpgrade Spy
        this.myCards = new HashSet<>();
        this.addCard(new CardType().getSpecialSpyUpgrade().get(0));
        this.points = 1000;
        this.sanctionCounter = 0;
        this.isGodWithU = false;
    }


    public void setCurrentGameID(GameID gameID) {
        currentGameID = gameID;
    }

    public int getCurrentGameID() {
        return currentGameID.getCurrGameID();
    }

    /**
     * temporally do deploy on player's map
     *
     * @param to
     * @param moveUnits
     */
    public void doDeploy(String to, int moveUnits) {
        Territory to_terr = this.getMyTerritories().get(to);
        to_terr.addUnitLevel(0, moveUnits, to_terr.getUnits());
        this.totalDeployment -= moveUnits;
    }

    /**
     * temporally update player's move action to player's own map
     *
     * @param moveUnits
     * @param from_name
     * @param to_name
     * @return
     */
    public void doMove(String from_name, String to_name, ArrayList<ArrayList<Integer>> moveUnits, int totalCost) {
        this.wholeMap.getTerritoryList().get(from_name).removeUnitMultiLevels(moveUnits);
        this.wholeMap.getTerritoryList().get(to_name).addUnitMultiLevels(moveUnits);
        this.foodResource -= totalCost;
    }

    /**
     * player temporarily reduces units in territory to and reduce food resource
     *
     * @param from_name
     * @param to_name
     * @param attackUnits
     * @param totalCost
     */
    public void doAttack(String from_name, String to_name, ArrayList<ArrayList<Integer>> attackUnits, int totalCost) {
        this.wholeMap.getTerritoryList().get(from_name).removeUnitMultiLevels(attackUnits);
        this.foodResource -= totalCost;
    }

    /**
     * temporally set player's nextTechLevel
     * mark player has updated the techlevel
     *
     * @param cost
     */
    public void setUpgradeTech(int cost) {
        //Temp value to restore whether player has upgraded tech
        this.isTechLevelUpgrade = true;
        this.techResource -= cost;
    }

    /**
     * Update Player's techlevel, refresh TechUpgrade status
     * GameRunnable will call this function from Player itself in PlayerHashmap
     */
    public void doUpgradeTech() {
        if (this.isTechLevelUpgrade == true) {
            this.currTechLevel += 1;
            this.isTechLevelUpgrade = false;
        }
    }

    /**
     * temporally upgrade player's units in Territory where
     *
     * @param where
     * @param oldLevel
     * @param newLevel
     */
    public void DoUpgradeUnit(String where, int oldLevel, int newLevel, int techCost) {
        Territory terr = this.myTerritories.get(where);
        this.techResource -= techCost;
        terr.removeUnitLevel(oldLevel, 1, terr.getUnits());
        terr.addUnitLevel(newLevel, 1, terr.getUnits());
    }


    /////////////////////////////////////getters and setters///////////////////////////////////////////////////
    public void addCard(Integer type){
        Card card = new Card(type);
        this.myCards.add(card);
    }

    public boolean haveCard(Integer type){
        for(Card card : this.myCards){
            if(card.getCardType().equals(type)){
                return true;
            }
        }
        return false;
    }

    public void deleteCard(Integer type){
        for(Card card : this.myCards){
            if(card.getCardType().equals(type)){
                this.myCards.remove(card);
                return;
            }
        }
    }


    public Map getWholeMap() {
        return wholeMap;
    }

    public int getTotalDeployment() {
        return this.totalDeployment;
    }

    public void setTotalDeployment(int new_totalDeployment) {
        this.totalDeployment = new_totalDeployment;
    }

    public HashMap<String, Territory> getMyTerritories() {
        return this.myTerritories;
    }

    public int getTechResource() {
        return techResource;
    }

    public int getFoodResource() {
        return foodResource;
    }

    public int getCurrTechLevel() {
        return currTechLevel;
    }

    public boolean isTechLevelUpgrade() {
        return isTechLevelUpgrade;
    }

    /**
     * assign my territories
     */
    public void assignMyTerritories() {
        for (String terr : wholeMap.getTerritoryList().keySet()) {
            //if map owner equals the player's accountID
            if (this.accountID.equals(wholeMap.getTerritoryList().get(terr).getOwnerId())) {
                this.myTerritories.put(terr, wholeMap.getTerritoryList().get(terr));
            }
        }
    }


    /**
     * Helper function for Test
     */
    public void setCurrTechLevel(int currTechLevel) {
        this.currTechLevel = currTechLevel;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public boolean isLose() {
        return isLose;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setLose(boolean lose) {
        isLose = lose;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public void  setFoodResource(int foodResource) {
        this.foodResource = foodResource;
    }

    public void setTechResource(int techResource) {
        this.techResource = techResource;
    }

    public int getSanctionCounter() {
        return sanctionCounter;
    }

    public void setSanctionCounter(int sanctionCounter) {
        this.sanctionCounter = sanctionCounter;
    }

    public boolean isGodWithU() {
        return isGodWithU;
    }

    public void setGodWithU(boolean godWithU) {
        isGodWithU = godWithU;
    }
}
