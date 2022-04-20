package edu.duke.ece651.shared.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class CardType {
    private final List<Integer> specialSpyUpgrade =  List.of(11,20);
    private final List<Integer> unitDeploy = List.of(12,20);
    public List<Integer> SpecialSpyUpgrade(){
        return specialSpyUpgrade;
    }
    public List<Integer> UnitDeploy(){
        return unitDeploy;
    }
}
