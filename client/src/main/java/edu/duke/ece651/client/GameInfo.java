package edu.duke.ece651.client;

public class GameInfo {
    private int GameID;
    private int NPlayer;
    private String Note;

    public GameInfo(int id, int n, String note){
        this.GameID = id;
        this.NPlayer = n;
        this.Note = note;
    }

    public String getNote() {
        return Note;
    }

    public int getGameID() {
        return GameID;
    }

    public int getNPlayer() {
        return NPlayer;
    }

}
