package edu.duke.ece651.shared;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MapTextView extends MapView {
    private String displayText; // save the display info
    final PrintStream out;

    public MapTextView(int numOfPlayers, PrintStream out) {
        super(numOfPlayers);
        this.displayText = "";
        this.out = out;
    }

    @Override
    void generateViewInfo(HashMap<String, Territory> territoryList) {
        // get territory info, transfer it into string
        HashMap<Integer, ArrayList<String>> terrsInfo = new HashMap<Integer, ArrayList<String>>();
        for (int i = 0; i < numOfPlayers; i++) {
            terrsInfo.put(i, new ArrayList<String>());
        }
        for (String terrName : territoryList.keySet()) {
            Territory t = territoryList.get(terrName);
            String info = terrName + " :(next to";
            for (Territory neighbour : t.neighbours) {
                info += "  " + neighbour.getName();
            }
            info += ")\n";
            HashMap<Integer, ArrayList<Unit>> Units = t.getUnits();
            for (Integer level  : Units.keySet()) {
                info += "level "+ Integer.toString(level) + ": " + Integer.toString(Units.get(level).size()) + " units\n"; 
            }
            terrsInfo.get(t.getOwner()).add(info);
        }

        // generate display info
        for (int i = 0; i < numOfPlayers; i++) {
            displayText += "player " + Integer.toString(i) + ":\n" + "--------------\n";
            for (String s : terrsInfo.get(i)) {
                displayText += s;
            }
            displayText += "\n";
        }

        return;
    }

    @Override
    void display() {
        out.print(displayText);
    }

}