package edu.duke.ece651.client;

public class gameInfo {
    private int GameID;
    private int NPlayer;
    private String Note;

    public gameInfo(int id, int n, String note){
        this.GameID = id;
        this.Note = note;
        this.NPlayer = n;
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

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public void setNote(String note) {
        Note = note;
    }

    public void setNPlayer(int NPlayer) {
        this.NPlayer = NPlayer;
    }
}
