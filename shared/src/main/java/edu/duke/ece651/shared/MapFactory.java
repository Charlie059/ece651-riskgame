package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

public interface MapFactory {
    /**
     * Create a fixed map for diiferent number of players(2-5). Each player has 3 territories in the
     * beginning.
     *
     * @return HaspMap of all the territory
     */
    HashMap<String, Territory> createMap();

    ArrayList<ArrayList<String>> createGroupsForPlayer();

}
