package edu.duke.ece651.shared.map;

import java.util.HashMap;

public class MapView {
    public int numOfPlayers;

    public MapView(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * create display info based on territory infomation. save the info in the class field.
     * @param territoryList all the territories
     * @return
     */
    void generateViewInfo(HashMap<String, Territory> territoryList){}

    /**
     * display the map info to client
     */
    void display(){}
}
