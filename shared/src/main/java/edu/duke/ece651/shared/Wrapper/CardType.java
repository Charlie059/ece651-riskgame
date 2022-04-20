package edu.duke.ece651.shared.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class CardType {
    private final Integer specialSpyUpgradeCost = 1;
    private final Integer unitDeployCost = 1;
    private final ArrayList<Integer> specialSpyUpgrade = (ArrayList<Integer>) List.of(11,specialSpyUpgradeCost);
    private final ArrayList<Integer> unitDeploy = (ArrayList<Integer>) List.of(12,unitDeployCost);
    public ArrayList<Integer> SpecialSpyUpgrade(){
        return specialSpyUpgrade;
    }
    public ArrayList<Integer> UnitDeploy(){
        return unitDeploy;
    }
}
