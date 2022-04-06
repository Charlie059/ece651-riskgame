package edu.duke.ece651.shared.map;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MapTextView extends MapView{
    private String displayText; // save the display info
    final PrintStream out;

    public MapTextView(int numOfPlayers, PrintStream out) {
        super(numOfPlayers);
        this.displayText = "";
        this.out = out;
    }

    @Override
    void generateViewInfo(HashMap<String, Territory> territoryList) {
//        displayText = "";
//        // save territory into different array based on their owners.
//        HashMap<Integer, ArrayList<String>> terrsInfo = new HashMap<Integer, ArrayList<String>>();
//        for (int i = 1; i <= numOfPlayers; i++) {
//            terrsInfo.put(i, new ArrayList<String>());
//        }
//        for (String terrName : territoryList.keySet()) {
//            Territory t = territoryList.get(terrName);
//            String info = terrName + " :(next to";
//            for (Territory neighbour : t.getNeighbour()) {
//                info += "  " + neighbour.getName();
//            }
//            info += ")\n";
//            ArrayList<Unit> Units = (ArrayList<Unit>) t.getUnits();
//            for (Unit u: Units) {
//                info += "level " + Integer.toString(u.getLevel()) + ": " + Integer.toString(u.getValue()) + " units\n";
//            }
//            if (terrsInfo.get(t.getOwnerId()) != null) {
//                terrsInfo.get(t.getOwnerId()).add(info);
//            }
//        }
//
//        // generate display info
//        for (int i = 1; i <= numOfPlayers; i++) {
//            displayText += "player " + Integer.toString(i) + ":\n" + "--------------\n";
//            for (String s : terrsInfo.get(i)) {
//                displayText += s;
//            }
//            displayText += "\n";
//        }

        return;
    }

    @Override
    void display() {
        out.print(displayText);
    }

}
