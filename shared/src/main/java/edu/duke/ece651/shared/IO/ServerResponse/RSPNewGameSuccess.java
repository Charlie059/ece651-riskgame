package edu.duke.ece651.shared.IO.ServerResponse;

import edu.duke.ece651.shared.Visitor.ResponseVisitor;
import edu.duke.ece651.shared.Wrapper.GameID;
import edu.duke.ece651.shared.map.Territory;

import java.util.HashMap;

public class RSPNewGameSuccess implements Response {
    //For orders
    private GameID gameID;
    private Integer numOfPlayer;
    private HashMap<String, Territory> myTerritories;
    private clientPlayerPacket;

    //For Info
    private int foodResource;
    private int techResource;
    private int currTechLevel;
    private boolean isLose;// if lose jump Lose view
    private boolean isWon;// if win jump Win view


    public RSPNewGameSuccess(GameID gameID, Integer numOfPlayer, HashMap<String, Territory> myTerritories, int foodResource, int techResource, int currTechLevel, boolean isLose, boolean isWon) {
        this.gameID = gameID;
        this.numOfPlayer = numOfPlayer;
        this.myTerritories = myTerritories;
        this.foodResource = foodResource;
        this.techResource = techResource;
        this.currTechLevel = currTechLevel;
        this.isLose = isLose;
        this.isWon = isWon;
    }

    public RSPNewGameSuccess() {

    }

    @Override
    public void accept(ResponseVisitor responseVisitor) {

    }

    public GameID getGameID() {
        return gameID;
    }

    public Integer getNumOfPlayer() {
        return numOfPlayer;
    }

    public HashMap<String, Territory> getMyTerritories() {
        return myTerritories;
    }

    public int getFoodResource() {
        return foodResource;
    }

    public int getTechResource() {
        return techResource;
    }

    public int getCurrTechLevel() {
        return currTechLevel;
    }

    public boolean isLose() {
        return isLose;
    }

    public boolean isWon() {
        return isWon;
    }


    public RSPNewGameSuccess(ClientPlayerPacket clientPlayerPacket) {
        this.clientPlayerPacket = clientPlayerPacket;
    }

    public ClientPlayerPacket getClientPlayerPacket() {
        return clientPlayerPacket;
    }
}
