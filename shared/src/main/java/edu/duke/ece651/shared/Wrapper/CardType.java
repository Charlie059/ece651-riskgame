package edu.duke.ece651.shared.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class CardType {
    private final Integer specialSpyUpgradeCost = 1;
    private final Integer unitDeployCost = 1;
    private final List<Integer> specialSpyUpgrade =  List.of(11,specialSpyUpgradeCost);
    private final List<Integer> unitDeploy = List.of(12,unitDeployCost);
    public List<Integer> SpecialSpyUpgrade(){
        return specialSpyUpgrade;
    }
    public List<Integer> UnitDeploy(){
        return unitDeploy;
    }
}
