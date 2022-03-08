package edu.duke.ece651.shared;

import java.util.ArrayList;
import java.util.HashMap;

public interface MapFactory {
    /**
     * Create a fixed map for 3 players. Each player has three territories in the
     * beginning.
     *
     * @return HaspMap of all the territory
     */
    HashMap<String, Territory> createMap();

    ArrayList<ArrayList<String>> createGroupsForPlayer();
}
